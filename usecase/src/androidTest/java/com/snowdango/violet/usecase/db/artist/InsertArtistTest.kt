package com.snowdango.violet.usecase.db.artist

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.db.mock.MockArtist
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class InsertArtistTest {

    private lateinit var writeArtist: WriteArtist
    private lateinit var getArtist: GetArtist
    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SongHistoryDatabase::class.java
        ).build()
        writeArtist = WriteArtist(db)
        getArtist = GetArtist(db)
    }

    @Test
    fun insertArtist() = runBlocking {
        assertEquals(getArtist.getArtistCount(), 0)
        writeArtist.insertArtist(MockArtist.singleData())
        assertEquals(getArtist.getArtistCount(), 1)
    }

    @Test
    fun insertsArtist() = runBlocking {
        val defCount = getArtist.getArtistCount()
        writeArtist.insertArtists(MockArtist.dataList)
        assertEquals(getArtist.getArtistCount(), defCount + MockArtist.dataList.size)
    }

    @After
    fun close() {
        db.close()
    }

}