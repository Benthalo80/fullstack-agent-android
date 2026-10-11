package com.fullstackagent.android.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Local 4-model bracket: semi1 → semi2 → winners → losers → tiebreak = 5 votes → 1-4 ranking.
 * Mirrors web methodology without backend.
 */
@Composable
fun BattleScreen(onBack: () -> Unit) {
    val pool = remember { listOf("Model A", "Model B", "Model C", "Model D") }
    var step by remember { mutableStateOf(0) }
    var winners by remember { mutableStateOf(mutableStateListOf<String>()) }
    var losers by remember { mutableStateOf(mutableStateListOf<String>()) }
    val pair: Pair<String, String>? = remember(step, winners, losers) {
        when (step) {
            0 -> pool[0] to pool[1]
            1 -> pool[2] to pool[3]
            2 -> (winners.getOrNull(0) ?: "?") to (winners.getOrNull(1) ?: "?")
            3 -> (losers.getOrNull(0) ?: "?") to (losers.getOrNull(1) ?: "?")
            4 -> "Finalist" to "Challenger"
            else -> null
        }
    }
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        TextButton(onClick = onBack) { Text("← Back") }
        Text("Blind battle ${minOf(step + 1, 5)} / 5", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        if (pair == null) {
            Text("Final ranking: ${winners.joinToString(" > ")}", style = MaterialTheme.typography.bodyLarge)
        } else {
            listOf(pair.first, pair.second).forEach { name ->
                Card(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Anonymous", style = MaterialTheme.typography.labelSmall)
                        Text(name, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
            Row {
                Button(onClick = {
                    if (step < 2) { if (step == 0) { winners.add(pool[0]); losers.add(pool[1]) } else { winners.add(pool[2]); losers.add(pool[3]) } }
                    step++
                }) { Text("Vote A") }
                Spacer(Modifier.padding(4.dp))
                Button(onClick = {
                    if (step < 2) { if (step == 0) { winners.add(pool[1]); losers.add(pool[0]) } else { winners.add(pool[3]); losers.add(pool[2]) } }
                    step++
                }) { Text("Vote B") }
            }
        }
    }
}

@Composable
fun AboutScreen(onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        TextButton(onClick = onBack) { Text("← Back") }
        Text("Methodology", style = MaterialTheme.typography.headlineSmall)
        Text(
            "4 models +1 backup, same prompt. Battle1 A vs B, Battle2 C vs D, " +
                "Winners, Losers, Tie-break = 5 votes → 1st-4th. " +
                "Bradley-Terry: 200 iters, Δ<0.0001, Rating=400·log10(strength). " +
                "WinRate raw %. <50 votes hidden, ~200 = reliable. Source: designarena.ai/about.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
