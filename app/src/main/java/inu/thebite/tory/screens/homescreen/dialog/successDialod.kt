package inu.thebite.tory.screens.homescreen.dialog

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotApplyResult
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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
            content = {


                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier,
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.success),
                            contentDescription = "null"
                        )
                        SuccessAnimation()
                    }




                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {


                        Button(
                            onClick = {

                                onDismiss()
                            },
                            modifier = Modifier
                                .height(50.dp)
                                .weight(1f)
                        ) {
                            Text(text = "Confirm")
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun SuccessAnimation(

){var slideOffset by remember { mutableStateOf(0f) }

    val animatable = remember { Animatable(0f) }
    val targetValue = 300f // Adjust this value to control the distance of the slide

    LaunchedEffect(Unit) {
        delay(500) // Delay to start the animation after 1 second
        animatable.animateTo(
            targetValue,
            animationSpec = tween(durationMillis = 2000)
        )
    }

    val modifier: Modifier = Modifier

        .offset { IntOffset(animatable.value.roundToInt(), 0) }

    Box(
        modifier = Modifier
            .padding(bottom = 20.dp, end = 50.dp)

            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.spoon_1), // Replace with your image
            contentDescription = null,
            modifier = modifier
        )

        Image(
            painter = painterResource(id = R.drawable.spoon_1), // Replace with your image
            contentDescription = null,
            modifier = Modifier
                .offset(x = slideOffset.dp)
        )
    }
}