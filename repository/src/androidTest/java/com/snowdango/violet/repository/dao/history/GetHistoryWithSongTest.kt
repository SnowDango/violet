package com.snowdango.violet.repository.dao.history

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.SongHistoryDatabase
import com.snowdango.violet.repository.mock.MockHistory
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
class GetHistoryWithSongTest {

    private lateinit var db: SongHistoryDatabase

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SongHistoryDatabase::class.java
        ).build()
    }

    @Test
    fun getHistoryWithSong() = runBlocking {
        db.historyDao.insertHistories(MockHistory.dataList)
        db.songDao.insertSongs(MockSong.dataList)
        val historyWithSong = db.historyDao.getHistoryWithSong(1).first()
        val song = db.songDao.getSong(MockHistory.dataList.first().songId).first()
        assertEquals(historyWithSong.song.title, song.title)
    }

    @Test
    fun getHistoriesWithSongLimit100() = runBlocking {
        db.historyDao.insertHistories(MockHistory.over100Data())
        db.songDao.insertSongs(MockSong.over100Data())
        val historiesWithSong = db.historyDao.getHistoriesWithSong(0, 100)
        assertEquals(historiesWithSong.size, 100)
    }

    @After
    fun clear() {
        db.close()
    }

}