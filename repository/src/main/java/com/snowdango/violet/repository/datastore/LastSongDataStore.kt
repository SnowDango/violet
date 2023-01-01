package com.snowdango.violet.repository.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.platform.PlatformType
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LastSongDataStore(private val context: Context) : KoinComponent {

    private val dataStore: DataStore<Preferences> by inject()

    private val titleKey = stringPreferencesKey("last_title")
    private val artistKey = stringPreferencesKey("last_artist")
    private val albumKey = stringPreferencesKey("last_album")
    private val mediaIdKey = stringPreferencesKey("last_media_id")
    private val albumArtist = stringPreferencesKey("last_album_artist")
    private val platform = stringPreferencesKey("last_platform")
    private val queueIdKey = longPreferencesKey("last_queue_id")
    private val genreKey = stringPreferencesKey("last_genre")

    suspend fun saveLastSong(lastSong: LastSong) {
        dataStore.edit { preferences: MutablePreferences ->
            preferences[titleKey] = lastSong.title ?: ""
            preferences[artistKey] = lastSong.artist ?: ""
            preferences[albumKey] = lastSong.album ?: ""
            preferences[mediaIdKey] = lastSong.mediaId ?: ""
            preferences[albumArtist] = lastSong.albumArtist ?: ""
            preferences[platform] = lastSong.platform?.name ?: ""
            preferences[queueIdKey] = lastSong.queueId ?: 0
            preferences[genreKey] = lastSong.genre ?: ""
        }
    }

    suspend fun getLastSong(): LastSong {
        val preferences = dataStore.data.first()
        return LastSong(
            preferences[mediaIdKey],
            preferences[titleKey],
            preferences[artistKey],
            preferences[albumKey],
            preferences[albumArtist],
            PlatformType.values().lastOrNull { it.packageName == preferences[platform] }
                ?: PlatformType.AppleMusic,
            preferences[queueIdKey],
            preferences[genreKey]
        )
    }
}