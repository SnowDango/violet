package com.snowdango.db.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.snowdango.db.domain.entity.artists.Artist
import com.snowdango.db.domain.entity.artists.ArtistsTableName

@Dao
interface ArtistDao {

    @Insert
    suspend fun insertArtist(artist: Artist): Long

    @Query("SELECT * FROM `$ArtistsTableName` WHERE id = :id")
    suspend fun getArtist(id: Long): List<Artist>

}