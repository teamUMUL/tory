package inu.thebite.tory.screens.game

import android.annotation.SuppressLint
import android.content.Context
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
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.point.AddPointRequest
import inu.thebite.tory.model.sto.StoResponse
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
    selectedSTODetailGameDataIndex : MutableIntState,
    setSelectedSTODetailGameDataIndex : (Int) -> Unit,
    resetGameButtonIndex : () -> Unit,
    setIsCardSelectEnd : (Boolean) -> Unit,
    isCardSelectEnd : Boolean
) {
    val (successDialog, setSuccessDialog) = remember{
        mutableStateOf(false)
    }

    val (firstSuccessImage, setFirstSuccessImage) = remember{
        mutableIntStateOf(0)
    }

    val (secondSuccessImage, setSecondSuccessImage) = remember{
        mutableIntStateOf(0)
    }

    if(successDialog){
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
    //성공 시 축하 다이얼로그가 종료 후 데이터 추가하는 경우
    LaunchedEffect(successDialog){
        if(selectedLTO.game == "같은 사진 매칭") {
            if(isCardSelectEnd && !successDialog && gameViewModel.oneGameResult.value.isNotNull()){
                stoViewModel.addPoint(selectedSTO, addPointRequest = AddPointRequest(result = gameViewModel.oneGameResult.value!!, registrant = "테스트"))
//                selectedSTO?.let { selectedSTO ->
//                    val changeList = selectedSTO.gameResult.toMutableList()
//                    changeList[selectedSTODetailGameDataIndex.intValue] = gameViewModel.oneGameResult.value!!
//                    selectedSTO.gameResult = changeList
//                    stoViewModel.updateSTO(selectedSTO)
//                    gameViewModel.setGameResultList(changeList)
//                    setSelectedSTODetailGameDataIndex(selectedSTODetailGameDataIndex.intValue+1)
//                    if(dragAndDropViewModel.isRandomGame){
//                        val randomMainItem = dragAndDropViewModel.targetItems.value!![getRandomIndex(dragAndDropViewModel.targetItems.value!!.size)]
//                        dragAndDropViewModel.setMainItem(
//                            randomMainItem.copy(
//                                image = getResourceIdByName(randomMainItem.name, context)
//                            )
//                        )
//                    }
//                    gameViewModel.clearOneGameResult()
//                }
            }
        }
        //성공 시 축하 다이얼로그가 종료 후 데이터 추가하는 경우
        if(selectedLTO.game == "일반화 매칭"){
            if(isCardSelectEnd && !successDialog && gameViewModel.oneGameResult.value.isNotNull()){
//                //-,P 저장
//                val changeList = selectedSTO!!.gameResult.toMutableList()
//                changeList[selectedSTODetailGameDataIndex.intValue] = gameViewModel.oneGameResult.value!!
//                selectedSTO.gameResult = changeList
//                stoViewModel.updateSTO(selectedSTO)
//                gameViewModel.setGameResultList(changeList)
//                setSelectedSTODetailGameDataIndex(selectedSTODetailGameDataIndex.intValue+1)
//
//                if(dragAndDropViewModel.isRandomGame){
//                    val randomMainItem = dragAndDropViewModel.targetItems.value!![getRandomIndex(dragAndDropViewModel.targetItems.value!!.size)]
//                    dragAndDropViewModel.setMainItem(randomMainItem)
//                    dragAndDropViewModel.setTwoMainDifferentImageInCategory(context, randomMainItem.name)
//
//                }
//                //이미지 변경(target에 있는 동일한 카테고리)
//                gameViewModel.clearOneGameResult()
            }
        }

    }

    //바로 데이터를 추가하는 경우
    if(selectedLTO.game == "같은 사진 매칭") {
        LaunchedEffect(isCardSelectEnd){
            if(isCardSelectEnd){
//                if (selectedSTODetailGameDataIndex.intValue < selectedSTO!!.gameResult.size) {
//                    if(gameViewModel.oneGameResult.value == "+" || gameViewModel.oneGameResult.value == "P"){
//                        setFirstSuccessImage(dragAndDropViewModel.mainItem.value!!.image)
//                        setSecondSuccessImage(dragAndDropViewModel.mainItem.value!!.image)
//                        setSuccessDialog(true)
//                    }
//                    if (gameViewModel.oneGameResult.value != "+"){
//                        val changeList = selectedSTO.gameResult.toMutableList()
//                        changeList[selectedSTODetailGameDataIndex.intValue] = gameViewModel.oneGameResult.value!!
//                        selectedSTO.gameResult = changeList
//                        stoViewModel.updateSTO(selectedSTO)
//                        gameViewModel.setGameResultList(changeList)
//                        setSelectedSTODetailGameDataIndex(selectedSTODetailGameDataIndex.intValue+1)
//                        if(dragAndDropViewModel.isRandomGame){
//                            val randomMainItem = dragAndDropViewModel.targetItems.value!![getRandomIndex(dragAndDropViewModel.targetItems.value!!.size)]
//                            dragAndDropViewModel.setMainItem(
//                                randomMainItem.copy(
//                                    image = getResourceIdByName(randomMainItem.name, context)
//                                )
//                            )
//                        }
//                        gameViewModel.clearOneGameResult()
//                    }
//                }
            }
        }
    }

    //바로 데이터를 추가하는 경우
    if(selectedLTO.game == "일반화 매칭"){
        LaunchedEffect(isCardSelectEnd){
            if(isCardSelectEnd){
//                if (selectedSTODetailGameDataIndex.intValue < selectedSTO!!.gameResult.size) {
//                    //축하 이팩트
//                    if(gameViewModel.oneGameResult.value == "+" || gameViewModel.oneGameResult.value == "P"){
//                        setFirstSuccessImage(dragAndDropViewModel.firstMainItem.value!!.image)
//                        setSecondSuccessImage(beforeCircleImage)
//                        setSuccessDialog(true)
//                    }
//                    if (gameViewModel.oneGameResult.value != "+"){
//                        //-,P 저장
//                        val changeList = selectedSTO.gameResult.toMutableList()
//                        changeList[selectedSTODetailGameDataIndex.intValue] = gameViewModel.oneGameResult.value!!
//                        selectedSTO.gameResult = changeList
//                        stoViewModel.updateSTO(selectedSTO)
//                        gameViewModel.setGameResultList(changeList)
//                        setSelectedSTODetailGameDataIndex(selectedSTODetailGameDataIndex.intValue+1)
//
//                        if(dragAndDropViewModel.isRandomGame){
//                            val randomMainItem = dragAndDropViewModel.targetItems.value!![getRandomIndex(dragAndDropViewModel.targetItems.value!!.size)]
//                            dragAndDropViewModel.setMainItem(randomMainItem)
//                            dragAndDropViewModel.setTwoMainDifferentImageInCategory(context, randomMainItem.name)
//
//                        }
//                        //이미지 변경(target에 있는 동일한 카테고리)
//                        gameViewModel.clearOneGameResult()
//                    }
//                }
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
