package com.snowdango.violet.repository.dao.album

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.SongHistoryDatabase
import com.snowdango.violet.repository.mock.MockAlbum
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GetAlbumTest {
    private lateinit var db: SongHistoryDatabase

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SongHistoryDatabase::class.java
        ).build()
    }

    @Test
    fun getByIdAlbum() = runBlocking {
        val mockList = MockAlbum.dataList
        db.albumDao.insertAlbums(mockList)
        val single = db.albumDao.getAlbum(id = 5)
        assertEquals(single.first().title, mockList[4].title)
    }

    @Test
    fun getAlbumLimit100() = runBlocking {
        db.albumDao.insertAlbums(MockAlbum.over100Data())
        assertEquals(db.albumDao.getAlbumListLimit100(1).size, 100)
    }

    @After
    fun close() {
        db.close()
    }
}