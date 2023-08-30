package com.snowdango.violet.repository.db.dao

import androidx.room.*
import com.snowdango.violet.domain.entity.songs.Song
import com.snowdango.violet.domain.entity.songs.SongsTableName
import com.snowdango.violet.domain.relation.SongAllMeta
import com.snowdango.violet.domain.relation.SongWithArtist
import com.snowdango.violet.domain.relation.SongWithPlatforms

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSong(song: Song): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSongs(songs: List<Song>): List<Long>

    @Delete
    suspend fun deleteSong(song: Song)

    @Query("UPDATE $SongsTableName SET thumbnail_url = :thumbnailUrl WHERE id = :id")
    suspend fun updateThumbnailUrl(id: Long, thumbnailUrl: String)

    @Query("DELETE FROM $SongsTableName WHERE id = :id")
    suspend fun deleteSongById(id: Long)

    @Query("SELECT COUNT(id) FROM $SongsTableName")
    suspend fun getCount(): Long

    @Query("SELECT * FROM `$SongsTableName` WHERE id = :id")
    suspend fun getSong(id: Long): List<Song>

    @Query("SELECT * FROM `$SongsTableName` ORDER BY id DESC LIMIT :from,:size")
    suspend fun getSongs(from: Long, size: Long): List<Song>

    @Transaction
    @Query("SELECT * FROM `$SongsTableName` WHERE id = :id")
    suspend fun getSongWithArtist(id: Long): List<SongWithArtist>

    @Transaction
    @Query("SELECT * FROM `$SongsTableName` ORDER BY id DESC LIMIT :from,:size")
    suspend fun getSongsWithArtist(from: Long, size: Long): List<SongWithArtist>

    @Transaction
    @Query("SELECT * FROM `$SongsTableName` WHERE id = :id")
    suspend fun getSongAllMeta(id: Long): List<SongAllMeta>

    @Transaction
    @Query("SELECT * FROM `$SongsTableName` ORDER BY id DESC LIMIT :from,:size")
    suspend fun getSongsAllMeta(from: Long, size: Long): List<SongAllMeta>

    @Transaction
    @Query("SELECT * FROM `$SongsTableName` WHERE id = :id")
    suspend fun getSongWithPlatforms(id: Long): List<SongWithPlatforms>

    @Transaction
    @Query("SELECT * FROM $SongsTableName ORDER BY id DESC LIMIT :from,:size")
    suspend fun getSongsWithPlatforms(from: Long, size: Long): List<SongWithPlatforms>
}