package com.snowdango.violet.repository.dao.song

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.SongHistoryDatabase
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
class GetSongTest {

    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, SongHistoryDatabase::class.java
        ).build()
    }

    @Test
    fun getSong() = runBlocking {
        val mock = MockSong.singleData()
        db.songDao.insertSong(mock)
        val song = db.songDao.getSong(id = 1).first()
        assertEquals(song.title, mock.title)
    }

    @Test
    fun getSongsLimit100() = runBlocking {
        db.songDao.insertSongs(MockSong.over100Data())
        val songs = db.songDao.getSongs(0, 100)
        assertEquals(songs.size, 100)
    }

    @After
    fun close() {
        db.close()
    }
}