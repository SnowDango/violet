package com.snowdango.violet.usecase.db.album

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.db.SongHistoryDatabase
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
class GetAlbumTest {

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
    fun getByIdAlbum() = runBlocking {
        val mockList = MockAlbum.dataList
        writeAlbum.insertAlbums(mockList)
        assertEquals(getAlbum.getAlbum(id = 5)?.title, MockAlbum.dataList[4].title)
    }

    @Test
    fun getAlbums() = runBlocking {
        writeAlbum.insertAlbums(MockAlbum.over100Data())
        assertEquals(getAlbum.getAlbums(0, 100).size, 100)
    }

    @After
    fun close() {
        db.close()
    }

}