package com.snowdango.db.repository.dao

import androidx.room.*
import com.snowdango.db.domain.entity.artists.Artist
import com.snowdango.db.domain.entity.artists.ArtistsTableName
import com.snowdango.db.domain.relation.ArtistWithAlbums

@Dao
interface ArtistDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArtist(artist: Artist): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArtists(artists: List<Artist>): List<Long>

    @Delete
    suspend fun deleteArtist(artist: Artist)

    @Query("SELECT * FROM `$ArtistsTableName` WHERE id = :id")
    suspend fun getArtist(id: Long): List<Artist>

    @Query("DELETE FROM `$ArtistsTableName` WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT COUNT(id) FROM `$ArtistsTableName`")
    suspend fun getCount(): Long

    @Query("SELECT * FROM `$ArtistsTableName` WHERE id = :id")
    suspend fun getArtistWithAlbums(id: Long): List<ArtistWithAlbums>

}