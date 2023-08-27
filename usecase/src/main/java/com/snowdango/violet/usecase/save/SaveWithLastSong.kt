package com.snowdango.violet.usecase.save

import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.repository.file.ArtworkFileManager
import com.snowdango.violet.usecase.save.common.SaveAlbum
import com.snowdango.violet.usecase.save.common.SaveArtist
import com.snowdango.violet.usecase.save.common.SavePlatform
import com.snowdango.violet.usecase.save.common.SaveSong
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class SaveWithLastSong : KoinComponent {

    private val db: SongHistoryDatabase by inject()
    private val artworkFileManager: ArtworkFileManager by inject()

    suspend fun saveLastSong(data: LastSong): Long? {
        Timber.d("saveLastSong")
        return try {
            val albumArtistId = saveAlbumArtist(data)
            val albumId = saveAlbum(data, albumArtistId)
            val artistId = saveSongArtist(data)
            val songId = saveSong(data, artistId, albumId.albumId, albumId.thumbnailUrl)
            savePlatform(songId, data)
            songId
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun saveAlbumArtist(data: LastSong): Long {
        val saveArtist = SaveArtist(db)
        return saveArtist.saveAlbumArtistWithLastSong(data)
    }

    private suspend fun saveSongArtist(data: LastSong): Long {
        val saveArtist = SaveArtist(db)
        return saveArtist.saveSongArtistWithLastSong(data)
    }

    private suspend fun saveAlbum(data: LastSong, artistAlbumId: Long): SaveAlbum.AlbumSaveResult {
        val saveAlbum = SaveAlbum(db)
        return saveAlbum.saveAlbumWithLastSong(data, artistAlbumId, null, data.artwork)
    }

    private suspend fun saveSong(data: LastSong, artistId: Long, albumId: Long, thumbnailUrl: String?): Long {
        val saveSong = SaveSong(db)
        return saveSong.saveSongWithLastSong(data, artistId, albumId, thumbnailUrl)
    }

    private suspend fun savePlatform(songId: Long, data: LastSong) {
        val savePlatform = SavePlatform(db)
        savePlatform.savePlatformWithLastSong(songId, data)
    }

}