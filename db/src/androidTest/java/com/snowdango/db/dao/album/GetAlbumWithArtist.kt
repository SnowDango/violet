package com.snowdango.db.dao.album

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
class GetAlbumWithArtist {

    private lateinit var db: SongHistoryDatabase

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SongHistoryDatabase::class.java
        ).build()
    }

    @Test
    fun getAlbumWithArtist() = runBlocking {
        db.albumDao.insertAlbums(MockAlbum.dataList)
        db.artistDao.insertArtists(MockArtist.dataList)
        val albumWithArtist = db.albumDao.getAlbumWithArtist(id = 1).first()
        val artist = db.artistDao.getArtist(MockAlbum.dataList.first().artistId).first()
        assertEquals(albumWithArtist.artist.name, artist.name)
    }

    @Test
    fun getAlbumWithArtistLimit100() = runBlocking {
        db.albumDao.insertAlbums(MockAlbum.over100Data())
        db.artistDao.insertArtists(MockArtist.over100Data())
        val albumWithArtist = db.albumDao.getAlbumWithArtistListLimit100(1)
        assertEquals(albumWithArtist.size, 100)
    }
    
    @After
    fun clear() {
        db.close()
    }

}