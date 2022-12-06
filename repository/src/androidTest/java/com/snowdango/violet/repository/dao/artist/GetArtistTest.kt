package com.snowdango.violet.repository.dao.artist

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.SongHistoryDatabase
import com.snowdango.violet.repository.mock.MockArtist
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GetArtistTest {

    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, SongHistoryDatabase::class.java
        ).build()
    }

    @Test
    fun getArtist() = runBlocking {
        val mock = MockArtist.singleData()
        db.artistDao.insertArtist(mock)
        val artist = db.artistDao.getArtist(id = 1).first()
        assertEquals(artist.name, mock.name)
    }

    @Test
    fun getArtists() = runBlocking {
        db.artistDao.insertArtists(MockArtist.over100Data())
        val listArtist = db.artistDao.getArtists(0, 100)
        assertEquals(listArtist.size, 100)
    }

    @After
    fun close() {
        db.close()
    }

}