package com.snowdango.violet.repository.dao.history

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.SongHistoryDatabase
import com.snowdango.violet.repository.mock.MockHistory
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GetHistoryTest {

    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, SongHistoryDatabase::class.java
        ).build()
    }

    @Test
    fun getHistory() = runBlocking {
        val mock = MockHistory.singleData()
        db.historyDao.insertHistory(mock)
        val history = db.historyDao.getHistory(id = 1).first()
        assertEquals(history.dateTime, history.dateTime)
    }

    @Test
    fun getHistories() = runBlocking {
        db.historyDao.insertHistories(MockHistory.over100Data())
        val histories = db.historyDao.getHistoriesLimit100(0)
        assertEquals(histories.size, 100)
    }

    @After
    fun close() {
        db.close()
    }

}