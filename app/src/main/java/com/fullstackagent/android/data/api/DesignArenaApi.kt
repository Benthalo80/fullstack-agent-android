package com.fullstackagent.android.data.api

import com.fullstackagent.android.data.api.dto.ArenasResponse
import com.fullstackagent.android.data.api.dto.LeaderboardResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DesignArenaApi {
    @GET("arenas")
    suspend fun getArenas(): ArenasResponse

    @GET("leaderboard/{arena}/{category}")
    suspend fun getLeaderboard(
        @Path("arena") arena: String,
        @Path("category") category: String
    ): LeaderboardResponse

    companion object {
        const val BASE_URL = "https://www.designarena.ai/api/v1/"
        val MODEL_CATEGORIES = listOf(
            "website", "gamedev", "3d", "dataviz", "uicomponent", "image",
            "logo", "graphicdesign", "svg", "ascii", "video", "imagetoimage",
            "slides", "tts", "codecategories"
        )
        val AGENT_CATEGORIES = listOf(
            "agon_webapps", "mobileapps", "nativeapps", "fullstack",
            "agentic_gamedev", "agon_godot", "agentic_3d", "agon_slides"
        )
        val HOME_BISCUITS = listOf(
            "website" to "Website", "slides" to "Slides", "image" to "Image",
            "gamedev" to "Game", "3d" to "3D Asset", "svg" to "SVG",
            "dataviz" to "Data Viz", "uicomponent" to "UI Component",
            "mobileapps" to "Mobile App", "logo" to "Logo",
            "tts" to "Text-to-Speech", "ascii" to "ASCII Art", "video" to "Video"
        )
    }
}
