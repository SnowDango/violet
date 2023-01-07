package com.snowdango.violet.usecase.save.common

import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.response.songlink.SongEntity
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.db.song.WriteSong

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
        return saveSongWithLastSong(
            data,
            artistId,
            albumId,
            songEntity?.thumbnailUrl,
            data.genre
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

}