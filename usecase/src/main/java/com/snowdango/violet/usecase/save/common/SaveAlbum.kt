package com.snowdango.violet.usecase.save.common

import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.response.apple.AppleMusicSongResult
import com.snowdango.violet.domain.response.songlink.SongEntity
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.db.album.GetAlbum
import com.snowdango.violet.usecase.db.album.WriteAlbum
import kotlin.io.path.Path

class SaveAlbum(val db: SongHistoryDatabase) {

    suspend fun saveAlbumWithLastSong(
        data: LastSong,
        albumArtistId: Long,
        thumbnailUrl: String? = null
    ): Long {
        if (data.album == null) return -1L
        return saveAlbum(data.album!!, albumArtistId, thumbnailUrl)
    }

    suspend fun saveAlbumWithSongLink(
        data: LastSong,
        songEntity: SongEntity?,
        albumArtistId: Long
    ): Long {
        return saveAlbumWithLastSong(
            data,
            albumArtistId,
            songEntity?.thumbnailUrl
        )
    }

    suspend fun saveAlbumWithAppleMusic(
        data: LastSong,
        result: AppleMusicSongResult,
        albumArtistId: Long
    ): Long {
        if (data.album == null && result.collectionName == null) return -1L
        return saveAlbum(
            result.collectionName ?: data.album,
            albumArtistId,
            generateThumbnailUrl(result.artworkUrl100 ?: result.artworkUrl60 ?: result.artworkUrl30)
        )
    }

    suspend fun saveAlbum(
        albumName: String?,
        albumArtistId: Long,
        thumbnailUrl: String?
    ): Long {
        if (albumName == null) return -1L
        val getAlbum = GetAlbum(db)
        val album = getAlbum.getAlbumByTitleAndArtistId(albumName, albumArtistId)
        return if (album == null) {
            val writeAlbum = WriteAlbum(db)
            writeAlbum.insertAlbum(albumName, albumArtistId, thumbnailUrl ?: "")
        } else {
            album.id
        }
    }

    private fun generateThumbnailUrl(url: String?): String? {
        return if (url != null) {
            val path = Path(url)
            url.replace(path.fileName.toString(), "500x500.jpg")
        } else {
            null
        }
    }

}