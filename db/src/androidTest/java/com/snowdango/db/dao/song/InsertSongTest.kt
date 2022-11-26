package com.snowdango.db.dao.song

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
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
class InsertSongTest {

    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, SongHistoryDatabase::class.java
        ).build()
    }

    @Test
    fun insertSong() = runBlocking {
        assertEquals(db.songDao.getCount(), 0)
        db.songDao.insertSong(MockSong.singleData())
        assertEquals(db.songDao.getCount(), 1)
    }

    @Test
    fun insertSongs() = runBlocking {
        val defCount = db.songDao.getCount()
        db.songDao.insertSongs(MockSong.dataList)
        assertEquals(db.songDao.getCount(), defCount + MockSong.dataList.size)
    }

    @After
    fun close() {
        db.close()
    }

}