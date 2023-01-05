package com.snowdango.violet.usecase.db.platform

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.domain.platform.PlatformType
import com.snowdango.violet.repository.db.SongHistoryDatabase
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
class ContainsPlatformTest {

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
    fun containsTest() = runBlocking {
        writePlatform.insertPlatform(MockPlatform.singleData())
        assertEquals(getPlatform.containsById(1), true)
        assertEquals(getPlatform.containsById(2), false)
    }

    @Test
    fun containsByMediaIdTest() = runBlocking {
        val mock = MockPlatform.singleData()
        writePlatform.insertPlatform(mock)
        assertEquals(getPlatform.containsByMediaId(mock.mediaId, mock.platform!!), true)
        assertEquals(getPlatform.containsByMediaId("test", PlatformType.Spotify), false)
    }

    @After
    fun close() {
        db.close()
    }

}