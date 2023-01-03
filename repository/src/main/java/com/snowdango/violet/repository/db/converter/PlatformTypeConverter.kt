package com.snowdango.violet.repository.db.converter

import androidx.room.TypeConverter
import com.snowdango.violet.domain.platform.PlatformType

class PlatformTypeConverter {

    @TypeConverter
    fun fromPlatformType(value: String?): PlatformType? {
        if (value == null) return null
        return when (value) {
            PlatformType.AppleMusic.name -> PlatformType.AppleMusic
            PlatformType.Spotify.name -> PlatformType.Spotify
            else -> PlatformType.Spotify
        }
    }

    @TypeConverter
    fun toPlatformType(value: PlatformType?): String? {
        if (value == null) return null
        return value.name
    }
}