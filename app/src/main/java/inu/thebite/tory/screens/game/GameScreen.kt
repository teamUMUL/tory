package inu.thebite.tory.screens.game

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.model.image.ImageResponse
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.point.AddPointRequest
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.screens.education.compose.sto.getRandomIndex
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.game.viewmodel.DragAndDropViewModel
import inu.thebite.tory.screens.game.viewmodel.GameViewModel
import inu.thebite.tory.screens.game.dialog.SuccessDialog
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel
import kotlin.random.Random

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
    val oneGameResult by gameViewModel.oneGameResult.collectAsState()
    val targetItems by dragAndDropViewModel.targetItems.collectAsState()
    val mainItem by dragAndDropViewModel.mainItem.collectAsState()
    val firstMainItem by dragAndDropViewModel.firstMainItem.collectAsState()
    val secondMainItem by dragAndDropViewModel.secondMainItem.collectAsState()


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
            if(isCardSelectEnd && !successDialog && oneGameResult.isNotNull()){
                stoViewModel.addPoint(selectedSTO, addPointRequest = AddPointRequest(result = oneGameResult!!, registrant = "테스트"))
                if(dragAndDropViewModel.isRandomGame){
                    val randomMainItem = targetItems!![getRandomIndex(targetItems!!.size)]
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
            if(isCardSelectEnd && !successDialog && oneGameResult.isNotNull()){
                stoViewModel.addPoint(selectedSTO, addPointRequest = AddPointRequest(result = oneGameResult!!, registrant = "테스트"))
                if(dragAndDropViewModel.isRandomGame){
                    val randomMainItem = targetItems!![getRandomIndex(targetItems!!.size)]
                    imageViewModel.findImageByName(randomMainItem.name)?.let {foundImage ->
                        dragAndDropViewModel.setMainItem(foundImage)
                        dragAndDropViewModel.resetMainItemsGeneralMode(imagesByCategory = imageViewModel.getImagesByCategory(dragAndDropViewModel.mainItem.value!!.category.name))
                    }

                } else {
                    dragAndDropViewModel.restartGeneralMode(
                        imagesByCategory = imageViewModel.getImagesByCategory(firstMainItem!!.category.name)
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
                    if(oneGameResult == "+" || oneGameResult == "P"){
                        setFirstSuccessImage(mainItem!!)
                        setSecondSuccessImage(mainItem!!)
                        setSuccessDialog(true)
                    } else {
                        if (oneGameResult != "+"){
                            stoViewModel.addPoint(selectedSTO, AddPointRequest(result = oneGameResult!!, registrant = "테스트"))
                            if (oneGameResult != "-"){
                                if(dragAndDropViewModel.isRandomGame){
                                    val randomMainItem = targetItems!![getRandomIndex(targetItems!!.size)]
                                    imageViewModel.findImageByName(randomMainItem.name)?.let {foundImage ->
                                        dragAndDropViewModel.setMainItem(
                                            foundImage
                                        )
                                    }
                                }
                            }
                            gameViewModel.clearOneGameResult()
                        }
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
                    if(oneGameResult == "+" || oneGameResult == "P"){
                        setFirstSuccessImage(imageViewModel.findImageByName(firstMainItem!!.name))
                        setSecondSuccessImage(imageViewModel.findImageByName(secondMainItem!!.name))
                        Log.d("reset", "addSecondImage")
                        Log.d("reset", secondMainItem!!.name)
                        setSuccessDialog(true)
                    } else {
                        if(gameViewModel.oneGameResult.value != "+"){
                            stoViewModel.addPoint(selectedSTO, AddPointRequest(oneGameResult!!, registrant = "테스트"))
                            if(oneGameResult != "-"){
                                if(dragAndDropViewModel.isRandomGame){
                                    val randomMainItem = targetItems!![getRandomIndex(targetItems!!.size)]
                                    imageViewModel.findImageByName(randomMainItem.name)?.let {foundImage ->
                                        dragAndDropViewModel.setMainItem(foundImage)
                                        dragAndDropViewModel.resetMainItemsGeneralMode(imagesByCategory = imageViewModel.getImagesByCategory(dragAndDropViewModel.mainItem.value!!.category.name))
                                    }
                                } else {
                                    dragAndDropViewModel.resetMainItemsGeneralMode(
                                        imagesByCategory = imageViewModel.getImagesByCategory(mainItem!!.category.name)
                                    )

                                }
                            }

                            gameViewModel.clearOneGameResult()
                        }
                    }


                }
            }
        }
    }



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

