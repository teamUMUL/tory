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
import inu.thebite.tory.database.LTO.LTOEntity
import inu.thebite.tory.database.STO.STOEntity
import inu.thebite.tory.screens.education.GameViewModel
import inu.thebite.tory.screens.education.STOViewModel
import inu.thebite.tory.screens.education.getRandomIndex
import inu.thebite.tory.screens.education.getResourceIdByName

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun GameScreen(
    context : Context,
    dragAndDropViewModel: DragAndDropViewModel,
    gameViewModel: GameViewModel,
    stoViewModel: STOViewModel,
    selectedSTO: STOEntity?,
    selectedLTO: LTOEntity,
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

    val (successImage, setSuccessImage) = remember{
        mutableIntStateOf(0)
    }

    if(successDialog){
        SuccessDialog(
            context = context,
            image1 = successImage,
            image2 = successImage,
            setSuccessDialog = {setSuccessDialog(it)},
            dragAndDropViewModel = dragAndDropViewModel,
            selectedLTO = selectedLTO
        )
    }

    val mainItem by dragAndDropViewModel.mainItem.collectAsState()
    if(selectedLTO.gameMode == "같은 사진 매칭") {
        LaunchedEffect(isCardSelectEnd){
            if(isCardSelectEnd){
                if (selectedSTODetailGameDataIndex.intValue < selectedSTO!!.gameResult.size) {
                    if(gameViewModel.oneGameResult.value == "+" || gameViewModel.oneGameResult.value == "P"){
                        setSuccessImage(dragAndDropViewModel.mainItem.value!!.image)
                        setSuccessDialog(true)
                    }
                    val changeList = selectedSTO.gameResult.toMutableList()
                    changeList[selectedSTODetailGameDataIndex.intValue] = gameViewModel.oneGameResult.value!!
                    selectedSTO.gameResult = changeList
                    stoViewModel.updateSTO(selectedSTO)
                    gameViewModel.setGameResultList(changeList)
                    setSelectedSTODetailGameDataIndex(selectedSTODetailGameDataIndex.intValue+1)
                    if(dragAndDropViewModel.isRandomGame){
                        val randomMainItem = dragAndDropViewModel.targetItems.value!![getRandomIndex(dragAndDropViewModel.targetItems.value!!.size)]
                        dragAndDropViewModel.setMainItem(
                            randomMainItem.copy(
                                image = getResourceIdByName(randomMainItem.name, context)
                            )
                        )
                    }
                    gameViewModel.clearOneGameResult()
                }
            }
        }
    }

    if(selectedLTO.gameMode == "일반화 매칭"){
        LaunchedEffect(isCardSelectEnd){
            if(isCardSelectEnd){
                if (selectedSTODetailGameDataIndex.intValue < selectedSTO!!.gameResult.size) {
                    //축하 이팩트
                    if(gameViewModel.oneGameResult.value == "+" || gameViewModel.oneGameResult.value == "P"){
                        setSuccessImage(dragAndDropViewModel.mainItem.value!!.image)
                        setSuccessDialog(true)
                    }
                    //+,-,P 저장
                    val changeList = selectedSTO.gameResult.toMutableList()
                    changeList[selectedSTODetailGameDataIndex.intValue] = gameViewModel.oneGameResult.value!!
                    selectedSTO.gameResult = changeList
                    stoViewModel.updateSTO(selectedSTO)
                    gameViewModel.setGameResultList(changeList)
                    setSelectedSTODetailGameDataIndex(selectedSTODetailGameDataIndex.intValue+1)

                    if(dragAndDropViewModel.isRandomGame){
                    }
                    //이미지 변경(target에 있는 동일한 카테고리)
                    dragAndDropViewModel.setTwoMainDifferentImageInCategory(context,dragAndDropViewModel.mainCategory.value!!)
                    gameViewModel.clearOneGameResult()
                }
            }
        }
    }

    val oneGameResult by gameViewModel.oneGameResult.collectAsState()
    val targetItems by dragAndDropViewModel.targetItems.collectAsState()
    val targetCategory by dragAndDropViewModel.targetCategory.collectAsState()


    val screenWidth = LocalConfiguration.current.screenWidthDp
    val cardSize = Dp(screenWidth / 6.5f)

    selectedLTO?.let { selectedLTO ->
        when (selectedLTO.gameMode) {
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
                        setIsCardSelectEnd = {setIsCardSelectEnd(it)}
                    )
                }
            }
            "일반화 매칭" -> {
                targetCategory?.let {targetCategory ->
                    GeneralGameScreen(
                        selectedLTO = selectedLTO,
                        context = context,
                        timerStart = timerStart,
                        timerRestart = timerRestart,
                        targetCategory = targetCategory,
                        cardSize = cardSize,
                        oneGameResult = oneGameResult,
                        isCardSelectEnd = isCardSelectEnd,
                        dragAndDropViewModel = dragAndDropViewModel,
                        gameViewModel = gameViewModel,
                        resetGameButtonIndex = {resetGameButtonIndex()},
                        setIsCardSelectEnd = {setIsCardSelectEnd(it)}
                    )
                }
            }
            else -> {

            }
        }
    }


}

