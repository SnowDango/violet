package com.snowdango.violet.usecase.db.song

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.violet.repository.SongHistoryDatabase
import com.snowdango.violet.usecase.db.mock.MockPlatform
import com.snowdango.violet.usecase.db.mock.MockSong
import com.snowdango.violet.usecase.db.platform.GetPlatform
import com.snowdango.violet.usecase.db.platform.WritePlatform
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GetSongWithPlatformTest {

    private lateinit var writeSong: WriteSong
    private lateinit var getSong: GetSong
    private lateinit var writePlatform: WritePlatform
    private lateinit var getPlatform: GetPlatform
    private lateinit var db: SongHistoryDatabase

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SongHistoryDatabase::class.java
        ).build()
        writeSong = WriteSong(db)
        getSong = GetSong(db)
        writePlatform = WritePlatform(db)
        getPlatform = GetPlatform(db)
    }

    @Test
    fun getSongWithPlatforms() = runBlocking {
        writeSong.insertSongs(MockSong.dataList)
        writePlatform.insertPlatforms(MockPlatform.dataList)
        val songWithPlatforms = getSong.getSongWithPlatforms(1)
        val platforms = MockPlatform.dataList.filter { it.songId == 1L }
        songWithPlatforms?.platforms?.forEach {
            assertEquals(platforms.any { mock -> mock.mediaId == it.mediaId }, true)
        }
        assertEquals(songWithPlatforms?.platforms?.size, platforms.size)
    }

    @Test
    fun getSongsWithPlatforms() = runBlocking {
        writeSong.insertSongs(MockSong.over100Data())
        writePlatform.insertPlatforms(MockPlatform.over100Data())
        val songsWithPlatforms = getSong.getSongsWithPlatforms(0, 100)
        assertEquals(songsWithPlatforms.size, 100)
    }

    @After
    fun clear() {
        db.close()
    }

}