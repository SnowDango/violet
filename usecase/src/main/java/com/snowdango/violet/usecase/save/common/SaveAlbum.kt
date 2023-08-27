package com.snowdango.violet.usecase.save.common

import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.response.apple.AppleMusicSongResult
import com.snowdango.violet.domain.response.songlink.SongEntity
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.repository.file.ArtworkFileManager
import com.snowdango.violet.usecase.db.album.GetAlbum
import com.snowdango.violet.usecase.db.album.WriteAlbum
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.io.path.Path

class SaveAlbum(val db: SongHistoryDatabase) : KoinComponent {

    private val artworkFileManager: ArtworkFileManager by inject()

    suspend fun saveAlbumWithLastSong(
        data: LastSong,
        albumArtistId: Long,
        thumbnailUrl: String? = null,
        thumbnailBase64: String? = null
    ): AlbumSaveResult {
        if (data.album == null) return AlbumSaveResult(-1L, null)
        return saveAlbum(data.album!!, albumArtistId, thumbnailUrl, thumbnailBase64)
    }

    suspend fun saveAlbumWithSongLink(
        data: LastSong,
        songEntity: SongEntity?,
        albumArtistId: Long,
        thumbnail: String?
    ): AlbumSaveResult {
        return saveAlbumWithLastSong(
            data,
            albumArtistId,
            songEntity?.thumbnailUrl ?: thumbnail,
            data.album
        )
    }

    suspend fun saveAlbumWithAppleMusic(
        data: LastSong,
        result: AppleMusicSongResult,
        albumArtistId: Long,
        thumbnail: String? = null
    ): AlbumSaveResult {
        if (data.album == null && result.collectionName == null) return AlbumSaveResult(-1L, null)

        return saveAlbum(
            result.collectionName ?: data.album,
            albumArtistId,
            generateThumbnailUrl(result.artworkUrl100 ?: result.artworkUrl60 ?: result.artworkUrl30, thumbnail),
            data.artwork
        )
    }

    suspend fun saveAlbum(
        albumName: String?,
        albumArtistId: Long,
        thumbnailUrl: String?,
        thumbnailBase64: String? = null
    ): AlbumSaveResult {
        if (albumName == null) return AlbumSaveResult(-1L, null)
        val getAlbum = GetAlbum(db)
        val album = getAlbum.getAlbumByTitleAndArtistId(albumName, albumArtistId)
        return if (album == null) {
            val artwork = thumbnailUrl ?: artworkFileManager.saveAlbumFile(thumbnailBase64)
            val writeAlbum = WriteAlbum(db)
            AlbumSaveResult(writeAlbum.insertAlbum(albumName, albumArtistId, artwork ?: ""), artwork)
        } else {
            AlbumSaveResult(album.id, album.thumbnailUrl)
        }
    }

    private fun generateThumbnailUrl(url: String?, thumbnail: String?): String? {
        return if (url != null) {
            val path = Path(url)
            url.replace(path.fileName.toString(), "500x500.jpg")
        } else {
            thumbnail
        }
    }

    data class AlbumSaveResult(val albumId: Long, val thumbnailUrl: String?)

}