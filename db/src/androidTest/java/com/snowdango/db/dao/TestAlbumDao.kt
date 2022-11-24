package com.snowdango.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.db.mock.MockAlbum
import com.snowdango.db.mock.MockArtist
import com.snowdango.db.repository.SongHistoryDatabase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TestAlbumDao {

    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, SongHistoryDatabase::class.java
        ).build()
    }

    @Test
    fun insertAndGetAndDelete() = runBlocking {
        val mockData = MockAlbum.singleData()
        db.albumDao.insertAlbum(mockData)
        assertEquals(db.albumDao.getCount(), 1)
        val singleList = db.albumDao.getAlbum(id = 1)
        assertEquals(singleList[0].title, mockData.title)
        db.albumDao.deleteAlbum(singleList.first())
        assertEquals(db.albumDao.getCount(), 0)
    }

    @Test
    fun over100DataInsert() = runBlocking {
        val mockList = MockAlbum.over100Data()
        db.albumDao.insertAlbums(mockList)
        assertEquals(db.albumDao.getCount(), mockList.size.toLong())
    }

    @Test
    fun getAlbumWithArtist() = runBlocking {
        db.artistDao.insertArtists(MockArtist.dataList)
        db.albumDao.insertAlbums(MockAlbum.dataList)
        val albumWithArtist = db.albumDao.getAlbumWithArtist(id = 1)
        val artist = db.artistDao.getArtist(albumWithArtist.first().album.artistId)
        assertEquals(albumWithArtist.first().artist.name, artist.first().name)
    }


    @After
    fun close() {
        db.close()
    }

}