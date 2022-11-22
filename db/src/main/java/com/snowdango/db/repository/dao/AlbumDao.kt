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

    @Query("SELECT * FROM `$AlbumsTableName` WHERE id = :id")
    suspend fun getAlbum(id: Long): List<Album>

    @Query("SELECT * FROM `$AlbumsTableName` ORDER BY id DESC LIMIT :offset,100")
    suspend fun getAlbumListLimit100(offset: Long): List<Album>

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
    suspend fun getAlbumWithSongsListLimit100(offset: Long): List<AlbumWithSongs>


}
