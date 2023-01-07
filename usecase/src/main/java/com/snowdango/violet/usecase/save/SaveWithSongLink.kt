package com.snowdango.violet.usecase.save

import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.response.songlink.SongApiResponse
import com.snowdango.violet.domain.response.songlink.SongEntity
import com.snowdango.violet.repository.api.ApiRepository
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.save.common.SaveAlbum
import com.snowdango.violet.usecase.save.common.SaveArtist
import com.snowdango.violet.usecase.save.common.SavePlatform
import com.snowdango.violet.usecase.save.common.SaveSong
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// when songlink api call successful

class SaveWithSongLink : KoinComponent {

    private val db: SongHistoryDatabase by inject()
    private val apiRepository: ApiRepository by inject()

    // return success or failure
    suspend fun saveSongLinkData(data: LastSong): Long? {
        return try {
            val response = apiCall(data)
            if (response != null) {
                val songLinkData = response.entitiesByUniqueId[response.entityUniqueId]
                val albumArtistId = saveAlbumArtist(data)
                val albumId = saveAlbum(data, songLinkData, albumArtistId)
                val artistId = saveSongArtist(data, songLinkData)
                val songId = saveSong(data, songLinkData, albumId, artistId)
                savePlatform(songId, response)
                songId
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun apiCall(data: LastSong): SongApiResponse? {
        data.platform?.songLink?.let { platform ->
            data.mediaId?.let { mediaId ->
                return apiRepository.getSongLink(platform, id = mediaId, type = "song")
            }
        }
        return null
    }

    private suspend fun saveAlbumArtist(data: LastSong): Long {
        val saveArtist = SaveArtist(db)
        return saveArtist.saveAlbumArtistWithLastSong(data)
    }

    private suspend fun saveSongArtist(data: LastSong, songLinkData: SongEntity?): Long {
        val saveArtist = SaveArtist(db)
        return saveArtist.saveSongArtistWithSongLink(data, songLinkData)
    }

    private suspend fun saveAlbum(
        data: LastSong,
        songLinkData: SongEntity?,
        albumArtistId: Long
    ): Long {
        val saveAlbum = SaveAlbum(db)
        return saveAlbum.saveAlbumWithSongLink(data, songLinkData, albumArtistId)
    }

    private suspend fun saveSong(
        data: LastSong,
        songLinkData: SongEntity?,
        artistId: Long,
        albumId: Long
    ): Long {
        val saveSong = SaveSong(db)
        return saveSong.saveSongWithSongLink(data, songLinkData, artistId, albumId)
    }

    private suspend fun savePlatform(
        songId: Long,
        response: SongApiResponse
    ) {
        val savePlatform = SavePlatform(db)
        savePlatform.savePlatformWithSongLink(songId, response)
    }

}