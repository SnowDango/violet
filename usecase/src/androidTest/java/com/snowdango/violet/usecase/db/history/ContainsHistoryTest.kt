package com.snowdango.violet.usecase.db.history

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.db.mock.MockHistory
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ContainsHistoryTest {

    private lateinit var writeHistory: WriteHistory
    private lateinit var getHistory: GetHistory
    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SongHistoryDatabase::class.java
        ).build()
        writeHistory = WriteHistory(db)
        getHistory = GetHistory(db)
    }

    @Test
    fun containsTest() = runBlocking {
        writeHistory.insertHistory(MockHistory.singleData())
        assertEquals(getHistory.containsById(1), true)
        assertEquals(getHistory.containsById(2), false)
    }

    @After
    fun close() {
        db.close()
    }
    
}