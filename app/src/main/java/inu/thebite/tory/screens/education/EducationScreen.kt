@file:Suppress("NAME_SHADOWING")

package inu.thebite.tory.screens.education

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import co.yml.charts.common.extensions.isNotNull
import es.dmoral.toasty.Toasty


import inu.thebite.tory.ChildViewModel
import inu.thebite.tory.screens.education.Compose.DevelopZoneRow
import inu.thebite.tory.screens.education.Compose.Dialog.AddGameItemsDialog
import inu.thebite.tory.screens.education.Compose.GraphRow
import inu.thebite.tory.screens.education.Compose.LTODetailsRow
import inu.thebite.tory.screens.education.Compose.LTOItemsRow
import inu.thebite.tory.screens.education.Compose.STODetailTableAndGameResult
import inu.thebite.tory.screens.education.Compose.STODetailsRow
import inu.thebite.tory.screens.education.Compose.STOItemsRow
import inu.thebite.tory.screens.education.Compose.Dialog.AddLTOItemDialog
import inu.thebite.tory.screens.education.Compose.Dialog.AddSTOItemDialog
import inu.thebite.tory.screens.education.Compose.Dialog.UpdateLTOItemDialog
import inu.thebite.tory.screens.education.Compose.Dialog.UpdateSTOItemDialog
import inu.thebite.tory.screens.education.Compose.gameReadyRow

import inu.thebite.tory.screens.game.DragAndDropViewModel
import inu.thebite.tory.screens.game.GameItem
import inu.thebite.tory.screens.game.GameScreen
import inu.thebite.tory.screens.game.GameTopBar


@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("MutableCollectionMutableState", "CheckResult")
@Composable
fun EducationScreen (
    ltoViewModel: LTOViewModel = viewModel(),
    childViewModel: ChildViewModel = viewModel(),
    stoViewModel: STOViewModel = viewModel(),
    gameViewModel: GameViewModel = viewModel(),
    dragAndDropViewModel: DragAndDropViewModel = viewModel()
) {

    val context = LocalContext.current

    val scrollState = rememberScrollState()

    val selectedChildName by childViewModel.selectedChildName.observeAsState("오전1")
    val selectedChildClass by childViewModel.selectedChildClass.observeAsState("오전반(월수금)")
    
    val gameResultList by gameViewModel.gameResultList.observeAsState()


    val stos by stoViewModel.stos.collectAsState()
    val selectedSTO by stoViewModel.selectedSTO.collectAsState()
    val allSTOs by stoViewModel.allSTOs.collectAsState()

    val ltos by ltoViewModel.ltos.collectAsState()
    val selectedLTO by ltoViewModel.selectedLTO.collectAsState()
    val allLTOs by ltoViewModel.allLTOs.collectAsState()


    val (updateLTOItem, setUpdateLTOItem) = rememberSaveable {
        mutableStateOf(false)
    }
    val (updateSTOItem, setUpdateSTOItem) = rememberSaveable {
        mutableStateOf(false)
    }
    val (addLTOItem, setAddLTOItem) = rememberSaveable {
        mutableStateOf(false)
    }
    val (addSTOItem, setAddSTOItem) = rememberSaveable {
        mutableStateOf(false)
    }
    val (gameDialog, setGameDialog) = rememberSaveable {
        mutableStateOf(false)
    }
    val (addGameItem, setAddGameItem) = rememberSaveable {
        mutableStateOf(false)
    }
    val (selectedDEVIndex, setSelectedDEVIndex) = rememberSaveable {
        mutableStateOf(0)
    }
    val (gameButton1Index, setGameButton1Index) = rememberSaveable {
        mutableStateOf(-1)
    }
    val (gameButton2Index, setGameButton2Index) = rememberSaveable {
        mutableStateOf(-1)
    }
    val (ltoDetailListIndex, setLTODetailListIndex) = rememberSaveable {
        mutableStateOf(-1)
    }
    val (stoDetailListIndex, setSTODetailListIndex) = rememberSaveable {
        mutableStateOf(-1)
    }

    val (isLTOGraphSelected, setIsLTOGraphSelected) = rememberSaveable {
        mutableStateOf(false)
    }

    val selectedSTODetailGameDataIndex = remember { mutableIntStateOf(0) }

    val (selectedSTOTryNum, setSelectedSTOTryNum) = rememberSaveable {
        mutableIntStateOf(0)
    }

    val timerStart = remember { mutableStateOf(false) }
    val timerRestart = remember { mutableStateOf(false) }

    val developZoneItems = listOf<String>(
        "1. 학습준비",
        "2. 매칭",
        "3. 동작모방",
        "4. 언어모방",
        "5. 변별",
        "6. 지시따라하기",
        "7. 요구하기",
        "8. 명명하기",
        "9. 인트라",
        "10. 가나다"
    )



    val (mainGameItem, setMainGameItem) = rememberSaveable {
        mutableStateOf("")
    }

    LaunchedEffect(selectedLTO, selectedDEVIndex, selectedChildClass, selectedChildName, addSTOItem, allSTOs, selectedSTO){
        selectedLTO?.let { stoViewModel.getSTOsByCriteria(selectedChildClass,selectedChildName, stoViewModel.developZoneItems[selectedDEVIndex], it.ltoName) }
    }

    LaunchedEffect(selectedChildClass, selectedChildName, selectedDEVIndex, allLTOs){
        ltoViewModel.getLTOsByCriteria(selectedChildClass,selectedChildName, stoViewModel.developZoneItems[selectedDEVIndex])
    }

    //LTO 추가 Dialog
    if (addLTOItem) {
        AddLTOItemDialog(
            context = context,
            allLTOs = allLTOs,
            setAddLTOItem = { setAddLTOItem(false) },
            ltoViewModel = ltoViewModel,
            selectedDevIndex = selectedDEVIndex,
            selectedChildClass = selectedChildClass,
            selectedChildName = selectedChildName,
            stoViewModel = stoViewModel,
        )
    }

    //LTO 추가 Dialog
    if (updateLTOItem) {
        UpdateLTOItemDialog(
            context = context,
            allLTOs = allLTOs,
            setUpdateLTOItem = {setUpdateLTOItem(false)},
            ltoViewModel = ltoViewModel,
            stoViewModel = stoViewModel,
            stos = stos,
            selectedLTO = selectedLTO,
        )
    }

    //STO 추가 Dialog
    if (addSTOItem) {
        AddSTOItemDialog(
            context = context,
            allSTOs = allSTOs,
            setAddSTOItem = { setAddSTOItem(false) },
            selectedDevIndex = selectedDEVIndex,
            childViewModel = childViewModel,
            stoViewModel = stoViewModel,
            selectedLTO = selectedLTO,
            selectedChildClass = selectedChildClass,
            selectedChildName = selectedChildName,
            updateSTOs = {}
        )
    }
    //STO 추가 Dialog
    if (updateSTOItem) {
        UpdateSTOItemDialog(
            context = context,
            allSTOs = allSTOs,
            stoViewModel = stoViewModel,
            setUpdateSTOItem = {setUpdateSTOItem(it)},
            selectedSTO = selectedSTO!!,
            setSelectedTryNum = {setSelectedSTOTryNum(it)}
        )
    }

    //게임아이템 추가, 제거 Dialog
    if(addGameItem){
        selectedSTO?.let {
            AddGameItemsDialog(
                context = context,
                selectedSTO = it,
                setMainItem = {setMainGameItem(it)},
                setAddGameItem = {setAddGameItem(it)},
                stoViewModel = stoViewModel
            )
        }
    }

    //게임 Dialog
    if(gameDialog){
        Dialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            onDismissRequest = { setGameDialog(false) }
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ){
                gameResultList?.let {
                    GameTopBar(
                        context = context,
                        dragAndDropViewModel = dragAndDropViewModel,
                        gameViewModel = gameViewModel,
                        selectedSTO = selectedSTO,
                        gameResultList = it,
                        gameButton1Index = gameButton1Index,
                        gameButton2Index = gameButton2Index,
                        timerStart = timerStart,
                        timerRestart = timerRestart,
                        setGameDialog = {setGameDialog(it)},
                        setGameButton1Index = {setGameButton1Index(it)},
                        setGameButton2Index = {setGameButton2Index(it)}
                    )
                }
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                ) {
                    GameScreen(
                        context = context,
                        dragAndDropViewModel = dragAndDropViewModel,
                        gameViewModel = gameViewModel,
                        stoViewModel = stoViewModel,
                        selectedSTO = selectedSTO,
                        selectedSTODetailGameDataIndex = selectedSTODetailGameDataIndex,
                        timerStart = timerStart,
                        timerRestart = timerRestart,
                        resetGameButtonIndex = {setGameButton1Index(-1)},
                        setSelectedSTODetailGameDataIndex = {selectedSTODetailGameDataIndex.intValue = it}
                    )
                }
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Divider(color = MaterialTheme.colorScheme.tertiary, thickness = 4.dp)
        //발달영역 ITEM------------------------------------------------------------------------------
        DevelopZoneRow(
            developZoneItems = developZoneItems,
            selectDevelopItem = {
                setSelectedDEVIndex(developZoneItems.indexOf(it))
                ltoViewModel.clearSelectedLTO()
                stoViewModel.clearSelectedSTO()
                Log.e("버그위치", "버그위치")
                selectedSTODetailGameDataIndex.intValue = 0
            },
        )
        Divider(color = MaterialTheme.colorScheme.tertiary, thickness = 4.dp)
        //LTO ITEM----------------------------------------------------------------------------------
        LTOItemsRow(
            ltoViewModel = ltoViewModel,
            selectedDevIndex = selectedDEVIndex,
            selectedLTO = selectedLTO,
            ltos = ltos,
            setAddLTOItem = {setAddLTOItem(it)},
            selectLTOItem = { it: String, progressState:Int ->
                setLTODetailListIndex(progressState)
                selectedSTODetailGameDataIndex.intValue = 0
            },
            stoViewModel = stoViewModel,
            selectedChildClass = selectedChildClass,
            selectedChildName = selectedChildName
        )
        //LTO Detail--------------------------------------------------------------------------------
        LTODetailsRow(
            selectedLTO = selectedLTO,
            isGraphSelected = isLTOGraphSelected,
            setGraphSelected = { setIsLTOGraphSelected(it) },
            setDetailListIndex = { setLTODetailListIndex(it) },
            ltoViewModel = ltoViewModel,
            selectedDevIndex = selectedDEVIndex,
            ltoDetailListIndex = ltoDetailListIndex,
            setLTOUpdateDialog = {setUpdateLTOItem(it)}
        )
        //STO ITEM ---------------------------------------------------------------------------------

        STOItemsRow(
            stoViewModel = stoViewModel,
            selectedLTO = selectedLTO,
            selectedSTO = selectedSTO,
            stos = stos,
            allSTOs = allSTOs,
            setAddSTOItem = { setAddSTOItem(it) },
            selectSTOItem = { it: String, progressState:Int ->
                setSTODetailListIndex(progressState)
                selectedSTODetailGameDataIndex.intValue = 0
            },
            setSelectedSTOTryNum = {setSelectedSTOTryNum(it)}
        )
        Divider(color = MaterialTheme.colorScheme.tertiary, thickness = 4.dp)
        //STO Details Row---------------------------------------------------------------------------
        selectedSTO?.let {
            STODetailsRow(
                selectedSTO = it,
                stoDetailListIndex = stoDetailListIndex,
                setSTODetailIndex = { it -> setSTODetailListIndex(it) },
                setUpdateSTOItem = { it -> setUpdateSTOItem(it)},
                gameStart = {
                    val targetItems = mutableListOf<GameItem>()
                    selectedSTO!!.gameItems.forEach { itemName ->
                        targetItems.add(
                            GameItem(
                                name = itemName,
                                image = getResourceIdByName(itemName,context)
                            )
                        )
                    }
                    dragAndDropViewModel.setTargetItems(targetItems)
                    Log.e("게임아이템들", dragAndDropViewModel.targetItems.value.toString()+dragAndDropViewModel.mainItem.value.toString())
                    //타겟아이템과 메인아이템이 null이 아닌 경우에만 게임 시작
                    if(dragAndDropViewModel.targetItems.value.isNotNull() && dragAndDropViewModel.mainItem.value.isNotNull() && dragAndDropViewModel.targetItems.value != emptyList<GameItem>()){
                        gameViewModel.setGameResultList(it.gameResult)
                        setGameDialog(true)
                        val targetElement = "n"
                        val firstNIndex = it.gameResult.indexOf(targetElement)
                        //n이 없는 경우에는 0으로 n이 있는 경우에는 처음으로 n이 있는 인덱스로 설정
                        if(firstNIndex != -1){
                            selectedSTODetailGameDataIndex.intValue = firstNIndex
                        }else {
                            selectedSTODetailGameDataIndex.intValue = 0

                        }
                    } else{
                        Toasty.warning(context, "게임아이템을 설정하고 메인아이템을 선택해주세요", Toast.LENGTH_SHORT, true).show()
                    }

                },
                stoViewModel = stoViewModel
            )
        }
        //STO Detail 내용 및 게임결과
        selectedSTO?.let {
            STODetailTableAndGameResult(
                selectedSTO = it,
                selectedSTODetailGameDataIndex = selectedSTODetailGameDataIndex.intValue,
                setSelectedSTODetailGameDataIndex = { it -> selectedSTODetailGameDataIndex.intValue = it},
                setSTODetailListIndex = { it -> setSTODetailListIndex(it)},
                stoViewModel = stoViewModel,
                selectedSTOTryNum = selectedSTOTryNum
            )
        }
        //게임준비
        selectedSTO?.let {
            gameReadyRow(
                context = context,
                dragAndDropViewModel = dragAndDropViewModel,
                selectedSTO = it,
                mainGameItem = mainGameItem,
                setAddGameItem = { it -> setAddGameItem(it)},
                setMainGameItem = { it -> setMainGameItem(it)}
            )
        }

        //그래프
        if(isLTOGraphSelected && selectedLTO.isNotNull()){
            GraphRow(
                stos = stos,
            )
        }
    }
}





@SuppressLint("DiscouragedApi")
fun getResourceIdByName(imageName: String, context: Context): Int {
    // 이 함수는 이미지 리소스 이름을 리소스 ID로 변환합니다.
    val packageName = context.packageName
    return context.resources.getIdentifier(imageName, "drawable", packageName)
}


