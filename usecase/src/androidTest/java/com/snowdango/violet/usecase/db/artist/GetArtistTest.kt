package com.snowdango.violet.usecase.db.artist

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.SongHistoryDatabase
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
class GetArtistTest {

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
    fun getArtist() = runBlocking {
        val mock = MockArtist.singleData()
        writeArtist.insertArtist(mock)
        val artist = getArtist.getArtist(1)
        assertEquals(artist?.name, mock.name)
    }

    @Test
    fun getArtists() = runBlocking {
        writeArtist.insertArtists(MockArtist.over100Data())
        val listArtist = getArtist.getArtists(0, 100)
        assertEquals(listArtist.size, 100)
    }

    @After
    fun close() {
        db.close()
    }
}