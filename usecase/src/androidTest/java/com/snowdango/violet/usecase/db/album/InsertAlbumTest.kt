package com.snowdango.violet.usecase.db.album

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.SongHistoryDatabase
import com.snowdango.violet.usecase.db.mock.MockAlbum
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

    private lateinit var writeAlbum: WriteAlbum
    private lateinit var getAlbum: GetAlbum
    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SongHistoryDatabase::class.java
        ).build()
        writeAlbum = WriteAlbum(db)
        getAlbum = GetAlbum(db)
    }

    @Test
    fun insertAlbum() = runBlocking {
        assertEquals(getAlbum.getAlbumCount(), 0)
        writeAlbum.insertAlbum(MockAlbum.singleData())
        assertEquals(getAlbum.getAlbumCount(), 1)
    }

    @Test
    fun insertAlbums() = runBlocking {
        val defCount = getAlbum.getAlbumCount()
        writeAlbum.insertAlbums(MockAlbum.dataList)
        assertEquals(getAlbum.getAlbumCount(), MockAlbum.dataList.size + defCount)
    }

    @After
    fun close() {
        db.close()
    }

}