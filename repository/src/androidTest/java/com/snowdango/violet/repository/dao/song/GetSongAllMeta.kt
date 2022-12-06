package com.snowdango.violet.repository.dao.song

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.SongHistoryDatabase
import com.snowdango.violet.repository.mock.MockAlbum
import com.snowdango.violet.repository.mock.MockArtist
import com.snowdango.violet.repository.mock.MockSong
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GetSongAllMeta {

    private lateinit var db: SongHistoryDatabase

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SongHistoryDatabase::class.java
        ).build()
    }

    @Test
    fun getSongAllMeta() = runBlocking {
        val song = MockSong.singleData()
        db.songDao.insertSong(song)
        db.artistDao.insertArtists(MockArtist.dataList)
        db.albumDao.insertAlbums(MockAlbum.dataList)
        val songAllMeta = db.songDao.getSongAllMeta(1).first()
        val songArtist = db.artistDao.getArtist(song.artistId).first()
        assertEquals(songAllMeta.artist.name, songArtist.name)
        val songAlbum = db.albumDao.getAlbum(song.albumId).first()
        assertEquals(songAllMeta.albumWithArtist.album.title, songAlbum.title)
        val albumArtist = db.artistDao.getArtist(songAlbum.artistId).first()
        assertEquals(songAllMeta.albumWithArtist.artist.name, albumArtist.name)
    }

    @Test
    fun getSongsAllMetaLimit100() = runBlocking {
        db.songDao.insertSongs(MockSong.over100Data())
        db.artistDao.insertArtists(MockArtist.over100Data())
        db.albumDao.insertAlbums(MockAlbum.over100Data())
        val songsAllMeta = db.songDao.getSongsAllMetaLimit100(0)
        assertEquals(songsAllMeta.size, 100)
    }

    @After
    fun clear() {
        db.close()
    }

}