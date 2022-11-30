package com.snowdango.db.dao.artist

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
class GetArtistWithAlbumTest {

    private lateinit var db: SongHistoryDatabase

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SongHistoryDatabase::class.java
        ).build()
    }

    @Test
    fun getArtistWithAlbums() = runBlocking {
        val mockArtist = MockArtist.singleData()
        db.artistDao.insertArtist(mockArtist)
        db.albumDao.insertAlbums(MockAlbum.dataList)
        val artistWithAlbum = db.artistDao.getArtistWithAlbums(id = 1).first()
        val artistAlbumList = MockAlbum.dataList.filter { it.artistId == 1L }
        assertEquals(
            artistWithAlbum.albums.size,
            artistAlbumList.size
        )
        artistWithAlbum.albums.forEach { album ->
            assertEquals(artistAlbumList.any { it.title == album.title }, true)
        }
    }

    @Test
    fun getArtistsWithAlbumsLimit100() = runBlocking {
        db.artistDao.insertArtists(MockArtist.over100Data())
        db.albumDao.insertAlbums(MockAlbum.dataList)
        assertEquals(db.artistDao.getArtistWithAlbumsLimit100(0).size, 100)
    }

    @After
    fun close() {
        db.close()
    }

}