package com.example.racingapp.ui

import android.graphics.ColorMatrix
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.racingapp.RacingApp
import com.example.racingapp.data.model.RaceHorse
import com.example.racingapp.presentation.viewmodel.RaceViewModel
import com.example.racingapp.ui.theme.Green1
import com.example.racingapp.ui.theme.Red1

@Composable
fun RaceScreen() {
    val app = LocalContext.current.applicationContext as RacingApp
    val factory = app.appComponent.provideRaceViewModelFactory()
    val raceViewModel: RaceViewModel = viewModel(factory = factory)

    val horses = raceViewModel.horses
    val winner by raceViewModel.winner
    val countdown by raceViewModel.countdown
    val isRunning by raceViewModel.isRaceRunning

    val horseSize = 64.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val trackMaxWidth = screenWidth - horseSize - 32.dp
    val countdownValue = countdown

    val showStartButton = !isRunning && winner == null
    val showRestartButton = !isRunning && winner != null

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Скачки",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            items(horses) { horse ->
                RaceTrack(
                    horse = horse,
                    maxWidth = trackMaxWidth.value,
                    isWinner = horse.name == winner,
                    horseSize = horseSize
                )
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    when {
                        countdownValue != null && countdownValue > 0 -> {
                            Text(
                                text = countdownValue.toString(),
                                style = MaterialTheme.typography.displayLarge,
                                color = Red1,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        countdownValue == 0 -> {
                            Text(
                                text = "СТАРТ!",
                                style = MaterialTheme.typography.displayLarge,
                                color = Red1,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                        winner != null -> {
                            Text(
                                text = "Победитель: $winner",
                                style = MaterialTheme.typography.titleLarge,
                                color = Green1,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    if (showStartButton) {
                        Button(
                            onClick = { raceViewModel.startRace() },
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text("Старт",
                                style = MaterialTheme.typography.titleLarge)
                        }
                    }

                    if (showRestartButton) {
                        Button(
                            onClick = { raceViewModel.resetRace() },
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text("Рестарт",
                                style = MaterialTheme.typography.titleLarge)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RaceTrack(
    horse: RaceHorse,
    maxWidth: Float,
    isWinner: Boolean,
    horseSize: Dp
) {
    val animatedOffset by animateFloatAsState(
        targetValue = (horse.progress.value * maxWidth).coerceAtMost(maxWidth),
        label = "horse-progress-${horse.id}"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(horseSize + 16.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .width(4.dp)
                .fillMaxHeight()
                .background(Red1)
        )

        Image(
            painter = painterResource(id = horse.imageResId),
            contentDescription = horse.name,
            modifier = Modifier
                .offset(x = animatedOffset.dp)
                .size(horseSize)
                .then(
                    if (isWinner)
                        Modifier
                            .border(3.dp,
                                Green1,
                                shape = MaterialTheme.shapes.small)
                    else Modifier
                )
        )
    }
}
