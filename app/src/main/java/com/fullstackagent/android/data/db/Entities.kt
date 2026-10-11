package com.fullstackagent.android.data.db

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "leaderboard_entries")
data class LeaderboardEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val arena: String,
    val category: String,
    val rank: Int,
    val displayName: String,
    val provider: String,
    val openRouterId: String?,
    val elo: Int,
    val winRate: Double,
    val avgGenMs: Long?,
    val fetchedAt: Long
)

@Entity(tableName = "votes")
data class VoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val battleId: String,
    val arena: String,
    val category: String,
    val winner: String,
    val loser: String,
    val createdAt: Long,
    val synced: Int = 0
)

@Dao
interface LeaderboardDao {
    @Query("SELECT * FROM leaderboard_entries WHERE arena=:arena AND category=:category ORDER BY elo DESC")
    fun observe(arena: String, category: String): Flow<List<LeaderboardEntryEntity>>

    @Query("DELETE FROM leaderboard_entries WHERE arena=:arena AND category=:category")
    suspend fun clear(arena: String, category: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rows: List<LeaderboardEntryEntity>)
}

@Dao
interface VoteDao {
    @Insert
    suspend fun insert(vote: VoteEntity)

    @Query("SELECT * FROM votes WHERE synced=0 ORDER BY createdAt ASC")
    suspend fun unsynced(): List<VoteEntity>
}
