package com.snowdango.violet.usecase.save.common

import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.response.apple.AppleMusicSongResult
import com.snowdango.violet.domain.response.songlink.SongEntity
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.db.artist.GetArtist
import com.snowdango.violet.usecase.db.artist.WriteArtist

class SaveArtist(private val db: SongHistoryDatabase) {

    suspend fun saveAlbumArtistWithLastSong(data: LastSong): Long {
        if (data.albumArtist == null) return -1L
        return saveArtist(data.albumArtist!!)
    }

    suspend fun saveSongArtistWithLastSong(data: LastSong): Long {
        if (data.artist == null) return -1L
        return saveArtist(data.artist!!)
    }

    suspend fun saveSongArtistWithSongLink(data: LastSong, songEntity: SongEntity?): Long {
        if (data.artist == null && songEntity?.artistName == null) return -1L
        return saveArtist(data.artist ?: songEntity?.artistName!!)
    }

    suspend fun saveSongArtistWithAppleMusic(data: LastSong, result: AppleMusicSongResult): Long {
        if (data.artist == null && result.artistName == null) return -1L
        return saveArtist(data.artist ?: result.artistName!!)
    }

    suspend fun saveArtist(name: String): Long {
        val getArtist = GetArtist(db)
        val artist = getArtist.getArtistByName(name)
        return if (artist == null) {
            val writeArtist = WriteArtist(db)
            writeArtist.insertArtist(name)
        } else {
            artist.id
        }
    }
}