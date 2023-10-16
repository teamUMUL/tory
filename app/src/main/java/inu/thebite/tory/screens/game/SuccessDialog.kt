package inu.thebite.tory.screens.game

import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import inu.thebite.tory.R
import inu.thebite.tory.database.LTO.LTOEntity
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun SuccessDialog(
    context: Context,
    image1: Int,
    image2: Int,
    selectedLTO: LTOEntity,
    setSuccessDialog : (Boolean) -> Unit,
    dragAndDropViewModel: DragAndDropViewModel
){
    Dialog(
        onDismissRequest = { setSuccessDialog(false) },
        properties = DialogProperties(usePlatformDefaultWidth = false),
        content = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier.size(1000.dp),
                    contentAlignment = Alignment.Center
                ) { val imageModifier = Modifier
                    .size(1000.dp)
                    Image(
                        painter = painterResource(id = R.drawable.success),
                        contentDescription = "null",
                        contentScale = ContentScale.Fit,
                        modifier = imageModifier
                    )
                    SuccessAnimation(
                        context = context,
                        image1 = image1,
                        image2 = image2,
                        selectedLTO = selectedLTO,
                        setSuccessDialog = {setSuccessDialog(it)},
                        dragAndDropViewModel = dragAndDropViewModel
                    )
                }

            }
        }
    )

}


@Composable
fun SuccessAnimation(
    context: Context,
    image1: Int,
    image2: Int,
    selectedLTO: LTOEntity,
    setSuccessDialog : (Boolean) -> Unit,
    dragAndDropViewModel : DragAndDropViewModel
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
        delay(500)
        if(selectedLTO.gameMode == "같은 사진 매칭"){
            dragAndDropViewModel.restartSameMode(context = context)
        } else {
            dragAndDropViewModel.restartGeneralMode(context = context)
        }
        setSuccessDialog(false)
    }

    val modifier1: Modifier = Modifier.size(300.dp)

        .offset { IntOffset(animatable1.value.roundToInt(), 0) }

    val modifier2: Modifier = Modifier.size(300.dp)
        .offset { IntOffset(animatable2.value.roundToInt(), 0) }


    Box(
        modifier = Modifier
            .padding(bottom = 20.dp, end = 150.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = image1), // 다른 사진 대체하는 곳
            contentDescription = null,
            modifier = modifier2
        )

        Image(
            painter = painterResource(id = image2), // 다른 움직이는 사진 대체하는 곳
            contentDescription = null,
            modifier = modifier1
        )
    }
}



