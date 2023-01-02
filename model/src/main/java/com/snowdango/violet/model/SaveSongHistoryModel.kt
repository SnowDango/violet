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
                val isAlreadySaveMeta = getPlatform.containsByMediaId(mediaId, data.platform!!.name)
                val songId: Long? = if (!isAlreadySaveMeta) {
                    val response = apiRepository.getSongLink(platform, id = mediaId, type = "song")
                    if (response != null) {
                        val songLinkData = response.entitiesByUniqueId[response.entityUniqueId]
                        val albumArtistId = saveArtist(data.albumArtist)
                        val albumId =
                            saveAlbum(data.album, albumArtistId, songLinkData?.thumbnailUrl ?: "")
                        val artistId = saveArtist(songLinkData?.artistName ?: data.artist)
                        val songId = saveSong(
                            songLinkData?.title ?: data.title,
                            artistId,
                            albumId,
                            songLinkData?.thumbnailUrl,
                            data.genre
                        )
                        response.linksByPlatform.filter {
                            PlatformType.values().any { type -> type.songLink == it.key }
                        }.forEach { (key, platform) ->
                            val platformType =
                                PlatformType.values().first { type -> type.songLink == key }
                            savePlatform(
                                songId,
                                platformType.name,
                                getMediaIdByUniqueId(platform.entityUniqueId, platformType),
                                platform.url
                            )
                        }
                        songId
                    } else {
                        null
                    }
                } else {
                    getPlatform.getPlatformWithSong(mediaId)?.song?.firstOrNull()?.id
                }
                songId?.let {
                    saveHistory(it)
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
        platform: String,
        mediaId: String,
        url: String,
    ) = withContext(Dispatchers.IO) {
        val writePlatform = WritePlatform(db)
        writePlatform.insertPlatform(
            Platform(
                songId = songId,
                platform = platform,
                mediaId = mediaId,
                url = url
            )
        )
    }

    private suspend fun getMediaIdByUniqueId(
        uniqueId: String,
        platformType: PlatformType
    ): String {
        return when (platformType) {
            PlatformType.AppleMusic -> {
                uniqueId.replace("ITUNES_SONG::", "")
            }
            PlatformType.Spotify -> {
                uniqueId.replace("SPOTIFY_SONG::", "")
            }
        }
    }

    private suspend fun saveHistory(songId: Long) = withContext(Dispatchers.IO) {
        val writeHistory = WriteHistory(db)
        val clock = Clock.System.now()
        writeHistory.insertHistory(
            History(
                songId = songId,
                dateTime = clock.toLocalDateTime(TimeZone.currentSystemDefault())
            )
        )
    }
}