package com.snowdango.violet.model

import com.snowdango.violet.domain.platform.PlatformType
import com.snowdango.violet.usecase.datastore.PurgeLastSong
import org.koin.core.component.KoinComponent

class PurgeLastSongModel : KoinComponent {

    suspend fun purgeLastSong(platformType: PlatformType) {
        val purgeLastSong = PurgeLastSong()
        purgeLastSong.purgeLastSong(platformType)
    }

}