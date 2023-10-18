@file:Suppress("NAME_SHADOWING")

package inu.thebite.tory.screens.education

import android.annotation.SuppressLint
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import co.yml.charts.common.extensions.isNotNull
import es.dmoral.toasty.Toasty
import inu.thebite.tory.CenterSelectViewModel
import inu.thebite.tory.ChildClassSelectViewModel
import inu.thebite.tory.ChildSelectViewModel
import inu.thebite.tory.database.LTO.LTOEntity
import inu.thebite.tory.database.STO.STOEntity


import inu.thebite.tory.screens.education.Compose.DevelopZoneRow
import inu.thebite.tory.screens.education.Compose.Dialog.AddGeneralGameItem
import inu.thebite.tory.screens.education.Compose.GraphRow
import inu.thebite.tory.screens.education.Compose.LTODetailsRow
import inu.thebite.tory.screens.education.Compose.LTOItemsRow
import inu.thebite.tory.screens.education.Compose.STODetailTableAndGameResult
import inu.thebite.tory.screens.education.Compose.STODetailsRow
import inu.thebite.tory.screens.education.Compose.STOItemsRow
import inu.thebite.tory.screens.education.Compose.Dialog.AddLTOItemDialog
import inu.thebite.tory.screens.education.Compose.Dialog.AddSTOItemDialog
import inu.thebite.tory.screens.education.Compose.Dialog.AddSameGameItemsDialog
import inu.thebite.tory.screens.education.Compose.Dialog.UpdateLTOItemDialog
import inu.thebite.tory.screens.education.Compose.Dialog.UpdateSTOItemDialog
import inu.thebite.tory.screens.education.Compose.gameReadyRow

import inu.thebite.tory.screens.game.DragAndDropViewModel
import inu.thebite.tory.screens.game.GameItem
import inu.thebite.tory.screens.game.GameScreen
import inu.thebite.tory.screens.game.GameTopBar
import kotlin.random.Random


@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("MutableCollectionMutableState", "CheckResult")
@Composable
fun EducationScreen (
    ltoViewModel: LTOViewModel,
    childSelectViewModel: ChildSelectViewModel,
    stoViewModel: STOViewModel,
    gameViewModel: GameViewModel,
    dragAndDropViewModel: DragAndDropViewModel,
    centerViewModel: CenterSelectViewModel,
    childClassViewModel : ChildClassSelectViewModel,
    childInfoViewModel: ChildSelectViewModel
) {

    val context = LocalContext.current

    val scrollState = rememberScrollState()

    val selectedChildClass by childClassViewModel.selectedChildClass.collectAsState()

    val selectedChildInfo by childInfoViewModel.selectedChildInfo.collectAsState()

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


    LaunchedEffect(selectedLTO, selectedDEVIndex, selectedChildClass, selectedChildInfo, addSTOItem, allSTOs, selectedSTO){
        selectedLTO?.let {
            selectedChildInfo?.let { selectedChildInfo ->
                selectedChildClass?.let { selectedChildClass ->
                    stoViewModel.getSTOsByCriteria(
                        selectedChildClass.className,
                        selectedChildInfo.childName,
                        stoViewModel.developZoneItems[selectedDEVIndex],
                        it.ltoName)
                }
            }
        }
    }

    LaunchedEffect(selectedChildClass, selectedChildInfo, selectedDEVIndex, allLTOs){
        selectedChildClass?.let { selectedChildClass ->
            selectedChildInfo?.let  { selectedChildInfo ->
                ltoViewModel.getLTOsByCriteria(
                    selectedChildClass.className,
                    selectedChildInfo.childName,
                    stoViewModel.developZoneItems[selectedDEVIndex]
                )
            }
        }
    }

    //LTO 추가 Dialog
    if (addLTOItem) {
        selectedChildClass?.let { selectedChildClass ->
            selectedChildInfo?.let { selectedChildInfo ->
                AddLTOItemDialog(
                    context = context,
                    allLTOs = allLTOs,
                    setAddLTOItem = { setAddLTOItem(false) },
                    ltoViewModel = ltoViewModel,
                    selectedDevIndex = selectedDEVIndex,
                    selectedChildClass = selectedChildClass.className,
                    selectedChildName = selectedChildInfo.childName,
                    stoViewModel = stoViewModel,
                )
            }
        }
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
        selectedChildClass?.let {selectedChildClass ->
            selectedChildInfo?.let { selectedChildInfo ->
                AddSTOItemDialog(
                    context = context,
                    stos = stos,
                    setAddSTOItem = { setAddSTOItem(false) },
                    selectedDevIndex = selectedDEVIndex,
                    stoViewModel = stoViewModel,
                    selectedLTO = selectedLTO,
                    selectedChildClass = selectedChildClass.className,
                    selectedChildName = selectedChildInfo.childName,
                    updateSTOs = {}
                )
            }
        }
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
        selectedLTO?.let { selectedLTO ->
            selectedSTO?.let { selectedSTO ->
                if(selectedLTO.gameMode == "같은 사진 매칭"){
                    AddSameGameItemsDialog(
                        context = context,
                        selectedSTO = selectedSTO,
                        setMainItem = {setMainGameItem(it)},
                        setAddGameItem = {setAddGameItem(it)},
                        stoViewModel = stoViewModel
                    )
                }
                if(selectedLTO.gameMode == "일반화 매칭"){
                    AddGeneralGameItem(
                        context = context,
                        selectedSTO = selectedSTO,
                        setMainItem = {setMainGameItem(it)},
                        setAddGameItem = {setAddGameItem(it)},
                        stoViewModel = stoViewModel
                    )
                }
            }
        }

    }

    //게임 Dialog
    if(gameDialog){
        val (isCardSelectEnd, setIsCardSelectEnd) = rememberSaveable {
            mutableStateOf(false)
        }
        Dialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            onDismissRequest = { setGameDialog(false) }
        ){
            val windowManager =
                remember { context.getSystemService(Context.WINDOW_SERVICE) as WindowManager }

            val metrics = DisplayMetrics().apply {
                windowManager.defaultDisplay.getRealMetrics(this)
            }
            val (width, height) = with(LocalDensity.current) {
                Pair(metrics.widthPixels.toDp(), metrics.heightPixels.toDp())
            }
            Column(
                modifier = Modifier
                    .requiredSize(width = width, height = height)
            ){
                gameResultList?.let {
                    GameTopBar(
                        context = context,
                        dragAndDropViewModel = dragAndDropViewModel,
                        gameViewModel = gameViewModel,
                        selectedSTO = selectedSTO,
                        selectedLTO = selectedLTO,
                        gameResultList = it,
                        gameButton1Index = gameButton1Index,
                        gameButton2Index = gameButton2Index,
                        timerStart = timerStart,
                        timerRestart = timerRestart,
                        setGameDialog = {setGameDialog(it)},
                        setGameButton1Index = {setGameButton1Index(it)},
                        setGameButton2Index = {setGameButton2Index(it)},
                        setIsCardSelectEnd = {setIsCardSelectEnd(it)}
                    )
                }
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)

                ) {
                    selectedLTO?.let {
                        GameScreen(
                            context = context,
                            dragAndDropViewModel = dragAndDropViewModel,
                            gameViewModel = gameViewModel,
                            stoViewModel = stoViewModel,
                            selectedSTO = selectedSTO,
                            selectedLTO = it,
                            selectedSTODetailGameDataIndex = selectedSTODetailGameDataIndex,
                            timerStart = timerStart,
                            timerRestart = timerRestart,
                            resetGameButtonIndex = {setGameButton1Index(-1)},
                            setSelectedSTODetailGameDataIndex = {selectedSTODetailGameDataIndex.intValue = it},
                            setIsCardSelectEnd = {setIsCardSelectEnd(it)},
                            isCardSelectEnd = isCardSelectEnd
                        )
                    }
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
        selectedChildClass?.let {selectedChildClass ->
            selectedChildInfo?.let { selectedChildInfo ->
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
                    selectedChildClass = selectedChildClass.className,
                    selectedChildName = selectedChildInfo.childName
                )
            }
        }
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
            setSelectedSTOTryNum = {setSelectedSTOTryNum(it)},
            selectedSTODetailGameDataIndex = selectedSTODetailGameDataIndex
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
                    Log.e("게임모드",selectedLTO!!.gameMode )
                    when(selectedLTO!!.gameMode){
                        "같은 사진 매칭" -> {
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

                            if(dragAndDropViewModel.targetItems.value.isNotNull() && dragAndDropViewModel.targetItems.value != emptyList<GameItem>()){
                                if(dragAndDropViewModel.mainItem.value.isNotNull()){
                                    dragAndDropViewModel.isNotRandomGame()
                                } else{
                                    dragAndDropViewModel.setMainItem(
                                        dragAndDropViewModel.targetItems.value!![getRandomIndex(dragAndDropViewModel.targetItems.value!!.size)]
                                    )
                                    dragAndDropViewModel.isRandomGame()
                                }




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
                                selectedSTO?.let{
                                    selectedSTO!!.stoDescription =
                                        getSTODescription(
                                            dragAndDropViewModel = dragAndDropViewModel,
                                            selectedSTO = selectedSTO!!,
                                            isRandom = dragAndDropViewModel.isRandomGame,
                                            selectedLTO = selectedLTO!!
                                        )
                                    stoViewModel.updateSTO(
                                        selectedSTO!!
                                    )
                                    stoViewModel.setSelectedSTO(selectedSTO!!)
                                }
                            } else{
                                Toasty.warning(context, "게임아이템을 설정해주세요", Toast.LENGTH_SHORT, true).show()
                            }
                        }
                        "일반화 매칭" -> {
                            val targetItems = mutableListOf<GameItem>()


                            selectedSTO!!.gameItems.forEach { categoryName ->
                                targetItems.add(
                                    GameItem(
                                        name = categoryName.substringBefore("_"),
                                        image = dragAndDropViewModel.getRandomImageInCategory(context, categoryName.substringBefore("_"))
                                    )
                                )
                            }

                            dragAndDropViewModel.setTargetItems(targetItems)

                            if(dragAndDropViewModel.targetItems.value.isNotNull() && dragAndDropViewModel.targetItems.value != emptyList<GameItem>()){
                                if(dragAndDropViewModel.mainItem.value.isNotNull()){
                                    dragAndDropViewModel.isNotRandomGame()
                                } else{
                                    dragAndDropViewModel.setMainItem(
                                        dragAndDropViewModel.targetItems.value!![getRandomIndex(dragAndDropViewModel.targetItems.value!!.size)]
                                    )
                                    dragAndDropViewModel.isRandomGame()
                                }


                                dragAndDropViewModel.mainItem.value?.let { mainItem ->
                                    dragAndDropViewModel.setTwoMainDifferentImageInCategory(context = context,
                                        mainItem.name
                                    )
                                }

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
                                selectedSTO?.let{
                                    selectedSTO!!.stoDescription =
                                        getSTODescription(
                                            dragAndDropViewModel = dragAndDropViewModel,
                                            selectedSTO = selectedSTO!!,
                                            isRandom = dragAndDropViewModel.isRandomGame,
                                            selectedLTO = selectedLTO!!
                                        )
                                    stoViewModel.updateSTO(
                                        selectedSTO!!
                                    )
                                    stoViewModel.setSelectedSTO(selectedSTO!!)
                                }
                            } else{
                                Toasty.warning(context, "게임아이템을 설정해주세요", Toast.LENGTH_SHORT, true).show()
                            }
                        }
                    }
                    Log.e("게임아이템들", dragAndDropViewModel.targetItems.value.toString()+dragAndDropViewModel.mainItem.value.toString())


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
        selectedSTO?.let { selectedSTO ->
            selectedLTO?.let { selectedLTO ->
                gameReadyRow(
                    context = context,
                    dragAndDropViewModel = dragAndDropViewModel,
                    selectedSTO = selectedSTO,
                    selectedLTO = selectedLTO,
                    mainGameItem = mainGameItem,
                    setAddGameItem = { it -> setAddGameItem(it)},
                    setMainGameItem = { it -> setMainGameItem(it)}
                )
            }
        }

        //그래프
        if(isLTOGraphSelected && selectedLTO.isNotNull()){
            GraphRow(
                stos = stos,
            )
        }
    }
}

fun getRandomIndex(itemSize: Int): Int {
    return Random.nextInt(0, itemSize)
}

@SuppressLint("SuspiciousIndentation")
fun getSTODescription(selectedSTO : STOEntity,selectedLTO: LTOEntity, isRandom : Boolean, dragAndDropViewModel: DragAndDropViewModel): String {


    var gameItemsByKorean = ""
        selectedSTO.gameItems.forEachIndexed { index, gameItem ->
            gameItemsByKorean += if(index == 0){
                englishToKorean(extractWord(gameItem))
            } else {
                ", ${englishToKorean(extractWord(gameItem))}"
            }
    }

    val description =
        if(selectedLTO.gameMode == "같은 사진 매칭"){
            if (isRandom){
                "${selectedSTO.gameItems.size} Array\n목표아이템 : 랜덤\n예시아이템 : $gameItemsByKorean "
            } else {
                "${selectedSTO.gameItems.size} Array\n목표아이템 :${englishToKorean(extractWord(dragAndDropViewModel.mainItem.value!!.name))}\n예시아이템 : $gameItemsByKorean"
            }
        } else {
            if (isRandom){
                "${selectedSTO.gameItems.size} Array\n목표아이템 : 랜덤\n예시아이템 : $gameItemsByKorean "
            } else {
                "${selectedSTO.gameItems.size} Array\n목표아이템 :${englishToKorean(extractWord(dragAndDropViewModel.mainItem.value!!.name))}\n예시아이템 : $gameItemsByKorean"
            }
        }


    return description
}
fun extractWord(input: String): String {
    val regex = "([a-zA-Z]+)_\\d+".toRegex()
    val matchResult = input.let { regex.find(it) }

    return matchResult?.groups?.get(1)?.value ?: input
}

@SuppressLint("DiscouragedApi")
fun getResourceIdByName(imageName: String, context: Context): Int {
    // 이 함수는 이미지 리소스 이름을 리소스 ID로 변환합니다.
    val packageName = context.packageName
    return context.resources.getIdentifier(imageName, "drawable", packageName)
}

fun englishToKorean(english : String):String{
    var korean = ""
    when(english){
        "ball" -> {
            korean = "공"
        }
        "block" -> {
            korean = "블록"
        }
        "clock" -> {
            korean = "시계"
        }
        "colorpencil" -> {
            korean = "색연필"
        }
        "cup" -> {
            korean = "컵"
        }
        "doll" -> {
            korean = "인형"
        }
        "scissor" -> {
            korean = "가위"
        }
        "socks" -> {
            korean = "양말"
        }
        "spoon" -> {
            korean = "숫가락"
        }
        "toothbrush" -> {
            korean = "칫솔"
        }
        else -> korean = english
    }
    return korean
}

