package com.snowdango.violet.repository.db.dao

import androidx.room.*
import com.snowdango.violet.domain.entity.albums.Album
import com.snowdango.violet.domain.entity.albums.AlbumsTableName
import com.snowdango.violet.domain.relation.AlbumWithArtist
import com.snowdango.violet.domain.relation.AlbumWithSongs

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

    @Query("SELECT * FROM `$AlbumsTableName` ORDER BY id DESC LIMIT :from,:size")
    suspend fun getAlbums(from: Long, size: Long): List<Album>

    @Query("SELECT * FROM `$AlbumsTableName` WHERE title = :title")
    suspend fun getAlbumByTitle(title: String): List<Album>

    @Query("DELETE FROM `$AlbumsTableName` WHERE id = :id")
    suspend fun deleteAlbumById(id: Long)

    @Query("SELECT COUNT(id) FROM `$AlbumsTableName`")
    suspend fun getCount(): Long

    @Transaction
    @Query("SELECT * FROM `$AlbumsTableName` WHERE id = :id")
    suspend fun getAlbumWithArtist(id: Long): List<AlbumWithArtist>

    @Transaction
    @Query("SELECT * FROM `$AlbumsTableName` ORDER BY id DESC LIMIT :from,:size")
    suspend fun getAlbumsWithArtist(from: Long, size: Long): List<AlbumWithArtist>

    @Transaction
    @Query("SELECT * FROM `$AlbumsTableName` WHERE id = :id")
    suspend fun getAlbumWithSongs(id: Long): List<AlbumWithSongs>

    @Transaction
    @Query("SELECT * FROM `$AlbumsTableName` ORDER BY id DESC LIMIT :from,:size")
    suspend fun getAlbumsWithSongs(from: Long, size: Long): List<AlbumWithSongs>


}
