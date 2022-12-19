package com.snowdango.violet.repository.dao.platform

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.SongHistoryDatabase
import com.snowdango.violet.repository.mock.MockPlatform
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GetPlatformTest {

    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, SongHistoryDatabase::class.java
        ).build()
    }

    @Test
    fun getPlatform() = runBlocking {
        val mock = MockPlatform.singleData()
        db.platformDao.insertPlatform(mock)
        assertEquals(db.platformDao.getPlatform(1).first().mediaId, mock.mediaId)
    }

    @Test
    fun getPlatforms() = runBlocking {
        db.platformDao.insertPlatforms(MockPlatform.dataList)
        assertEquals(
            db.platformDao.getPlatformByMediaId("niophoikjolfasf").size,
            MockPlatform.dataList.filter { it.mediaId == "niophoikjolfasf" }.size
        )
    }

    @After
    fun close() {
        db.close()
    }
}