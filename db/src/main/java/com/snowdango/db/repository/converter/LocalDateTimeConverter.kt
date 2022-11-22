package com.snowdango.db.repository.converter

import androidx.room.TypeConverter
import kotlinx.datetime.*

class LocalDateTimeConverter {

    @TypeConverter
    fun fromLocalDateTime(value: Long?): LocalDateTime? {
        if (value == null) return null

        val instant = Instant.fromEpochMilliseconds(value)
        return instant.toLocalDateTime(TimeZone.currentSystemDefault())
    }

    @TypeConverter
    fun toLocalTimeDate(value: LocalDateTime?): Long? {
        if (value == null) return null

        val instant = value.toInstant(TimeZone.currentSystemDefault())
        return instant.toEpochMilliseconds()
    }
}