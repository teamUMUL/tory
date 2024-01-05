package inu.thebite.tory.screens.game

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import inu.thebite.tory.R
import inu.thebite.tory.model.image.ImageResponse
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.screens.game.viewmodel.DragAndDropViewModel
import inu.thebite.tory.screens.game.viewmodel.GameViewModel
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun GeneralGameScreen(
    context : Context,
    selectedLTO : LtoResponse,
    timerStart : MutableState<Boolean>,
    timerRestart : MutableState<Boolean>,
    targetItems : List<ImageResponse>,
    cardSize : Dp,
    oneGameResult : String?,
    isCardSelectEnd : Boolean,
    dragAndDropViewModel: DragAndDropViewModel,
    gameViewModel : GameViewModel,
    imageViewModel : ImageViewModel,
    resetGameButtonIndex : () -> Unit,
    setIsCardSelectEnd : (Boolean) -> Unit,
//    setBeforeCircleImage : (Int) -> Unit
){
    val successURL = "https://storage.googleapis.com/download/storage/v1/b/tory-education-image-repository/o/Etc%2FEtc_1?generation=1704437112256088&alt=media"
    val group1Size = (targetItems.size + 1) / 2
    val group1 = targetItems.subList(0, group1Size)
    val group2 = targetItems.subList(group1Size, targetItems.size)
    DraggableScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        dragAndDropViewModel = dragAndDropViewModel,
        timerRestart = timerRestart,
        selectedLTO = selectedLTO,
        cardSize = cardSize
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if(targetItems.size <= 3){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ){
                    targetItems.forEach { gameCategory ->
                        DropItem<ImageResponse>(
                            modifier = Modifier
                                .weight(1f)
                                .size(cardSize)
                        ) { isInBound, dragInGameItem ->
                            if(dragInGameItem != null){
                                LaunchedEffect(key1 = dragInGameItem){
                                    //드래그해서 들어온 아이템의 이름과 드래그한 곳의 이름이 같은 경우에 맞다는 판정
                                    if(dragInGameItem.category.name == gameCategory.category.name){
                                        dragAndDropViewModel.updateGameItemGeneralMode(
                                            dragAndDropViewModel.secondMainItem.value!!.copy(
                                                url = imageViewModel.findImageByName(imageName = "Etc_1")?.url
                                                    ?: successURL
                                            )
                                        )
                                        dragAndDropViewModel.isCorrect()
                                        when (oneGameResult) {
                                            "P" -> {
                                                gameViewModel.setOneGameResult("P")
                                            }
                                            "C" -> {
                                                gameViewModel.setOneGameResult("C")
                                            }
                                            else -> {
                                                gameViewModel.setOneGameResult("+")
                                            }
                                        }
                                    }
                                    //여기는 틀린 경우에 들어갈 행동
                                    else{
                                        when (oneGameResult) {
                                            "P" -> {
                                                gameViewModel.setOneGameResult("P")
                                            }
                                            "C" -> {
                                                gameViewModel.setOneGameResult("C")
                                            }
                                            else -> {
                                                gameViewModel.setOneGameResult("-")
                                            }
                                        }
                                    }
                                    timerRestart.value = true
                                    timerStart.value = false
                                    resetGameButtonIndex()
                                    if(gameViewModel.oneGameResult.value == "C"){
                                        gameViewModel.setOneGameResult("+")
                                    } else {
                                        setIsCardSelectEnd(true)
                                    }
                                }
                            }
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(
                                        if(dragAndDropViewModel.firstMainItem.value!!.category.name == gameCategory.category.name){
                                            dragAndDropViewModel.secondMainItem.value!!.url
                                        } else {
                                            gameCategory.url
                                        }
                                    )
                                    .crossfade(true)
                                    .build(),
                                placeholder = painterResource(id = R.drawable.icon_edit),
                                contentDescription = null,
//                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(cardSize),
                                alpha = if(isInBound) 0.5f else 1f
                            )
                        }
                    }
                }
            } else{
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.65f),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ){
                        group1.forEach { gameCategory ->
                            DropItem<ImageResponse>(
                                modifier = Modifier
                                    .weight(1f)
                                    .size(cardSize)
                            ) { isInBound, dragInGameItem ->
                                if(dragInGameItem != null){
                                    LaunchedEffect(key1 = dragInGameItem){
                                        //드래그해서 들어온 아이템의 이름과 드래그한 곳의 이름이 같은 경우에 맞다는 판정
                                        if(dragInGameItem.category.name == gameCategory.category.name){
                                            dragAndDropViewModel.updateGameItemGeneralMode(
                                                dragAndDropViewModel.secondMainItem.value!!.copy(
                                                    url = imageViewModel.findImageByName(imageName = "Etc_1")?.url
                                                        ?: successURL
                                                )
                                            )
                                            dragAndDropViewModel.isCorrect()
                                            when (oneGameResult) {
                                                "P" -> {
                                                    gameViewModel.setOneGameResult("P")
                                                }
                                                "C" -> {
                                                    gameViewModel.setOneGameResult("C")
                                                }
                                                else -> {
                                                    gameViewModel.setOneGameResult("+")
                                                }
                                            }
                                        }
                                        //여기는 틀린 경우에 들어갈 행동
                                        else{

                                            when (oneGameResult) {

                                                "P" -> {
                                                    gameViewModel.setOneGameResult("P")
                                                }
                                                "C" -> {
                                                    gameViewModel.setOneGameResult("C")
                                                }
                                                else -> {
                                                    gameViewModel.setOneGameResult("-")
                                                }
                                            }

                                        }
                                        timerRestart.value = true
                                        timerStart.value = false
                                        resetGameButtonIndex()
                                        if(gameViewModel.oneGameResult.value == "C"){
                                            gameViewModel.setOneGameResult("+")
                                        } else {
                                            setIsCardSelectEnd(true)

                                        }


                                    }
                                }
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(
                                            if(dragAndDropViewModel.firstMainItem.value!!.category.name == gameCategory.category.name){
                                                dragAndDropViewModel.secondMainItem.value!!.url
                                            } else {
                                                gameCategory.url
                                            }
                                        )
                                        .crossfade(true)
                                        .build(),
                                    placeholder = painterResource(id = R.drawable.icon_edit),
                                    contentDescription = null,
//                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(cardSize),
                                    alpha = if(isInBound) 0.5f else 1f
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ){
                        group2.forEach { gameCategory ->
                            DropItem<ImageResponse>(
                                modifier = Modifier
                                    .weight(1f)
                                    .size(cardSize)
                            ) { isInBound, dragInGameItem ->
                                if(dragInGameItem != null){
                                    LaunchedEffect(key1 = dragInGameItem){
                                        //드래그해서 들어온 아이템의 이름과 드래그한 곳의 이름이 같은 경우에 맞다는 판정
                                        if(dragInGameItem.category.name == gameCategory.category.name){
                                            dragAndDropViewModel.updateGameItemGeneralMode(
                                                dragAndDropViewModel.secondMainItem.value!!.copy(
                                                    url = imageViewModel.findImageByName(imageName = "Etc_1")?.url
                                                        ?: successURL
                                                )
                                            )
                                            dragAndDropViewModel.isCorrect()
                                            when (oneGameResult) {
                                                "P" -> {
                                                    gameViewModel.setOneGameResult("P")
                                                }
                                                "C" -> {
                                                    gameViewModel.setOneGameResult("C")
                                                }
                                                else -> {
                                                    gameViewModel.setOneGameResult("+")
                                                }
                                            }
                                        }
                                        //여기는 틀린 경우에 들어갈 행동
                                        else{

                                            when (oneGameResult) {

                                                "P" -> {
                                                    gameViewModel.setOneGameResult("P")
                                                }
                                                "C" -> {
                                                    gameViewModel.setOneGameResult("C")
                                                }
                                                else -> {
                                                    gameViewModel.setOneGameResult("-")
                                                }
                                            }

                                        }
                                        timerRestart.value = true
                                        timerStart.value = false
                                        resetGameButtonIndex()
                                        if(gameViewModel.oneGameResult.value == "C"){
                                            gameViewModel.setOneGameResult("+")
                                        } else {
                                            setIsCardSelectEnd(true)

                                        }


                                    }
                                }
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(
                                            if(dragAndDropViewModel.firstMainItem.value!!.category.name == gameCategory.category.name){
                                                dragAndDropViewModel.secondMainItem.value!!.url
                                            } else {
                                                gameCategory.url
                                            }
                                        )
                                        .crossfade(true)
                                        .build(),
                                    placeholder = painterResource(id = R.drawable.icon_edit),
                                    contentDescription = null,
//                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(cardSize),
                                    alpha = if(isInBound) 0.5f else 1f
                                )
                            }
                        }
                    }
                }

            }


            Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .background(MaterialTheme.colorScheme.secondary),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                DragTarget(
                    modifier = Modifier
                        .size(cardSize)
                        .padding(bottom = 20.dp)
                    ,
                    data = dragAndDropViewModel.firstMainItem.value,
                    viewModel = dragAndDropViewModel,
                    timerStart = timerStart,
                    timerRestart = timerRestart,
                    isCardSelectEnd = isCardSelectEnd,
                    setIsCardSelectEnd = {setIsCardSelectEnd(it)},
                    resetGameButtonIndex = {resetGameButtonIndex()}
                ) {
                    if(!dragAndDropViewModel.isCurrentlyDragging){
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(dragAndDropViewModel.firstMainItem.value!!.url)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(id = R.drawable.icon_edit),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(cardSize),
                        )
                    }
                }
            }


        }

    }
}