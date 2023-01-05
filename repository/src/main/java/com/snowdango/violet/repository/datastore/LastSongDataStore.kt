package com.snowdango.violet.repository.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.platform.PlatformType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent

class LastSongDataStore(private val dataStore: DataStore<Preferences>) : KoinComponent {

    private fun titleKey(platformType: PlatformType) =
        stringPreferencesKey("last_title_${platformType.name}")

    private fun artistKey(platformType: PlatformType) =
        stringPreferencesKey("last_artist_${platformType.name}")

    private fun albumKey(platformType: PlatformType) =
        stringPreferencesKey("last_album_${platformType.name}")

    private fun mediaIdKey(platformType: PlatformType) =
        stringPreferencesKey("last_media_id_${platformType.name}")

    private fun albumArtistKey(platformType: PlatformType) =
        stringPreferencesKey("last_album_artist_${platformType.name}")

    private fun platformKey(platformType: PlatformType) =
        stringPreferencesKey("last_platform_${platformType.name}")

    private fun queueIdKey(platformType: PlatformType) =
        longPreferencesKey("last_queue_id_${platformType.name}")

    private fun genreKey(platformType: PlatformType) =
        stringPreferencesKey("last_genre_${platformType.name}")


    suspend fun saveLastSong(lastSong: LastSong, platformType: PlatformType) {
        dataStore.edit { preferences: MutablePreferences ->
            preferences[titleKey(platformType)] = lastSong.title ?: ""
            preferences[artistKey(platformType)] = lastSong.artist ?: ""
            preferences[albumKey(platformType)] = lastSong.album ?: ""
            preferences[mediaIdKey(platformType)] = lastSong.mediaId ?: ""
            preferences[albumArtistKey(platformType)] = lastSong.albumArtist ?: ""
            preferences[platformKey(platformType)] = lastSong.platform?.name ?: ""
            preferences[queueIdKey(platformType)] = lastSong.queueId ?: -1
            preferences[genreKey(platformType)] = lastSong.genre ?: ""
        }
    }

    suspend fun getLastSong(platformType: PlatformType): LastSong {
        val preferences = dataStore.data.first()
        return LastSong(
            preferences[mediaIdKey(platformType)],
            preferences[titleKey(platformType)],
            preferences[artistKey(platformType)],
            preferences[albumKey(platformType)],
            preferences[albumArtistKey(platformType)],
            PlatformType.values()
                .lastOrNull { it.packageName == preferences[platformKey(platformType)] }
                ?: PlatformType.AppleMusic,
            preferences[queueIdKey(platformType)],
            preferences[genreKey(platformType)]
        )
    }

    suspend fun purgeLastSong(platformType: PlatformType) {
        dataStore.edit { preferences: MutablePreferences ->
            preferences[titleKey(platformType)] = ""
            preferences[artistKey(platformType)] = ""
            preferences[albumKey(platformType)] = ""
            preferences[mediaIdKey(platformType)] = ""
            preferences[albumArtistKey(platformType)] = ""
            preferences[platformKey(platformType)] = ""
            preferences[queueIdKey(platformType)] = 0
            preferences[genreKey(platformType)] = ""
        }
    }

    fun flowLastSong() = dataStore.data.map {
        PlatformType.values().map { platformType ->
            LastSong(
                it[mediaIdKey(platformType)],
                it[titleKey(platformType)],
                it[artistKey(platformType)],
                it[albumKey(platformType)],
                it[albumArtistKey(platformType)],
                PlatformType.values()
                    .lastOrNull { type -> type.packageName == it[platformKey(platformType)] }
                    ?: PlatformType.AppleMusic,
                it[queueIdKey(platformType)],
                it[genreKey(platformType)]
            )
        }
    }
}