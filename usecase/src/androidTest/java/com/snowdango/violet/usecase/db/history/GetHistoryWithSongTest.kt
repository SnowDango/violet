package com.snowdango.violet.usecase.db.history

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.SongHistoryDatabase
import com.snowdango.violet.usecase.db.mock.MockHistory
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
class GetHistoryWithSongTest {

    private lateinit var writeHistory: WriteHistory
    private lateinit var getHistory: GetHistory
    private lateinit var writeSong: WriteSong
    private lateinit var getSong: GetSong
    private lateinit var db: SongHistoryDatabase

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SongHistoryDatabase::class.java
        ).build()
        writeHistory = WriteHistory(db)
        getHistory = GetHistory(db)
        writeSong = WriteSong(db)
        getSong = GetSong(db)
    }

    @Test
    fun getHistoryWithSong() = runBlocking {
        writeHistory.insertHistories(MockHistory.dataList)
        writeSong.insertSongs(MockSong.dataList)
        val historyWriteSong = getHistory.getHistoryWithSong(1)
        val song = getSong.getSong(MockHistory.dataList.first().songId)
        assertEquals(historyWriteSong?.song?.title, song?.title)
    }

    @Test
    fun getHistoriesWithSong() = runBlocking {
        writeHistory.insertHistories(MockHistory.over100Data())
        writeSong.insertSongs(MockSong.dataList)
        val historiesWithSong = getHistory.getHistoriesWithSong(0, 100)
        assertEquals(historiesWithSong.size, 100)
    }

    @After
    fun clear() {
        db.close()
    }
}