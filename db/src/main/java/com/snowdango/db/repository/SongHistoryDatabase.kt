package com.snowdango.db.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.snowdango.db.domain.entity.albums.Album
import com.snowdango.db.domain.entity.albums.AlbumTitleFts
import com.snowdango.db.domain.entity.artists.Artist
import com.snowdango.db.domain.entity.histories.History
import com.snowdango.db.domain.entity.platforms.Platform
import com.snowdango.db.domain.entity.songs.Song
import com.snowdango.db.domain.entity.songs.SongTitleFts
import com.snowdango.db.repository.converter.LocalDateTimeConverter
import com.snowdango.db.repository.dao.*

const val DatabaseName = "song_history_db"

@Database(
    entities = [
        Album::class,
        Song::class,
        Artist::class,
        History::class,
        Platform::class,
        AlbumTitleFts::class,
        SongTitleFts::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class SongHistoryDatabase : RoomDatabase() {

    abstract val songDao: SongDao
    abstract val artistDao: ArtistDao
    abstract val albumDao: AlbumDao
    abstract val platformDao: PlatformDao
    abstract val historyDao: HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: SongHistoryDatabase? = null

        fun getInstance(context: Context): SongHistoryDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    SongHistoryDatabase::class.java,
                    DatabaseName
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }

}