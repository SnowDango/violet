package com.snowdango.violet.usecase.db.platform

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.SongHistoryDatabase
import com.snowdango.violet.usecase.db.mock.MockPlatform
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

    private lateinit var writePlatform: WritePlatform
    private lateinit var getPlatform: GetPlatform
    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SongHistoryDatabase::class.java
        ).build()
        writePlatform = WritePlatform(db)
        getPlatform = GetPlatform(db)
    }

    @Test
    fun getPlatform() = runBlocking {
        val mock = MockPlatform.singleData()
        writePlatform.insertPlatform(mock)
        assertEquals(getPlatform.getPlatform(1)?.mediaId, mock.mediaId)
    }

    @Test
    fun getPlatforms() = runBlocking {
        writePlatform.insertPlatforms(MockPlatform.dataList)
        assertEquals(
            getPlatform.getPlatformByMediaId("niophoikjolfasf")?.url,
            MockPlatform.dataList.filter { it.mediaId == "niophoikjolfasf" }.first().url
        )
    }

    @After
    fun close() {
        db.close()
    }
}