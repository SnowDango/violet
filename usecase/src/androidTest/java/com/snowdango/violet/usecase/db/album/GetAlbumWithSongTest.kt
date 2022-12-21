package com.snowdango.violet.usecase.db.album

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.SongHistoryDatabase
import com.snowdango.violet.usecase.db.mock.MockAlbum
import com.snowdango.violet.usecase.db.mock.MockSong
import com.snowdango.violet.usecase.db.song.GetSong
import com.snowdango.violet.usecase.db.song.WriteSong
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GetAlbumWithSongTest {

    private lateinit var writeAlbum: WriteAlbum
    private lateinit var getAlbum: GetAlbum
    private lateinit var getSong: GetSong
    private lateinit var writeSong: WriteSong
    private lateinit var db: SongHistoryDatabase

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SongHistoryDatabase::class.java
        ).build()
        writeAlbum = WriteAlbum(db)
        getAlbum = GetAlbum(db)
        getSong = GetSong(db)
        writeSong = WriteSong(db)
    }

    @Test
    fun getAlbumWithSongs() = runBlocking {
        writeAlbum.insertAlbums(MockAlbum.dataList)
        writeSong.insertSongs(MockSong.dataList)
        val albumWithSongs = getAlbum.getAlbumWithSongs(id = 1)
        val songs = MockSong.dataList.filter { it.albumId == 1L }
        albumWithSongs?.songs?.forEach { song ->
            assertEquals(songs.any { it.title == song.title }, true)
        }
        assertEquals(albumWithSongs?.songs?.size, songs.size)
    }

    @Test
    fun getAlbumsWithSongs() = runBlocking {
        writeAlbum.insertAlbums(MockAlbum.over100Data())
        writeSong.insertSongs(MockSong.dataList)
        val albumsWithSongs = getAlbum.getAlbumsWithSongs(0, 100)
        assertEquals(albumsWithSongs.size, 100)
    }

    @After
    fun clear() {
        db.close()
    }

}