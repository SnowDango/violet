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
class DeleteSongTest {

    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, SongHistoryDatabase::class.java
        ).build()
    }

    @Test
    fun deleteSong() = runBlocking {
        db.songDao.insertSong(MockSong.singleData())
        assertEquals(db.songDao.getCount(), 1)
        db.songDao.deleteSongById(1)
        assertEquals(db.songDao.getCount(), 0)
    }


    @After
    fun close() {
        db.close()
    }
}