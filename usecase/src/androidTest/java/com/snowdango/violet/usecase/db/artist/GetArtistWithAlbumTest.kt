package com.snowdango.violet.usecase.db.artist

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.SongHistoryDatabase
import com.snowdango.violet.usecase.db.album.GetAlbum
import com.snowdango.violet.usecase.db.album.WriteAlbum
import com.snowdango.violet.usecase.db.mock.MockAlbum
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
class GetArtistWithAlbumTest {

    private lateinit var writeArtist: WriteArtist
    private lateinit var getArtist: GetArtist
    private lateinit var writeAlbum: WriteAlbum
    private lateinit var getAlbum: GetAlbum
    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SongHistoryDatabase::class.java
        ).build()
        writeArtist = WriteArtist(db)
        getArtist = GetArtist(db)
        writeAlbum = WriteAlbum(db)
        getAlbum = GetAlbum(db)
    }

    @Test
    fun getArtistWithAlbums() = runBlocking {
        val mockArtist = MockArtist.singleData()
        writeArtist.insertArtist(mockArtist)
        writeAlbum.insertAlbums(MockAlbum.dataList)
        val artistWithAlbum = getArtist.getArtistWithAlbums(id = 1)
        val artistAlbums = MockAlbum.dataList.filter { it.artistId == 1L }
        artistWithAlbum?.albums?.forEach { album ->
            assertEquals(artistAlbums.any { it.title == album.title }, true)
        }
        assertEquals(artistWithAlbum?.albums?.size, artistAlbums.size)
    }

    @Test
    fun getArtistsWithAlbums() = runBlocking {
        writeArtist.insertArtists(MockArtist.over100Data())
        writeAlbum.insertAlbums(MockAlbum.dataList)
        assertEquals(getArtist.getArtistsWithAlbums(0, 100).size, 100)
    }

    @After
    fun close() {
        db.close()
    }

}