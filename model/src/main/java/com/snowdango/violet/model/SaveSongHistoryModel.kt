package com.snowdango.violet.model

import com.snowdango.violet.domain.last.LastSong
import com.snowdango.violet.domain.platform.PlatformType
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.connect.ConnectManager
import com.snowdango.violet.usecase.datastore.CheckLastSong
import com.snowdango.violet.usecase.db.history.WriteHistory
import com.snowdango.violet.usecase.db.platform.GetPlatform
import com.snowdango.violet.usecase.save.SaveWithAppleMusic
import com.snowdango.violet.usecase.save.SaveWithSongLink
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// TODO need refactoring

class SaveSongHistoryModel : KoinComponent {

    private val db: SongHistoryDatabase by inject()
    private val connectManager: ConnectManager by inject()

    suspend fun saveSongHistory(data: LastSong, platformType: PlatformType) {
        val checkLastSong = CheckLastSong()
        val isChange = checkLastSong.checkLastSong(data, platformType)
        if (isChange) {
            getSongData(data, platformType)
        }
    }


    private suspend fun getSongData(data: LastSong, platformType: PlatformType) {
        data.platform?.songLink?.let { platform ->
            data.mediaId?.let { mediaId ->
                // is already save meta data
                val songId: Long? = if (!isAlreadySaved(mediaId, data.platform!!)) {
                    if (connectManager.isConnectInternet()) {
                        var songIdOrNull: Long? = null
                        songIdOrNull = saveWithSongLink(data)
                        if (songIdOrNull == null) {
                            songIdOrNull = saveWithPlatform(data)
                        }
                        songIdOrNull
                    } else {
                        -1L // TODO after insert by workManager
                    }
                } else {
                    val getPlatform = GetPlatform(db)
                    getPlatform.getPlatformWithSong(mediaId)?.song?.firstOrNull()?.id
                }
                //save history
                songId?.let {
                    saveHistory(it, platformType, data.dateTime!!)
                }
            }
        }
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

    private suspend fun isAlreadySaved(mediaId: String, platformType: PlatformType): Boolean {
        val getPlatform = GetPlatform(db)
        return getPlatform.containsByMediaId(mediaId, platformType)
    }

    private suspend fun saveHistory(
        songId: Long,
        platformType: PlatformType,
        datetime: Long
    ): Long {
        val writeHistory = WriteHistory(db)
        return writeHistory.insertHistory(songId, platformType, datetime)
    }
}