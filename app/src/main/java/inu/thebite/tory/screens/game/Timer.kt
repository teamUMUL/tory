package inu.thebite.tory.screens.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import inu.thebite.tory.screens.education.GameViewModel
import kotlinx.coroutines.delay

@Composable
fun Timer(timerStart: MutableState<Boolean>, timerRestart: MutableState<Boolean>, gameButtonIndex : Int, gameViewModel : GameViewModel) {
    var progress by remember { mutableStateOf(1f) }

    LaunchedEffect(timerStart.value, timerRestart.value) {
        if(gameButtonIndex == -1){
            if(timerStart.value){
                val startTime = System.currentTimeMillis()
                val duration = 5000L // 5 seconds

                while (progress > 0f) {
                    val elapsedTime = System.currentTimeMillis() - startTime
                    progress = 1 - (elapsedTime.toFloat() / duration)
                    delay(16) // Update every 16ms (approximately 60 FPS)
                }
                gameViewModel.setOneGameResult("-")

                timerRestart.value = true
                timerStart.value = false
            }
            if(timerRestart.value){
                progress = 1f
            }
        }


    }

    LinearProgressIndicator(
        modifier = Modifier.fillMaxSize(),
        progress = progress,
        color = Color(0xFFE4E4E4),
        trackColor = Color.Transparent
    )
}