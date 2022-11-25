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
class TestArtistDao {

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
        val mockData = MockArtist.singleData()
        db.artistDao.insertArtist(mockData)
        val singleList = db.artistDao.getArtist(id = 1)
        assertEquals(singleList.first().name, mockData.name)
        db.artistDao.deleteArtist(singleList.first())
        checkClearDb()
    }

    @Test
    fun over100DataInsert() = runBlocking {
        val mockList = MockArtist.over100Data()
        db.artistDao.insertArtists(mockList)
        val count = db.artistDao.getCount()
        assertEquals(count, mockList.size.toLong())
        db.artistDao.deleteArtists(db.artistDao.getArtistsAll())
        checkClearDb()
    }

    @Test
    fun getArtistWithAlbum() = runBlocking {
        db.albumDao.insertAlbums(MockAlbum.dataList)
        db.artistDao.insertArtists(MockArtist.dataList)
        val artistWithAlbums = db.artistDao.getArtistWithAlbums(id = 2)
        val albums = MockAlbum.dataList.filter { it.artistId == 2L }
        println(artistWithAlbums)
        albums.forEach { album ->
            assertEquals(
                artistWithAlbums.first().albums.any { it.title == album.title },
                true
            )
        }
        db.albumDao.deleteAlbums(db.albumDao.getAlbumsAll())
        db.artistDao.deleteArtists(db.artistDao.getArtistsAll())
        checkClearDb()
    }

    @Test
    fun getArtistWithNothingAlbum() = runBlocking {
        db.artistDao.insertArtists(MockArtist.dataList)
        val artistWithAlbums = db.artistDao.getArtistWithAlbums(id = 1)
        println(artistWithAlbums)
    }

    private suspend fun checkClearDb() {
        assertEquals(db.artistDao.getCount(), 0)
        assertEquals(db.albumDao.getCount(), 0)
    }

    @After
    fun clear() {
        db.close()
    }
}