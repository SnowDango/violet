package com.snowdango.db.repository.dao

import androidx.room.*
import com.snowdango.db.domain.entity.songs.Song
import com.snowdango.db.domain.entity.songs.SongsTableName
import com.snowdango.db.domain.relation.SongAllMeta
import com.snowdango.db.domain.relation.SongWithArtist
import com.snowdango.db.domain.relation.SongWithPlatform

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSong(song: Song): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSongs(songs: List<Song>): List<Long>

    @Delete
    suspend fun deleteSong(song: Song)

    @Query("SELECT * FROM `$SongsTableName` WHERE id = :id")
    suspend fun getSong(id: Long): List<Song>

    @Query("SELECT * FROM `$SongsTableName` ORDER BY id DESC LIMIT :offset,100")
    suspend fun getSongListLimit100(offset: Long): List<Song>

    @Transaction
    @Query("SELECT * FROM `$SongsTableName` WHERE id = :id")
    suspend fun getSongWithArtist(id: Long): List<SongWithArtist>

    @Transaction
    @Query("SELECT * FROM `$SongsTableName` ORDER BY id DESC LIMIT :offset,100")
    suspend fun getSongWithArtistListLimit100(offset: Long): List<SongWithArtist>

    @Transaction
    @Query("SELECT * FROM `$SongsTableName` WHERE id = :id")
    suspend fun getSongAllMeta(id: Long): List<SongAllMeta>

    @Transaction
    @Query("SELECT * FROM `$SongsTableName` ORDER BY id DESC LIMIT :offset,100")
    suspend fun getSongAllMetaListLimit100(offset: Long): List<SongAllMeta>

    @Transaction
    @Query("SELECT * FROM `$SongsTableName` WHERE id = :id")
    suspend fun getSongWithPlatform(id: Long): List<SongWithPlatform>

}