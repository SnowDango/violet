package com.snowdango.db.repository.dao

import androidx.room.*
import com.snowdango.db.domain.entity.songs.Song
import com.snowdango.db.domain.entity.songs.SongsTableName
import com.snowdango.db.domain.relation.SongAllMeta
import com.snowdango.db.domain.relation.SongWithArtist

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSong(song: Song)

    @Query("SELECT * FROM `$SongsTableName` WHERE id = :id")
    suspend fun getSong(id: Long): List<SongWithArtist>

    @Transaction
    @Query("SELECT * FROM `$SongsTableName` WHERE id = :id")
    suspend fun getSongWithArtist(id: Long): List<SongWithArtist>

    @Transaction
    @Query("SELECT * FROM `$SongsTableName` WHERE id = :id")
    suspend fun getSongAllMeta(id: Long): List<SongAllMeta>

}