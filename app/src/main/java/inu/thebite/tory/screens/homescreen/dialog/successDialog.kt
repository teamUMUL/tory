package inu.thebite.tory.screens.homescreen.dialog

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import inu.thebite.tory.R
import kotlinx.coroutines.delay
import kotlin.math.roundToInt


@Composable
fun SuccessDialog(

    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(usePlatformDefaultWidth = false),
            content = {


                Column(
                    modifier = Modifier
                        .padding(16.dp)

                ) {
                    Box(
                        modifier = Modifier.size(1000.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        val imageModifier = Modifier
                            .size(1000.dp)
                        Image(
                            painter = painterResource(id = R.drawable.success),
                            contentDescription = "null",
                            contentScale = ContentScale.Fit,
                            modifier = imageModifier
                        )
                        SuccessAnimation(

                        )
                    }

                }
            }
        )
    }
}

@Composable
fun SuccessAnimation(

){
    val animatable1 = remember { Animatable(750f) }
    val animatable2 = remember { Animatable(-300f) } //처음 시작 위치 지정

    val targetValue1 = 450f // 멈추는 위치 지정
    val targetValue2 = -100f
    LaunchedEffect(Unit) {
//        delay(500) // Delay to start the animation after 0.5 second

        animatable2.animateTo(
            targetValue2,
            animationSpec = tween(durationMillis = 1000)   //처음 지정한 위치에서 타겟 위치로 변경
        )
        animatable1.animateTo(
            targetValue1,
            animationSpec = tween(durationMillis = 1000)   //애니매이션 지속시간
        )

    }

    val modifier1: Modifier = Modifier
        .size(300.dp)
        .offset { IntOffset(animatable1.value.roundToInt(), 0) }

    val modifier2: Modifier = Modifier
        .offset { IntOffset(animatable2.value.roundToInt(), 0) }
        .size(300.dp)

    Box(
        modifier = Modifier
            .padding(bottom = 20.dp, end = 150.dp)

            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.spoon_1), // 다른 사진 대체하는 곳
            contentDescription = null,
            modifier = modifier2
        )

        Image(
            painter = painterResource(id =  R.drawable.spoon_1), // 다른 움직이는 사진 대체하는 곳
            contentDescription = null,
            modifier = modifier1
        )
    }
}