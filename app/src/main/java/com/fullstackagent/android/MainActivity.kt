package com.fullstackagent.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.room.Room
import com.fullstackagent.android.data.api.ApiFactory
import com.fullstackagent.android.data.db.ArenaDatabase
import com.fullstackagent.android.data.repo.ArenaRepository
import com.fullstackagent.android.ui.navigation.NavGraph

class MainActivity : ComponentActivity() {
    private lateinit var repo: ArenaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = ArenaDatabase.get(applicationContext)
        repo = ArenaRepository(ApiFactory.create(), db)
        setContent {
            MaterialTheme {
                Surface { NavGraph(repoProvider = { repo }) }
            }
        }
    }
}
