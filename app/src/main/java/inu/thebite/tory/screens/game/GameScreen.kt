package inu.thebite.tory.screens.game

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.model.image.ImageResponse
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.point.AddPointRequest
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.screens.education2.compose.sto.getRandomIndex
import inu.thebite.tory.screens.education2.viewmodel.STOViewModel
import inu.thebite.tory.screens.game.viewmodel.DragAndDropViewModel
import inu.thebite.tory.screens.game.viewmodel.GameViewModel
import inu.thebite.tory.screens.game.dialog.SuccessDialog
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun GameScreen(
    context : Context,
    dragAndDropViewModel: DragAndDropViewModel,
    gameViewModel: GameViewModel,
    stoViewModel: STOViewModel,
    imageViewModel : ImageViewModel,
    selectedSTO: StoResponse,
    selectedLTO: LtoResponse,
    timerStart : MutableState<Boolean>,
    timerRestart : MutableState<Boolean>,
    points : List<String>,
    resetGameButtonIndex : () -> Unit,
    setIsCardSelectEnd : (Boolean) -> Unit,
    isCardSelectEnd : Boolean
) {
    val (successDialog, setSuccessDialog) = remember{
        mutableStateOf(false)
    }

    val (firstSuccessImage, setFirstSuccessImage) = remember{
        mutableStateOf<ImageResponse?>(null)
    }

    val (secondSuccessImage, setSecondSuccessImage) = remember{
        mutableStateOf<ImageResponse?>(null)
    }

    if(successDialog){
        firstSuccessImage?.let {firstSuccessImage ->
            secondSuccessImage?.let { secondSuccessImage ->
                SuccessDialog(
                    context = context,
                    image1 = firstSuccessImage,
                    image2 = secondSuccessImage,
                    setSuccessDialog = {setSuccessDialog(it)},
                    dragAndDropViewModel = dragAndDropViewModel,
                    selectedLTO = selectedLTO,
                    gameViewModel = gameViewModel,
                    imageViewModel = imageViewModel
                )
            }
        }
    }
    //성공 시 축하 다이얼로그가 종료 후 데이터 추가하는 경우
    LaunchedEffect(successDialog){
        if(selectedLTO.game == "같은 사진 매칭") {
            if(isCardSelectEnd && !successDialog && gameViewModel.oneGameResult.value.isNotNull()){
                stoViewModel.addPoint(selectedSTO, addPointRequest = AddPointRequest(result = gameViewModel.oneGameResult.value!!, registrant = "테스트"))
                if(dragAndDropViewModel.isRandomGame){
                    val randomMainItem = dragAndDropViewModel.targetItems.value!![getRandomIndex(dragAndDropViewModel.targetItems.value!!.size)]
                    imageViewModel.findImageByName(randomMainItem.name)?.let {foundImage ->
                        dragAndDropViewModel.setMainItem(foundImage)
                        dragAndDropViewModel.restartSameMode(imageViewModel.allImages.value!!)
                    }
                }
                gameViewModel.clearOneGameResult()
            }
        }
        //성공 시 축하 다이얼로그가 종료 후 데이터 추가하는 경우
        if(selectedLTO.game == "일반화 매칭"){
            if(isCardSelectEnd && !successDialog && gameViewModel.oneGameResult.value.isNotNull()){
                stoViewModel.addPoint(selectedSTO, addPointRequest = AddPointRequest(result = gameViewModel.oneGameResult.value!!, registrant = "테스트"))
                if(dragAndDropViewModel.isRandomGame){
                    val randomMainItem = dragAndDropViewModel.targetItems.value!![getRandomIndex(dragAndDropViewModel.targetItems.value!!.size)]
                    imageViewModel.findImageByName(randomMainItem.name)?.let {foundImage ->
                        dragAndDropViewModel.setMainItem(foundImage)
                        dragAndDropViewModel.restartGeneralMode(imagesByCategory = imageViewModel.getImagesByCategory(dragAndDropViewModel.mainItem.value!!.category.name))
                    }

                } else {
                    dragAndDropViewModel.restartGeneralMode(
                        imagesByCategory = imageViewModel.getImagesByCategory(dragAndDropViewModel.firstMainItem.value!!.category.name)
                    )
                }
                gameViewModel.clearOneGameResult()
            }
        }

    }

    //바로 데이터를 추가하는 경우
    if(selectedLTO.game == "같은 사진 매칭") {
        LaunchedEffect(isCardSelectEnd){
            if(isCardSelectEnd){
                if(points.size < selectedSTO.count){
                    if(gameViewModel.oneGameResult.value == "+" || gameViewModel.oneGameResult.value == "P"){
                        setFirstSuccessImage(dragAndDropViewModel.mainItem.value!!)
                        setSecondSuccessImage(dragAndDropViewModel.mainItem.value!!)
                        setSuccessDialog(true)
                    }
                    if (gameViewModel.oneGameResult.value != "+"){
                        stoViewModel.addPoint(selectedSTO, AddPointRequest(result = gameViewModel.oneGameResult.value!!, registrant = "테스트"))
                        if(dragAndDropViewModel.isRandomGame){
                            val randomMainItem = dragAndDropViewModel.targetItems.value!![getRandomIndex(dragAndDropViewModel.targetItems.value!!.size)]
                            imageViewModel.findImageByName(randomMainItem.name)?.let {foundImage ->
                                dragAndDropViewModel.setMainItem(
                                    foundImage
                                )
                            }
                        }
                        gameViewModel.clearOneGameResult()
                    }
                }
            }
        }
    }

    //바로 데이터를 추가하는 경우
    if(selectedLTO.game == "일반화 매칭"){
        LaunchedEffect(isCardSelectEnd){
            if(isCardSelectEnd){
                if(points.size < selectedSTO.count){
                    if(gameViewModel.oneGameResult.value == "+" || gameViewModel.oneGameResult.value == "P"){
                        setFirstSuccessImage(imageViewModel.findImageByName(dragAndDropViewModel.firstMainItem.value!!.name))
                        setSecondSuccessImage(imageViewModel.findImageByName(dragAndDropViewModel.secondMainItem.value!!.name))
                        Log.d("reset", "addSecondImage")
                        Log.d("reset", dragAndDropViewModel.secondMainItem.value!!.name)
                        setSuccessDialog(true)
                    }
                    if(gameViewModel.oneGameResult.value != "+"){
                        stoViewModel.addPoint(selectedSTO, AddPointRequest(gameViewModel.oneGameResult.value!!, registrant = "테스트"))
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
                        gameViewModel.clearOneGameResult()
                    }

                }
            }
        }
    }

    val oneGameResult by gameViewModel.oneGameResult.collectAsState()
    val targetItems by dragAndDropViewModel.targetItems.collectAsState()


    val screenWidth = LocalConfiguration.current.screenWidthDp
    val cardSize = Dp(screenWidth / 6.5f)

    selectedLTO?.let { selectedLTO ->
        when (selectedLTO.game) {
            "같은 사진 매칭" -> {
                targetItems?.let {targetItems ->
                    SameGameScreen(
                        selectedLTO = selectedLTO,
                        timerStart = timerStart,
                        timerRestart = timerRestart,
                        targetItems = targetItems,
                        cardSize = cardSize,
                        oneGameResult = oneGameResult,
                        isCardSelectEnd = isCardSelectEnd,
                        dragAndDropViewModel = dragAndDropViewModel,
                        gameViewModel = gameViewModel,
                        resetGameButtonIndex = {resetGameButtonIndex()},
                        setIsCardSelectEnd = {setIsCardSelectEnd(it)},
                        imageViewModel = imageViewModel
                    )
                }
            }
            "일반화 매칭" -> {
                targetItems?.let {targetItems ->
                    GeneralGameScreen(
                        selectedLTO = selectedLTO,
                        context = context,
                        timerStart = timerStart,
                        timerRestart = timerRestart,
                        targetItems = targetItems,
                        cardSize = cardSize,
                        oneGameResult = oneGameResult,
                        isCardSelectEnd = isCardSelectEnd,
                        dragAndDropViewModel = dragAndDropViewModel,
                        gameViewModel = gameViewModel,
                        imageViewModel = imageViewModel,
                        resetGameButtonIndex = {resetGameButtonIndex()},
                        setIsCardSelectEnd = {setIsCardSelectEnd(it)},
//                        setBeforeCircleImage = {setBeforeCircleImage(it)}
                    )
                }
            }
            else -> {

            }
        }
    }


}
