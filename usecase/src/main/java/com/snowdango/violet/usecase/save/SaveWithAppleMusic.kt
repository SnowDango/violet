package com.snowdango.violet.usecase.save

import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.platform.PlatformType
import com.snowdango.violet.domain.response.apple.AppleMusicSongResult
import com.snowdango.violet.repository.api.ApiRepository
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.save.common.SaveAlbum
import com.snowdango.violet.usecase.save.common.SaveArtist
import com.snowdango.violet.usecase.save.common.SavePlatform
import com.snowdango.violet.usecase.save.common.SaveSong
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SaveWithAppleMusic : KoinComponent {

    private val db: SongHistoryDatabase by inject()
    private val apiRepository: ApiRepository by inject()

    suspend fun saveAppleMusic(data: LastSong): Long? {
        return try {
            val response = apiCall(data)
            if (response != null) {
                val albumArtistId = saveAlbumArtist(data)
                val albumId = saveAlbum(data, response, albumArtistId)
                val artistId = saveSongArtist(data, response)
                val songId = saveSong(data, response, artistId, albumId.albumId, albumId.thumbnailUrl)
                savePlatform(songId, data.mediaId!!, response)
                songId
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun apiCall(data: LastSong): AppleMusicSongResult? {
        data.mediaId?.let { mediaId ->
            return apiRepository.getAppleMusic(mediaId)?.results?.firstOrNull()
        }
        return null
    }

    private suspend fun saveAlbumArtist(data: LastSong): Long {
        val saveArtist = SaveArtist(db)
        return saveArtist.saveAlbumArtistWithLastSong(data)
    }

    private suspend fun saveSongArtist(data: LastSong, appleMusicData: AppleMusicSongResult): Long {
        val saveArtist = SaveArtist(db)
        return saveArtist.saveSongArtistWithAppleMusic(data, appleMusicData)
    }

    private suspend fun saveAlbum(
        data: LastSong,
        appleMusicData: AppleMusicSongResult,
        albumArtistId: Long
    ): SaveAlbum.AlbumSaveResult {
        val saveAlbum = SaveAlbum(db)
        return saveAlbum.saveAlbumWithAppleMusic(data, appleMusicData, albumArtistId)
    }

    private suspend fun saveSong(
        data: LastSong,
        appleMusicData: AppleMusicSongResult,
        artistId: Long,
        albumId: Long,
        thumbnail: String? = null
    ): Long {
        val saveSong = SaveSong(db)
        return saveSong.saveSongWithAppleMusic(data, artistId, albumId, appleMusicData, thumbnail)
    }

    private suspend fun savePlatform(
        songId: Long,
        mediaId: String,
        result: AppleMusicSongResult
    ) {
        val savePlatform = SavePlatform(db)
        savePlatform.savePlatform(
            songId,
            PlatformType.AppleMusic,
            mediaId,
            result.trackViewUrl
        )
    }

}