package com.snowdango.db.dao.song

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.db.mock.MockPlatform
import com.snowdango.db.mock.MockSong
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
class GetSongWithPlatforms {

    private lateinit var db: SongHistoryDatabase

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SongHistoryDatabase::class.java
        ).build()
    }

    @Test
    fun getSongWithPlatforms() = runBlocking {
        db.songDao.insertSongs(MockSong.dataList)
        db.platformDao.insertPlatforms(MockPlatform.dataList)
        val songWithPlatforms = db.songDao.getSongWithPlatforms(id = 1).first()
        val platforms = MockPlatform.dataList.filter { it.songId == 1L }
        assertEquals(songWithPlatforms.platforms.size, platforms.size)
        songWithPlatforms.platforms.forEach {
            assertEquals(platforms.any { mock -> mock.mediaId == it.mediaId }, true)
        }
    }

    @Test
    fun getSongsWithPlatformsLimit100() = runBlocking {
        db.songDao.insertSongs(MockSong.over100Data())
        db.platformDao.insertPlatforms(MockPlatform.over100Data())
        val songsWithPlatforms = db.songDao.getSongsWithPlatforms(0)
        assertEquals(songsWithPlatforms.size, 100)
    }

    @After
    fun clear() {
        db.close()
    }

}