package com.snowdango.violet.usecase.db.song

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.SongHistoryDatabase
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
class DeleteSongTest {

    private lateinit var writeSong: WriteSong
    private lateinit var getSong: GetSong
    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SongHistoryDatabase::class.java
        ).build()
        writeSong = WriteSong(db)
        getSong = GetSong(db)
    }

    @Test
    fun deleteTest() = runBlocking {
        writeSong.insertSong(MockSong.singleData())
        assertEquals(getSong.getSongCount(), 1)
        writeSong.deleteSongById(id = 1)
        assertEquals(getSong.getSongCount(), 0)
    }

    @After
    fun close() {
        db.close()
    }

}