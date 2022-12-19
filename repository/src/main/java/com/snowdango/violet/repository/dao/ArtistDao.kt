package com.snowdango.violet.repository.dao

import androidx.room.*
import com.snowdango.violet.domain.entity.artists.Artist
import com.snowdango.violet.domain.entity.artists.ArtistsTableName
import com.snowdango.violet.domain.relation.ArtistWithAlbums

@Dao
interface ArtistDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArtist(artist: Artist): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArtists(artists: List<Artist>): List<Long>

    @Delete
    suspend fun deleteArtist(artist: Artist)

    @Delete
    suspend fun deleteArtists(artists: List<Artist>)

    @Query("SELECT * FROM `$ArtistsTableName` WHERE id = :id")
    suspend fun getArtist(id: Long): List<Artist>

    @Query("SELECT * FROM $ArtistsTableName ORDER BY name ASC LIMIT :from,:size")
    suspend fun getArtists(from: Long, size: Long): List<Artist>

    @Query("DELETE FROM `$ArtistsTableName` WHERE id = :id")
    suspend fun deleteArtistById(id: Long)

    @Query("SELECT COUNT(id) FROM `$ArtistsTableName`")
    suspend fun getCount(): Long

    @Transaction
    @Query("SELECT * FROM `$ArtistsTableName` WHERE id = :id")
    suspend fun getArtistWithAlbums(id: Long): List<ArtistWithAlbums>

    @Transaction
    @Query("SELECT * FROM $ArtistsTableName ORDER BY name ASC LIMIT :from,:size")
    suspend fun getArtistsWithAlbums(from: Long, size: Long): List<ArtistWithAlbums>
}