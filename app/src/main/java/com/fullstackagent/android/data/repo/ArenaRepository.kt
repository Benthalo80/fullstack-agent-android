package com.fullstackagent.android.data.repo

import com.fullstackagent.android.data.api.DesignArenaApi
import com.fullstackagent.android.data.db.ArenaDatabase
import com.fullstackagent.android.data.db.LeaderboardEntryEntity
import kotlinx.coroutines.flow.Flow

class ArenaRepository(private val api: DesignArenaApi, private val db: ArenaDatabase) {
    fun observeLeaderboard(arena: String, category: String): Flow<List<LeaderboardEntryEntity>> =
        db.leaderboardDao().observe(arena, category)

    suspend fun refreshLeaderboard(arena: String, category: String) {
        val resp = api.getLeaderboard(arena, category)
        val now = System.currentTimeMillis()
        val rows = resp.data.mapIndexed { i, e ->
            LeaderboardEntryEntity(
                arena = arena, category = category, rank = i + 1,
                displayName = e.displayName, provider = e.provider,
                openRouterId = e.openRouterId, elo = e.elo,
                winRate = e.winRate, avgGenMs = e.avgGenerationTimeMs, fetchedAt = now
            )
        }
        db.leaderboardDao().clear(arena, category)
        db.leaderboardDao().insertAll(rows)
    }
}
