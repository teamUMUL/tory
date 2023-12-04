package inu.thebite.tory.screens.game.dialog

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import inu.thebite.tory.R
import inu.thebite.tory.model.image.ImageResponse
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.screens.game.getRandomIndex
import inu.thebite.tory.screens.game.viewmodel.DragAndDropViewModel
import inu.thebite.tory.screens.game.viewmodel.GameViewModel
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel
import kotlinx.coroutines.delay
import kotlin.math.roundToInt


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SuccessDialog(
    context: Context,
    image1: ImageResponse,
    image2: ImageResponse,
    selectedLTO: LtoResponse,
    setSuccessDialog : (Boolean) -> Unit,
    dragAndDropViewModel: DragAndDropViewModel,
    gameViewModel: GameViewModel,
    imageViewModel: ImageViewModel
){
    val (selectedButton, setSelectedButton) = remember {
        mutableStateOf("")
    }
    Dialog(
        onDismissRequest = {
            if(gameViewModel.oneGameResult.value == "+"){
                gameViewModel.setOneGameResult("+")
            }
            setSuccessDialog(false)
        },
        properties = DialogProperties(usePlatformDefaultWidth = false),
        content = {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(gameViewModel.oneGameResult.value == "+"){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.7f),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Button(
                            onClick = {
                                gameViewModel.setOneGameResult("P")
                                setSelectedButton("P")
                                setSuccessDialog(false)
                                if(selectedLTO.game == "같은 사진 매칭"){
                                    dragAndDropViewModel.restartSameMode(imageViewModel.allImages.value?: emptyList())
                                } else {
                                    if(dragAndDropViewModel.isRandomGame){
                                        val randomMainItem = dragAndDropViewModel.targetItems.value!![getRandomIndex(dragAndDropViewModel.targetItems.value!!.size)]
                                        imageViewModel.findImageByName(randomMainItem.name)?.let {foundImage ->
                                            dragAndDropViewModel.setMainItem(foundImage)
                                            dragAndDropViewModel.restartGeneralMode(imagesByCategory = imageViewModel.getImagesByCategory(dragAndDropViewModel.mainItem.value!!.category.name))
                                        }
                                    } else {
                                        dragAndDropViewModel.restartGeneralMode(
                                            imagesByCategory = imageViewModel.getImagesByCategory(dragAndDropViewModel.mainItem.value!!.category.name)
                                        )
                                    }
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor =
                                if(selectedButton == "P"){
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.tertiary
                                }
                            )
                        ){
                            Text(
                                text = "P",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Button(
                            onClick = {
                                gameViewModel.clearOneGameResult()
                                setSelectedButton("C")
                                setSuccessDialog(false)
                                if(selectedLTO.game == "같은 사진 매칭"){
                                    dragAndDropViewModel.restartSameMode(imageViewModel.allImages.value?: emptyList())
                                } else {
                                    if(dragAndDropViewModel.isRandomGame){
                                        val randomMainItem = dragAndDropViewModel.targetItems.value!![getRandomIndex(dragAndDropViewModel.targetItems.value!!.size)]
                                        imageViewModel.findImageByName(randomMainItem.name)?.let {foundImage ->
                                            dragAndDropViewModel.setMainItem(foundImage)
                                            dragAndDropViewModel.restartGeneralMode(imagesByCategory = imageViewModel.getImagesByCategory(dragAndDropViewModel.mainItem.value!!.category.name))
                                        }
                                    } else {
                                        dragAndDropViewModel.restartGeneralMode(
                                            imagesByCategory = imageViewModel.getImagesByCategory(dragAndDropViewModel.mainItem.value!!.category.name)
                                        )
                                    }
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor =
                                if(selectedButton == "C"){
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.tertiary
                                }
                            )
                        ){
                            Text(
                                text = "C",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                        }
                    }
                }

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
                        dragAndDropViewModel = dragAndDropViewModel,
                        imageViewModel = imageViewModel
                    )
                }

            }
        }
    )

}


@Composable
fun SuccessAnimation(
    context: Context,
    image1: ImageResponse,
    image2: ImageResponse,
    selectedLTO: LtoResponse,
    setSuccessDialog : (Boolean) -> Unit,
    dragAndDropViewModel : DragAndDropViewModel,
    imageViewModel: ImageViewModel
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
        delay(1000)
        if(selectedLTO.game == "같은 사진 매칭"){
            dragAndDropViewModel.restartSameMode(imageViewModel.allImages.value?: emptyList())
        } else {
            if(dragAndDropViewModel.isRandomGame){
                val randomMainItem = dragAndDropViewModel.targetItems.value!![getRandomIndex(dragAndDropViewModel.targetItems.value!!.size)]
                imageViewModel.findImageByName(randomMainItem.name)?.let {foundImage ->
                    dragAndDropViewModel.setMainItem(foundImage)
                    dragAndDropViewModel.restartGeneralMode(imagesByCategory = imageViewModel.getImagesByCategory(dragAndDropViewModel.mainItem.value!!.category.name))
                }
            } else {
                dragAndDropViewModel.restartGeneralMode(
                    imagesByCategory = imageViewModel.getImagesByCategory(dragAndDropViewModel.mainItem.value!!.category.name)
                )
            }
        }
        setSuccessDialog(false)
    }

    val modifier1: Modifier = Modifier
        .size(300.dp)

        .offset { IntOffset(animatable1.value.roundToInt(), 0) }

    val modifier2: Modifier = Modifier
        .size(300.dp)
        .offset { IntOffset(animatable2.value.roundToInt(), 0) }


    Box(
        modifier = Modifier
            .padding(bottom = 20.dp, end = 150.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(
                    image1.url
                )
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.icon_edit),
            contentDescription = null,
//            contentScale = ContentScale.Crop,
            modifier = modifier2
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(
                    image2.url
                )
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.icon_edit),
            contentDescription = null,
//            contentScale = ContentScale.Crop,
            modifier = modifier1
        )
    }
}