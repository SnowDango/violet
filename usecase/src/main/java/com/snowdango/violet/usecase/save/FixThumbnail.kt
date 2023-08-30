package com.snowdango.violet.usecase.save

import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.platform.PlatformType
import com.snowdango.violet.domain.relation.PlatformWithSongAndAlbum
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.repository.file.ArtworkFileManager
import com.snowdango.violet.usecase.db.album.WriteAlbum
import com.snowdango.violet.usecase.db.platform.GetPlatform
import com.snowdango.violet.usecase.db.song.WriteSong
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FixThumbnail : KoinComponent {

    val db: SongHistoryDatabase by inject()
    private val artworkFileManager: ArtworkFileManager by inject()

    suspend fun fixSongData(data: LastSong) {
        if (data.mediaId == null || data.platform == null) return
        val platformWithSongAndAlbum = getSongData(data.mediaId!!, data.platform!!) ?: return

        val targetSong = platformWithSongAndAlbum.song.firstOrNull { it.song.title == data.title }?.song
        val targetAlbum = platformWithSongAndAlbum.song.firstOrNull { it.album.title == data.album }?.album

        if (targetSong == null && targetAlbum == null) return

        if (targetSong?.thumbnailUrl.isNullOrBlank() || targetAlbum?.thumbnailUrl.isNullOrBlank()) {
            val path = artworkFileManager.saveAlbumFile(data.artwork) ?: return

            if (targetSong?.thumbnailUrl.isNullOrBlank()) {
                val writeSong = WriteSong(db)
                writeSong.updateThumbnailUrl(targetSong!!.id, path)
            }

            if (targetAlbum?.thumbnailUrl.isNullOrBlank()) {
                val writeAlbum = WriteAlbum(db)
                writeAlbum.updateThumbnailUrl(targetAlbum!!.id, path)
            }
        }

    }

    private suspend fun getSongData(mediaId: String, platformType: PlatformType): PlatformWithSongAndAlbum? {
        val getPlatform = GetPlatform(db)
        val song = getPlatform.getPlatformWithSongAndAlbum(mediaId)
        return if (song?.platform?.platform == platformType) song else null
    }

}