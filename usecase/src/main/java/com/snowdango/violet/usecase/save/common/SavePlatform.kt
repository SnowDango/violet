package com.snowdango.violet.usecase.save.common

import com.snowdango.violet.domain.platform.PlatformType
import com.snowdango.violet.domain.response.songlink.SongApiResponse
import com.snowdango.violet.repository.db.SongHistoryDatabase
import com.snowdango.violet.usecase.db.platform.WritePlatform

class SavePlatform(private val db: SongHistoryDatabase) {

    suspend fun savePlatformWithSongLink(songId: Long, response: SongApiResponse) {
        response.entitiesByUniqueId.filter {
            PlatformType.values().any { type ->
                it.key.startsWith(type.songLinkEntityString)
            }
        }.forEach { (key, songEntity) ->
            val platformType = PlatformType.values().first { type ->
                key.startsWith(type.songLinkEntityString)
            }
            val linkPlatform = songEntity.platforms.first {
                PlatformType.values().any { platform ->
                    platform.songLink == it
                }
            }
            savePlatform(
                songId,
                platformType,
                getMediaIdByUniqueId(songEntity.id, platformType),
                response.linksByPlatform[linkPlatform]?.url
            )
        }
    }

    suspend fun savePlatform(
        songId: Long,
        platformType: PlatformType,
        mediaId: String,
        url: String?
    ) {
        val writePlatform = WritePlatform(db)
        writePlatform.insertPlatform(songId, platformType, mediaId, url ?: "")
    }

    private fun getMediaIdByUniqueId(
        uniqueId: String,
        platformType: PlatformType
    ): String {
        return uniqueId.replace(platformType.songLinkEntityString + "::", "")
    }


}