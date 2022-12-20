package com.snowdango.violet.usecase.db.song

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.SongHistoryDatabase
import com.snowdango.violet.usecase.db.artist.GetArtist
import com.snowdango.violet.usecase.db.artist.WriteArtist
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
class GetSongWithArtistTest {

    private lateinit var writeSong: WriteSong
    private lateinit var getSong: GetSong
    private lateinit var getArtist: GetArtist
    private lateinit var writeArtist: WriteArtist
    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SongHistoryDatabase::class.java
        ).build()
        writeSong = WriteSong(db)
        getSong = GetSong(db)
        getArtist = GetArtist(db)
        writeArtist = WriteArtist(db)
    }

    @Test
    fun getSongWithArtist() = runBlocking {
        writeSong.insertSongs(MockSong.dataList)
        writeArtist.insertArtists(MockArtist.dataList)
        val songWithArtist = getSong.getSongWithArtist(1)
        val artist = getArtist.getArtist(MockSong.dataList.first().artistId)
        assertEquals(songWithArtist?.artist?.name, artist?.name)
    }

    @Test
    fun getSongsWithArtist() = runBlocking {
        writeSong.insertSongs(MockSong.over100Data())
        writeArtist.insertArtists(MockArtist.over100Data())
        val songsWithArtist = getSong.getSongsWithArtist(0, 100)
        assertEquals(songsWithArtist.size, 100)
    }

    @After
    fun close() {
        db.close()
    }
}