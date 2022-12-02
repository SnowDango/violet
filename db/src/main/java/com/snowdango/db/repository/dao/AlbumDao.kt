package com.snowdango.db.repository.dao

import androidx.room.*
import com.snowdango.db.domain.entity.albums.Album
import com.snowdango.db.domain.entity.albums.AlbumsTableName
import com.snowdango.db.domain.relation.AlbumWithArtist
import com.snowdango.db.domain.relation.AlbumWithSongs

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlbum(album: Album): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlbums(albums: List<Album>): List<Long>

    @Delete
    suspend fun deleteAlbum(album: Album)

    @Delete
    suspend fun deleteAlbums(albums: List<Album>)

    @Query("SELECT * FROM `$AlbumsTableName` WHERE id = :id")
    suspend fun getAlbum(id: Long): List<Album>

    @Query("SELECT * FROM `$AlbumsTableName` ORDER BY id DESC LIMIT :offset,100")
    suspend fun getAlbumListLimit100(offset: Long): List<Album>

    @Query("DELETE FROM `$AlbumsTableName` WHERE id = :id")
    suspend fun deleteAlbumById(id: Long)

    @Query("SELECT COUNT(id) FROM `$AlbumsTableName`")
    suspend fun getCount(): Long

    @Transaction
    @Query("SELECT * FROM `$AlbumsTableName` WHERE id = :id")
    suspend fun getAlbumWithArtist(id: Long): List<AlbumWithArtist>

    @Transaction
    @Query("SELECT * FROM `$AlbumsTableName` ORDER BY id DESC LIMIT :offset,100")
    suspend fun getAlbumWithArtistListLimit100(offset: Long): List<AlbumWithArtist>

    @Transaction
    @Query("SELECT * FROM `$AlbumsTableName` WHERE id = :id")
    suspend fun getAlbumWithSongs(id: Long): List<AlbumWithSongs>

    @Transaction
    @Query("SELECT * FROM `$AlbumsTableName` ORDER BY id DESC LIMIT :offset,100")
    suspend fun getAlbumsWithSongsLimit100(offset: Long): List<AlbumWithSongs>


}
