package com.snowdango.violet.model

import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.platform.PlatformType
import com.snowdango.violet.domain.response.SongApiResponse
import com.snowdango.violet.domain.response.SongEntity
import com.snowdango.violet.repository.api.ApiRepository
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.datastore.CheckLastSong
import com.snowdango.violet.usecase.db.album.GetAlbum
import com.snowdango.violet.usecase.db.album.WriteAlbum
import com.snowdango.violet.usecase.db.artist.GetArtist
import com.snowdango.violet.usecase.db.artist.WriteArtist
import com.snowdango.violet.usecase.db.history.WriteHistory
import com.snowdango.violet.usecase.db.platform.GetPlatform
import com.snowdango.violet.usecase.db.platform.WritePlatform
import com.snowdango.violet.usecase.db.song.WriteSong
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// TODO need refactoring

class SaveSongHistoryModel : KoinComponent {

    private val db: SongHistoryDatabase by inject()
    private val apiRepository: ApiRepository by inject()


    suspend fun saveSongHistory(data: LastSong, platformType: PlatformType) {

        val checkLastSong = CheckLastSong()
        val isChange = checkLastSong.checkLastSong(data, platformType)
        if (isChange) {
            getSongData(data, platformType)
        }
    }

    private suspend fun isAlreadySaved(mediaId: String, platformType: PlatformType): Boolean {
        val getPlatform = GetPlatform(db)
        return getPlatform.containsByMediaId(mediaId, platformType)
    }

    private suspend fun getSongLinkData(platform: String, mediaId: String): SongApiResponse? {
        return apiRepository.getSongLink(platform, id = mediaId, type = "song")
    }


    private suspend fun getSongData(data: LastSong, platformType: PlatformType) {
        data.platform?.songLink?.let { platform ->
            data.mediaId?.let { mediaId ->
                // is already save meta data
                val songId: Long? = if (!isAlreadySaved(mediaId, data.platform!!)) {
                    val response = getSongLinkData(platform, mediaId)
                    if (response != null) {
                        // request songlink api
                        val songLinkData = response.entitiesByUniqueId[response.entityUniqueId]
                        //save album artist
                        val albumArtistId = saveArtist(data)
                        // save album
                        val albumId = saveAlbum(data, songLinkData, albumArtistId)
                        //save song artist
                        val artistId = saveArtist(data, songLinkData)
                        //save song
                        val songId = saveSong(data, songLinkData, albumId, artistId)
                        // save platform
                        response.entitiesByUniqueId.filter {
                            PlatformType.values().any { type ->
                                it.key.startsWith(type.songLinkEntityString)
                            }
                        }.forEach { (key, songEntity) ->
                            savePlatform(songId, key, songEntity, response)
                        }
                        songId
                    } else {
                        // TODO not api response work
                        null
                    }
                } else {
                    val getPlatform = GetPlatform(db)
                    getPlatform.getPlatformWithSong(mediaId)?.song?.firstOrNull()?.id
                }
                //save history
                songId?.let {
                    saveHistory(it, platformType, data.dateTime!!)
                }
            }
        }
    }

    private suspend fun saveArtist(data: LastSong): Long {
        if (data.albumArtist == null) return -1L
        val getArtist = GetArtist(db)
        val artist = getArtist.getArtistByName(data.albumArtist!!)
        return if (artist == null) {
            val writeArtist = WriteArtist(db)
            writeArtist.insertArtist(data.albumArtist)
        } else {
            artist.id
        }
    }

    private suspend fun saveArtist(data: LastSong, songEntity: SongEntity?): Long {
        if (data.albumArtist == null && songEntity?.artistName == null) return -1L
        val getArtist = GetArtist(db)
        val artist = getArtist.getArtistByName(data.albumArtist ?: songEntity?.artistName!!)
        return if (artist == null) {
            val writeArtist = WriteArtist(db)
            writeArtist.insertArtist(data.albumArtist)
        } else {
            artist.id
        }
    }

    private suspend fun saveAlbum(
        data: LastSong,
        songLinkData: SongEntity?,
        albumArtistId: Long
    ): Long {
        if (data.album == null) return -1L
        val getAlbum = GetAlbum(db)
        val album = getAlbum.getAlbumByTitle(data.album!!)
        return if (album == null) {
            val writeAlbum = WriteAlbum(db)
            writeAlbum.insertAlbum(
                data.title,
                albumArtistId,
                songLinkData?.thumbnailUrl ?: ""
            )
        } else {
            album.id
        }
    }

    private suspend fun saveSong(
        data: LastSong,
        songLinkData: SongEntity?,
        artistId: Long,
        albumId: Long
    ): Long {
        if (data.title == null) return -1L
        val getSong = WriteSong(db)
        return getSong.insertSong(
            data.title,
            artistId,
            albumId,
            songLinkData?.thumbnailUrl,
            data.genre
        )
    }

    private suspend fun savePlatform(
        songId: Long,
        songLinkId: String,
        songEntity: SongEntity,
        songApiResponse: SongApiResponse
    ): Long {
        val platformType = PlatformType.values()
            .first { type -> songLinkId.startsWith(type.songLinkEntityString) }
        val linkPlatform = songEntity.platforms.first {
            PlatformType.values().any { platform -> platform.songLink == it }
        }
        val writePlatform = WritePlatform(db)
        return writePlatform.insertPlatform(
            songId,
            platformType,
            getMediaIdByUniqueId(songEntity.id, platformType),
            songApiResponse.linksByPlatform[linkPlatform]?.url ?: ""
        )
    }

    private fun getMediaIdByUniqueId(
        uniqueId: String,
        platformType: PlatformType
    ): String {
        return uniqueId.replace(platformType.songLinkEntityString + "::", "")
    }

    private suspend fun saveHistory(
        songId: Long,
        platformType: PlatformType,
        datetime: Long
    ): Long {
        val writeHistory = WriteHistory(db)
        return writeHistory.insertHistory(songId, platformType, datetime)
    }
}