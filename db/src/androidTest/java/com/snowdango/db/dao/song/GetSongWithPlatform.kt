package com.snowdango.db.dao.song

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.snowdango.db.repository.SongHistoryDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GetSongWithPlatform {

    private lateinit var db: SongHistoryDatabase

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), SongHistoryDatabase::class.java
        ).build()
    }

    @Test
    fun getSongWithPlatform() = runBlocking {
        // TODO test
    }

    @After
    fun clear() {
        db.close()
    }

}