package com.snowdango.db.dao

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
class TestArtistDao {

    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, SongHistoryDatabase::class.java
        ).build()
    }

    @Test
    fun insertAndGetAndDelete() = runBlocking {
        val mockData = MockArtist.singleData()
        db.artistDao.insertArtist(mockData)
        val singleList = db.artistDao.getArtist(id = 1)
        println(singleList.toString())
        assertEquals(singleList.first().name, mockData.name)
        db.artistDao.deleteArtist(singleList.first())
        val count = db.artistDao.getCount()
        assertEquals(count, 0)
    }

    @Test
    fun over100DataInsert() = runBlocking {
        val mockList = MockArtist.over100Data()
        db.artistDao.insertArtists(mockList)
        val count = db.artistDao.getCount()
        assertEquals(count, mockList.size.toLong())
    }

    @After
    fun clear() {
        db.close()
    }
}