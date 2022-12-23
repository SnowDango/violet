package com.snowdango.violet.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.snowdango.violet.domain.entity.albums.Album
import com.snowdango.violet.domain.entity.albums.AlbumTitleFts
import com.snowdango.violet.domain.entity.artists.Artist
import com.snowdango.violet.domain.entity.histories.History
import com.snowdango.violet.domain.entity.platforms.Platform
import com.snowdango.violet.domain.entity.songs.Song
import com.snowdango.violet.domain.entity.songs.SongTitleFts
import com.snowdango.violet.repository.db.converter.LocalDateTimeConverter
import com.snowdango.violet.repository.db.dao.*

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