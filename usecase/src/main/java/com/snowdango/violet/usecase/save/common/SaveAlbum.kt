package com.snowdango.violet.usecase.save.common

import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.response.songlink.SongEntity
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.db.album.GetAlbum
import com.snowdango.violet.usecase.db.album.WriteAlbum

class SaveAlbum(val db: SongHistoryDatabase) {

    suspend fun saveAlbumWithLastSong(
        data: LastSong,
        albumArtistId: Long,
        thumbnailUrl: String?
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

}