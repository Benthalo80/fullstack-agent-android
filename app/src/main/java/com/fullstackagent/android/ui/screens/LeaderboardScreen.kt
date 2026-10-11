package com.fullstackagent.android.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fullstackagent.android.data.repo.ArenaRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(arena: String, category: String, repo: ArenaRepository, onBack: () -> Unit) {
    var byElo by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    val rows by repo.observeLeaderboard(arena, category).collectAsState(initial = emptyList())
    LaunchedEffect(arena, category) {
        try { repo.refreshLeaderboard(arena, category) } catch (t: Throwable) { error = t.message }
    }
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        TextButton(onClick = onBack) { Text("← Back") }
        Text("$arena / $category", style = MaterialTheme.typography.headlineSmall)
        error?.let { Text("Offline cache (no key/network): $it", style = MaterialTheme.typography.bodySmall) }
        Row {
            FilterChip(selected = byElo, onClick = { byElo = true }, label = { Text("Elo") })
            FilterChip(selected = !byElo, onClick = { byElo = false }, label = { Text("WinRate") })
        }
        if (rows.isEmpty()) CircularProgressIndicator(Modifier.padding(24.dp))
        LazyColumn {
            val sorted = if (byElo) rows.sortedByDescending { it.elo } else rows.sortedByDescending { it.winRate }
            items(sorted) { e ->
                Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(Modifier.padding(12.dp)) {
                        Text("#${e.rank} ${e.displayName}", style = MaterialTheme.typography.bodyLarge)
                        Text("${e.provider} • Elo ${e.elo} • ${e.winRate}% • ${e.avgGenMs?.let { "${it / 1000}s" } ?: "—"}",
                            style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
        Text("Rankings by Design Arena — designarena.ai", style = MaterialTheme.typography.labelSmall)
    }
}
