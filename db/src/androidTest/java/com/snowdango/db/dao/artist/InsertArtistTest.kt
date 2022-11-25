package com.snowdango.db.dao.artist

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.db.mock.MockArtist
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
class InsertArtistTest {

    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, SongHistoryDatabase::class.java
        ).build()
    }

    @Test
    fun insertArtist() = runBlocking {
        assertEquals(db.albumDao.getCount(), 0)
        db.artistDao.insertArtist(MockArtist.singleData())
        assertEquals(db.artistDao.getCount(), 1)
    }

    @Test
    fun insertArtists() = runBlocking {
        val defCount = db.artistDao.getCount()
        db.artistDao.insertArtists(MockArtist.dataList)
        assertEquals(db.artistDao.getCount(), defCount + MockArtist.dataList.size)
    }

    @After
    fun close() {
        db.close()
    }

}