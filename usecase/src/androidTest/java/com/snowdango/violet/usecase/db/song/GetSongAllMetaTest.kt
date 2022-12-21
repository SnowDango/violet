package com.snowdango.violet.usecase.db.song

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.SongHistoryDatabase
import com.snowdango.violet.usecase.db.album.GetAlbum
import com.snowdango.violet.usecase.db.album.WriteAlbum
import com.snowdango.violet.usecase.db.artist.GetArtist
import com.snowdango.violet.usecase.db.artist.WriteArtist
import com.snowdango.violet.usecase.db.mock.MockAlbum
import com.snowdango.violet.usecase.db.mock.MockArtist
import com.snowdango.violet.usecase.db.mock.MockSong
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GetSongAllMetaTest {

    private lateinit var writeSong: WriteSong
    private lateinit var getSong: GetSong
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
        writeSong = WriteSong(db)
        getSong = GetSong(db)
        writeArtist = WriteArtist(db)
        getArtist = GetArtist(db)
        writeAlbum = WriteAlbum(db)
        getAlbum = GetAlbum(db)
    }

    @Test
    fun getSongAllMeta() = runBlocking {
        val song = MockSong.singleData()
        writeSong.insertSong(song)
        writeArtist.insertArtists(MockArtist.dataList)
        writeAlbum.insertAlbums(MockAlbum.dataList)
        val songAllMeta = getSong.getSongAllMeta(1)
        val artist = getArtist.getArtist(song.artistId)
        val album = getAlbum.getAlbum(song.albumId)
        assertEquals(songAllMeta?.artist?.name, artist?.name)
        assertEquals(songAllMeta?.albumWithArtist?.album?.title, album?.title)
        val albumArtist = getArtist.getArtist(album?.artistId!!)
        assertEquals(songAllMeta?.albumWithArtist?.artist?.name, albumArtist?.name)
    }

    @Test
    fun getSongsAllMeta() = runBlocking {
        writeSong.insertSongs(MockSong.over100Data())
        writeArtist.insertArtists(MockArtist.over100Data())
        writeAlbum.insertAlbums(MockAlbum.over100Data())
        val songAllMeta = getSong.getSongsAllMeta(0, 100)
        assertEquals(songAllMeta.size, 100)
    }

    @After
    fun close() {
        db.close()
    }

}