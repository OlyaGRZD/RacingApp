package com.example.racingapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.racingapp.RacingApp
import com.example.racingapp.presentation.viewmodel.HistoryViewModel
import com.example.racingapp.ui.theme.Grey
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HistoryScreen() {
    val app = LocalContext.current.applicationContext as RacingApp
    val factory = app.appComponent.provideHistoryViewModelFactory()
    val viewModel: HistoryViewModel = viewModel(factory = factory)

    val history = viewModel.history

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text("История скачек",
            style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        if (history.isEmpty()) {
            Text("История пуста",
                style = MaterialTheme.typography.bodyLarge)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(history) { result ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                        ) {
                            Text(
                                text = "Победитель: ${result.winner}",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Время: ${formatDate(result.timestamp)}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Grey
                            )
                        }
                    }
                }
            }

        }
    }
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("HH:mm:ss dd.MM.yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

