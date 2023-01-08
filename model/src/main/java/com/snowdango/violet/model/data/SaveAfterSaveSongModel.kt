package com.snowdango.violet.model.data

import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.platform.PlatformType
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.connect.ConnectManager
import com.snowdango.violet.usecase.db.after.GetAfterSaveSong
import com.snowdango.violet.usecase.db.after.WriteAfterSaveSong
import com.snowdango.violet.usecase.db.history.WriteHistory
import com.snowdango.violet.usecase.db.platform.GetPlatform
import com.snowdango.violet.usecase.save.SaveWithAppleMusic
import com.snowdango.violet.usecase.save.SaveWithLastSong
import com.snowdango.violet.usecase.save.SaveWithSongLink
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class SaveAfterSaveSongModel : KoinComponent {

    private val db: SongHistoryDatabase by inject()
    private val connectManager: ConnectManager by inject()

    private val getAfterSaveSong = GetAfterSaveSong(db)
    private val getPlatform = GetPlatform(db)

    suspend fun saveAfterSaveSong() {
        if (connectManager.isConnectInternet()) {
            getAfterSaveSong.getAllAfterSaveSong().forEach {
                Timber.d(it.toString())
                val lastSong = LastSong(
                    mediaId = it.mediaId,
                    title = it.title,
                    album = it.album,
                    artist = it.artist,
                    albumArtist = it.albumArtist,
                    platform = it.platformType,
                    queueId = -1L,
                    genre = it.genre,
                    dateTime = it.dateTime,
                    artwork = null
                )
                saveWithLastSong(lastSong, it.historyId, it.id)
            }
        }
    }

    private suspend fun saveWithLastSong(lastSong: LastSong, historyId: Long, id: Long) {
        if (lastSong.platform != null && lastSong.mediaId != null) {
            val songId: Long? = if (!isAlreadySaved(lastSong.mediaId!!, lastSong.platform!!)) {
                var songIdOrNull: Long? = null
                songIdOrNull = saveWithSongLink(lastSong)
                if (songIdOrNull == null) {
                    songIdOrNull = saveWithPlatform(lastSong)
                }
                if (songIdOrNull == null) {
                    songIdOrNull = saveWithLastSong(lastSong)
                }
                songIdOrNull
            } else {
                null
            }
            if (songId == null) {
                purgeHistory(historyId)
            } else {
                updateHistory(historyId, songId, lastSong.platform!!, lastSong.dateTime!!)
            }
            purgeAfterSaveSong(id)
        }
    }

    private suspend fun isAlreadySaved(mediaId: String, platformType: PlatformType): Boolean {
        return getPlatform.containsByMediaId(mediaId, platformType)
    }

    private suspend fun saveWithSongLink(data: LastSong): Long? {
        val saveWithSongLink = SaveWithSongLink()
        return saveWithSongLink.saveSongLinkData(data)
    }

    private suspend fun saveWithPlatform(data: LastSong): Long? {
        return when (data.platform) {
            PlatformType.AppleMusic -> {
                val saveWithAppleMusic = SaveWithAppleMusic()
                saveWithAppleMusic.saveAppleMusic(data)
            }
            else -> {
                null
            }
        }
    }

    private suspend fun saveWithLastSong(data: LastSong): Long? {
        val saveWithLastSong = SaveWithLastSong()
        return saveWithLastSong.saveLastSong(data)
    }

    private suspend fun purgeAfterSaveSong(id: Long) {
        val writeAfterSaveSong = WriteAfterSaveSong(db)
        writeAfterSaveSong.deleteAfterSaveSongById(id)
    }

    private suspend fun purgeHistory(historyId: Long) {
        val writeHistory = WriteHistory(db)
        writeHistory.deleteHistoryById(historyId)
    }

    private suspend fun updateHistory(
        historyId: Long,
        songId: Long,
        platformType: PlatformType,
        datetime: Long
    ) {
        val writeHistory = WriteHistory(db)
        return writeHistory.updateHistory(historyId, songId, platformType, datetime)
    }

}