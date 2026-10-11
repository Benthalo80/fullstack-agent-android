package com.fullstackagent.android.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fullstackagent.android.data.api.DesignArenaApi

@Composable
fun HomeScreen(
    onOpenLeaderboard: (String, String) -> Unit,
    onOpenBattle: () -> Unit,
    onOpenAbout: () -> Unit
) {
    var prompt by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("What are you creating today?", style = MaterialTheme.typography.headlineSmall)
        Text("by Intelligence — Design Arena clone", style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = prompt, onValueChange = { prompt = it },
            label = { Text("Ask Design Arena to …") },
            modifier = Modifier.fillMaxWidth().height(120.dp)
        )
        Spacer(Modifier.height(12.dp))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(140.dp),
            contentPadding = PaddingValues(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f, fill = false)
        ) {
            items(DesignArenaApi.HOME_BISCUITS) { (slug, label) ->
                val disabled = slug == "mobileapps" || slug == "video"
                Card(onClick = { if (!disabled) onOpenLeaderboard("models", slug) }, enabled = !disabled) {
                    Column(Modifier.padding(12.dp)) {
                        Text(label, style = MaterialTheme.typography.bodyMedium)
                        if (disabled) Text("Coming back soon", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onOpenBattle, modifier = Modifier.fillMaxWidth()) { Text("Start blind battle (A vs B)") }
        TextButton(onClick = { onOpenLeaderboard("models", "website") }) { Text("See Website leaderboard") }
        TextButton(onClick = onOpenAbout) { Text("Methodology (Bradley-Terry)") }
        Text(
            "Rankings by Design Arena — designarena.ai",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
