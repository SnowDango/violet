package com.snowdango.db.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.snowdango.db.domain.entity.artists.Artist
import com.snowdango.db.domain.entity.artists.ArtistsTableName

@Dao
interface ArtistDao {

    @Insert
    suspend fun insertArtist(artist: Artist): Long

    @Insert
    suspend fun insertArtists(artists: List<Artist>): List<Long>

    @Delete
    suspend fun deleteArtist(artist: Artist)

    @Query("SELECT * FROM `$ArtistsTableName` WHERE id = :id")
    suspend fun getArtist(id: Long): List<Artist>

    @Query("DELETE FROM `$ArtistsTableName` WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT COUNT(id) FROM `$ArtistsTableName`")
    suspend fun getCount(): Long
    
}