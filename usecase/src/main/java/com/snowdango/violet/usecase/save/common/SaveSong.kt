package com.snowdango.violet.usecase.save.common

import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.response.apple.AppleMusicSongResult
import com.snowdango.violet.domain.response.songlink.SongEntity
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.db.song.WriteSong
import kotlin.io.path.Path

class SaveSong(private val db: SongHistoryDatabase) {

    suspend fun saveSongWithLastSong(
        data: LastSong,
        artistId: Long,
        albumId: Long,
        thumbnailUrl: String? = null,
        genre: String? = null
    ): Long {
        if (data.title == null) return 1L
        return saveSong(data.title!!, artistId, albumId, thumbnailUrl, genre)
    }

    suspend fun saveSongWithSongLink(
        data: LastSong,
        songEntity: SongEntity?,
        artistId: Long,
        albumId: Long
    ): Long {
        if (data.title == null && songEntity?.title == null) return 1L
        return saveSong(
            data.title ?: songEntity?.title!!,
            artistId,
            albumId,
            songEntity?.thumbnailUrl,
            data.genre
        )
    }

    suspend fun saveSongWithAppleMusic(
        data: LastSong,
        artistId: Long,
        albumId: Long,
        response: AppleMusicSongResult
    ): Long {
        if (data.title == null && response.trackName == null) return 1L
        return saveSong(
            response.trackName ?: data.title!!,
            artistId,
            albumId,
            generateThumbnailUrl(
                response.artworkUrl100 ?: response.artworkUrl60 ?: response.artworkUrl30
            ),
            response.primaryGenreName
        )
    }

    suspend fun saveSong(
        title: String,
        artistId: Long,
        albumId: Long,
        thumbnailUrl: String?,
        genre: String?,
    ): Long {
        val writeSong = WriteSong(db)
        return writeSong.insertSong(
            title,
            artistId,
            albumId,
            thumbnailUrl ?: "",
            genre ?: ""
        )
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