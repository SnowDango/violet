package com.snowdango.violet.usecase.db.album

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.SongHistoryDatabase
import com.snowdango.violet.usecase.db.artist.GetArtist
import com.snowdango.violet.usecase.db.artist.WriteArtist
import com.snowdango.violet.usecase.db.mock.MockAlbum
import com.snowdango.violet.usecase.db.mock.MockArtist
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GetAlbumWithArtistTest {

    private lateinit var writeAlbum: WriteAlbum
    private lateinit var getAlbum: GetAlbum

    private lateinit var writeArtist: WriteArtist
    private lateinit var getArtist: GetArtist
    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SongHistoryDatabase::class.java
        ).build()
        writeAlbum = WriteAlbum(db)
        getAlbum = GetAlbum(db)
        writeArtist = WriteArtist(db)
        getArtist = GetArtist(db)
    }


    @Test
    fun getAlbumWithArtist() = runBlocking {
        writeAlbum.insertAlbums(MockAlbum.dataList)
        writeArtist.insertArtists(MockArtist.dataList)
        val albumWithArtist = getAlbum.getAlbumWithArtist(1)
        val artist = getArtist.getArtist(MockAlbum.dataList.first().artistId)
        assert(albumWithArtist?.artist?.name == artist?.name)
    }

    @Test
    fun getAlbumsWithArtist() = runBlocking {
        writeAlbum.insertAlbums(MockAlbum.over100Data())
        writeArtist.insertArtists(MockArtist.over100Data())
        val albumWithArtist = getAlbum.getAlbumsWithArtist(0, 100)
        assert(albumWithArtist.size == 100)
    }

    @After
    fun clear() {
        db.close()
    }
    
}