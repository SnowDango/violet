package com.snowdango.db.dao.album

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.db.mock.MockAlbum
import com.snowdango.db.mock.MockSong
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
class GetAlbumWithSong {

    private lateinit var db: SongHistoryDatabase

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SongHistoryDatabase::class.java
        ).build()
    }

    @Test
    fun getAlbumWithSongs() = runBlocking {
        db.albumDao.insertAlbum(MockAlbum.singleData())
        db.songDao.insertSongs(MockSong.dataList)
        val albumWithSongs = db.albumDao.getAlbumWithSongs(id = 1).first()
        val songs = MockSong.dataList.filter { it.albumId == 1L }
        assertEquals(albumWithSongs.songs.size, songs.size)
        albumWithSongs.songs.forEach { song ->
            assertEquals(songs.any { it.title == song.title }, true)
        }
    }

    @Test
    fun getAlbumsWithSongsLimit100() = runBlocking {
        db.albumDao.insertAlbums(MockAlbum.over100Data())
        db.songDao.insertSongs(MockSong.dataList)
        val albumsWithSongs = db.albumDao.getAlbumsWithSongsLimit100(0)
        assertEquals(albumsWithSongs.size, 100)
    }

    @After
    fun clear() {
        db.close()
    }

}