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
class InsertAlbumTest {

    private lateinit var db: SongHistoryDatabase

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SongHistoryDatabase::class.java
        ).build()
    }


    @Test
    fun insertAlbum() = runBlocking {
        assertEquals(db.albumDao.getCount(), 0)
        val mockData = MockAlbum.singleData()
        db.albumDao.insertAlbum(mockData)
        assertEquals(db.albumDao.getCount(), 1)
    }

    @Test
    fun insertAlbums() = runBlocking {
        val defCount = db.albumDao.getCount()
        val mockData = MockAlbum.over100Data()
        db.albumDao.insertAlbums(mockData)
        assertEquals(db.albumDao.getCount(), defCount + mockData.size)
    }

    @After
    fun close() {
        db.close()
    }

}