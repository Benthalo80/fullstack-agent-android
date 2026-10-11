package com.fullstackagent.android.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class ArenaCategoryDto(val id: String, val displayName: String = "")

@Serializable
data class LeaderboardUrlDto(val category: String = "", val url: String = "")

@Serializable
data class ArenaDto(
    val id: String = "",
    val name: String = "",
    val categories: List<ArenaCategoryDto> = emptyList(),
    val leaderboardUrl: List<LeaderboardUrlDto> = emptyList()
)

@Serializable
data class ArenasResponse(val success: Boolean = false, val data: List<ArenaDto> = emptyList())

@Serializable
data class LeaderboardEntryDto(
    val displayName: String = "",
    val provider: String = "",
    val openRouterId: String? = null,
    val elo: Int = 1200,
    val winRate: Double = 0.0,
    val avgGenerationTimeMs: Long? = null
)

@Serializable
data class LeaderboardMetaDto(val arena: String = "", val category: String = "", val lastUpdated: String? = null)

@Serializable
data class LeaderboardResponse(
    val success: Boolean = false,
    val data: List<LeaderboardEntryDto> = emptyList(),
    val meta: LeaderboardMetaDto = LeaderboardMetaDto(),
    val timestamp: String? = null
)
