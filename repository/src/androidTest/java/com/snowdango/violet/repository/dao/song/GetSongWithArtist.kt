package com.snowdango.violet.repository.dao.song

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.SongHistoryDatabase
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
class GetSongWithArtist {

    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, SongHistoryDatabase::class.java
        ).build()
    }

    @Test
    fun getSongWithArtist() = runBlocking {
        db.songDao.insertSongs(MockSong.dataList)
        db.artistDao.insertArtists(MockArtist.dataList)
        val songWithArtist = db.songDao.getSongWithArtist(1).first()
        val artist = db.artistDao.getArtist(MockSong.dataList.first().artistId).first()
        assertEquals(songWithArtist.artist.name, artist.name)
    }

    @Test
    fun getSongWithArtistLimit100() = runBlocking {
        db.songDao.insertSongs(MockSong.over100Data())
        db.artistDao.insertArtists(MockArtist.over100Data())
        val songsWithArtist = db.songDao.getSongsWithArtist(0, 100)
        assertEquals(songsWithArtist.size, 100)
    }

    @After
    fun close() {
        db.close()
    }

}