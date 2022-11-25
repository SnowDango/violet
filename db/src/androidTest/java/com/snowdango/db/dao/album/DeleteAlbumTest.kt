package com.snowdango.db.dao.album

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.db.mock.MockAlbum
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
class DeleteAlbumTest {

    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SongHistoryDatabase::class.java
        ).build()
    }

    @Test
    fun deleteTest() = runBlocking {
        db.albumDao.insertAlbum(MockAlbum.singleData())
        assertEquals(db.albumDao.getCount(), 1)
        db.albumDao.deleteAlbumById(id = 1)
        assertEquals(db.albumDao.getCount(), 0)
    }

    @After
    fun close() {
        db.close()
    }


}