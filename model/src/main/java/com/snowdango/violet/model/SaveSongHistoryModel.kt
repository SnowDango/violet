package com.snowdango.violet.model

import com.snowdango.violet.domain.entity.albums.Album
import com.snowdango.violet.domain.entity.artists.Artist
import com.snowdango.violet.domain.entity.histories.History
import com.snowdango.violet.domain.entity.platforms.Platform
import com.snowdango.violet.domain.entity.songs.Song
import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.platform.PlatformType
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

// TODO need refactoring

class SaveSongHistoryModel : KoinComponent {

    private val db: SongHistoryDatabase by inject()
    private val apiRepository: ApiRepository by inject()

    suspend fun saveSongHistory(data: LastSong) {

        val checkLastSong = CheckLastSong()
        val isChange = checkLastSong.checkLastSong(data)
        if (isChange) {
            getSongData(data)
        }
    }

    private suspend fun getSongData(data: LastSong) {
        data.platform?.songLink?.let { platform ->
            data.mediaId?.let { mediaId ->
                Timber.d(data.toString())
                val getPlatform = GetPlatform(db)
                val isAlreadySaveMeta = getPlatform.containsByMediaId(mediaId, data.platform!!)
                // is already save meta data
                val songId: Long? = if (!isAlreadySaveMeta) {
                    val response = apiRepository.getSongLink(platform, id = mediaId, type = "song")
                    if (response != null) {
                        // request songlink api
                        val songLinkData = response.entitiesByUniqueId[response.entityUniqueId]
                        //save album artist
                        val albumArtistId = saveArtist(data.albumArtist)
                        // save album
                        val albumId =
                            saveAlbum(data.album, albumArtistId, songLinkData?.thumbnailUrl ?: "")
                        //save song artist
                        val artistId = saveArtist(data.artist ?: songLinkData?.artistName)
                        //save song
                        val songId = saveSong(
                            data.title ?: songLinkData?.title,
                            artistId,
                            albumId,
                            songLinkData?.thumbnailUrl,
                            data.genre
                        )
                        // save platform
                        response.entitiesByUniqueId.filter {
                            PlatformType.values().any { type ->
                                it.key.startsWith(type.songLinkEntityString)
                            }
                        }.forEach { (key, songEntity) ->
                            val platformType = PlatformType.values()
                                .first { type -> key.startsWith(type.songLinkEntityString) }
                            val linkPlatform = songEntity.platforms.first {
                                PlatformType.values().any { platform -> platform.songLink == it }
                            }
                            savePlatform(
                                songId,
                                platformType,
                                getMediaIdByUniqueId(songEntity.id, platformType),
                                response.linksByPlatform[linkPlatform]?.url ?: ""
                            )
                        }
                        songId
                    } else {
                        // TODO not api response work
                        null
                    }
                } else {
                    getPlatform.getPlatformWithSong(mediaId)?.song?.firstOrNull()?.id
                }
                //save history
                songId?.let {
                    saveHistory(it, mediaId)
                }
            }
        }
    }

    private suspend fun saveArtist(name: String?): Long = withContext(Dispatchers.IO) {
        if (name == null) return@withContext -1
        val getArtist = GetArtist(db)
        val artist = getArtist.getArtistByName(name)
        return@withContext if (artist == null) {
            val writeArtist = WriteArtist(db)
            writeArtist.insertArtist(Artist(name = name))
        } else {
            artist.id
        }
    }

    private suspend fun saveAlbum(title: String?, artistId: Long, thumbnail: String): Long =
        withContext(Dispatchers.IO) {
            if (title == null) return@withContext -1
            val getAlbum = GetAlbum(db)
            val album = getAlbum.getAlbumByTitle(title)
            return@withContext if (album == null) {
                val writeAlbum = WriteAlbum(db)
                writeAlbum.insertAlbum(
                    Album(
                        title = title,
                        artistId = artistId,
                        thumbnailUrl = thumbnail
                    )
                )
            } else {
                album.id
            }
        }

    private suspend fun saveSong(
        title: String?,
        artistId: Long,
        albumId: Long,
        thumbnail: String?,
        genre: String?
    ): Long = withContext(Dispatchers.IO) {
        if (title == null) return@withContext -1
        val writeSong = WriteSong(db)
        return@withContext writeSong.insertSong(
            Song(
                title = title,
                artistId = artistId,
                albumId = albumId,
                thumbnailUrl = thumbnail ?: "",
                genre = genre ?: ""
            )
        )
    }

    private suspend fun savePlatform(
        songId: Long,
        platformType: PlatformType,
        mediaId: String,
        url: String,
    ) = withContext(Dispatchers.IO) {
        val writePlatform = WritePlatform(db)
        writePlatform.insertPlatform(
            Platform(
                songId = songId,
                platform = platformType,
                mediaId = mediaId,
                url = url
            )
        )
    }

    private suspend fun getMediaIdByUniqueId(
        uniqueId: String,
        platformType: PlatformType
    ): String {
        return uniqueId.replace(platformType.songLinkEntityString + "::", "")
    }

    private suspend fun saveHistory(songId: Long, mediaId: String) = withContext(Dispatchers.IO) {
        val writeHistory = WriteHistory(db)
        val clock = Clock.System.now()
        writeHistory.insertHistory(
            History(
                songId = songId,
                dateTime = clock.toLocalDateTime(TimeZone.currentSystemDefault()),
                mediaId = mediaId
            )
        )
    }
}