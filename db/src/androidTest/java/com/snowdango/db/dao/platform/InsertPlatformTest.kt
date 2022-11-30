package com.snowdango.db.dao.platform

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.db.mock.MockPlatform
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
class InsertPlatformTest {

    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, SongHistoryDatabase::class.java
        ).build()
    }


    @Test
    fun insertPlatform() = runBlocking {
        assertEquals(db.platformDao.getCount(), 0)
        db.platformDao.insertPlatform(MockPlatform.singleData())
        assertEquals(db.platformDao.getCount(), 1)
    }

    @Test
    fun insertPlatforms() = runBlocking {
        val defCount = db.platformDao.getCount()
        db.platformDao.insertPlatforms(MockPlatform.dataList)
        assertEquals(db.platformDao.getCount(), defCount + MockPlatform.dataList.size)
    }

    @After
    fun close() {
        db.close()
    }

}