package com.snowdango.db.dao.history

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.db.mock.MockHistory
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
class InsertHistoryTest {

    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, SongHistoryDatabase::class.java
        ).build()
    }

    @Test
    fun insertHistory() = runBlocking {
        assertEquals(db.historyDao.getCount(), 0)
        db.historyDao.insertHistory(MockHistory.singleData())
        assertEquals(db.historyDao.getCount(), 1)
    }

    @Test
    fun insertHistories() = runBlocking {
        val defCount = db.historyDao.getCount()
        db.historyDao.insertHistories(MockHistory.dataList)
        assertEquals(db.historyDao.getCount(), defCount + MockHistory.dataList.size)
    }

    @After
    fun close() {
        db.close()
    }

}