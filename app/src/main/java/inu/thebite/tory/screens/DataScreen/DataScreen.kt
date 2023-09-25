@file:Suppress("NAME_SHADOWING")

package inu.thebite.tory.screens.DataScreen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel


import inu.thebite.tory.ChildViewModel
import inu.thebite.tory.R
import inu.thebite.tory.screens.DataScreen.Compose.Dialog.AddLTOItemDialog
import inu.thebite.tory.screens.DataScreen.Compose.Dialog.AddSTOItemDialog
import inu.thebite.tory.screens.DataScreen.Compose.DevelopZoneRow
import inu.thebite.tory.screens.DataScreen.Compose.Dialog.UpdateLTOItemDialog
import inu.thebite.tory.screens.DataScreen.Compose.Dialog.UpdateSTOItemDialog
import inu.thebite.tory.screens.DataScreen.Compose.GraphRow
import inu.thebite.tory.screens.DataScreen.Compose.LTODetailsRow
import inu.thebite.tory.screens.DataScreen.Compose.LTOItemsRow
import inu.thebite.tory.screens.DataScreen.Compose.STODetailTableAndGameResult
import inu.thebite.tory.screens.DataScreen.Compose.STODetailsRow
import inu.thebite.tory.screens.DataScreen.Compose.STOItemsRow

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun DataScreen (
    ltoViewModel: LTOViewModel = viewModel(),
    childViewModel: ChildViewModel = viewModel(),
    stoViewModel: STOViewModel = viewModel()
) {
    val context = LocalContext.current

    val scrollState = rememberScrollState()

    val selectedChildName by childViewModel.selectedChildName.observeAsState("오전1")
    val selectedChildClass by childViewModel.selectedChildClass.observeAsState("오전반(월수금)")

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
    val (selectedLTO, setSelectedLTO) = rememberSaveable {
        mutableStateOf("")
    }
    val (selectedSTO, setSelectedSTO) = rememberSaveable {
        mutableStateOf("")
    }

    val (selectedSTOId, setSelectedSTOId) = rememberSaveable {
        mutableStateOf(-1)
    }
    val (selectedDEVIndex, setSelectedDEVIndex) = rememberSaveable {
        mutableStateOf(0)
    }
    val (selectedLTOIndex, setSelectedLTOIndex) = rememberSaveable {
        mutableStateOf(0)
    }
//    val (selectedSTOIndex, setSelectedSTOIndex) = rememberSaveable {
//        mutableStateOf(0)
//    }
    val (ltoDetailListIndex, setLTODetailListIndex) = rememberSaveable {
        mutableStateOf(-1)
    }
    val (stoDetailListIndex, setSTODetailListIndex) = rememberSaveable {
        mutableStateOf(-1)
    }

    val (isLTOGraphSelected, setIsLTOGraphSelected) = rememberSaveable {
        mutableStateOf(false)
    }
//    val (isSTOGraphSelected, setIsSTOGraphSelected) = rememberSaveable {
//        mutableStateOf(false)
//    }
//
//    val (ltoProgressState, setLTOProgressState) = rememberSaveable {
//        mutableStateOf(-1)
//    }
//    val (stoProgressState, setSTOProgressState) = rememberSaveable {
//        mutableStateOf(-1)
//    }
//    val (stoDetailState, setSTODetailState) = rememberSaveable {
//        mutableStateOf(mutableListOf<String>())
//    }

    val (selectedSTODetailGameDataIndex, setSelectedSTODetailGameDataIndex) = rememberSaveable {
        mutableStateOf(0)
    }




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

    val selectedIdxMap = remember {
        mutableStateMapOf<String, Int>()
    }
    val selectedGameItems = remember { mutableStateListOf<String>() }

    val (mainGameItem, setMainGameItem) = rememberSaveable {
        mutableStateOf("")
    }

    //LTO 추가 Dialog
    if (addLTOItem) {
        AddLTOItemDialog(
            setAddLTOItem = { setAddLTOItem(false) },
            ltoViewModel = ltoViewModel,
            selectedDevIndex = selectedDEVIndex,
        )
    }

    //LTO 추가 Dialog
    if (updateLTOItem) {
        UpdateLTOItemDialog(
            setUpdateLTOItem = {setUpdateLTOItem(false)},
            ltoViewModel = ltoViewModel,
            stoViewModel = stoViewModel,
            selectedSTOId = selectedSTOId,
            selectedDevIndex = selectedDEVIndex,
            selectedLTOIndex = selectedLTOIndex,
            setSelectedLTO = {setSelectedLTO(it)}
        )
    }

    //STO 추가 Dialog
    if (addSTOItem) {
        AddSTOItemDialog(
            setAddSTOItem = { setAddSTOItem(false) },
            selectedDevIndex = selectedDEVIndex,
            childViewModel = childViewModel,
            stoViewModel = stoViewModel,
            selectedLTO = selectedLTO,
            selectedChildClass = selectedChildClass,
            selectedChildName = selectedChildName
        )
    }
    //STO 추가 Dialog
    if (updateSTOItem) {
        UpdateSTOItemDialog(
            stoViewModel = stoViewModel,
            selectedSTOId = selectedSTOId,
            setUpdateSTOItem = {setUpdateSTOItem(it)},
            setSelectedSTO = {setSelectedSTO(it)},
        )
    }

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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1f)
                        .background(MaterialTheme.colorScheme.secondary),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .clickable {
                                setGameDialog(false)
                            },
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )

                }
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)) {
                    Button(onClick = {
                    }) {
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                    }

                }
            }

        }
    }

    if(addGameItem){

        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            onDismissRequest = {
                setAddGameItem(false)
            }
        ){
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                shape = RoundedCornerShape(10.dp),
                color = Color.White
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)

                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.1f),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            modifier = Modifier
                                .padding(start = 20.dp),
                            text = "교육준비",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            modifier = Modifier
                                .padding(end = 20.dp),
                            onClick ={
                                setAddGameItem(false)
                            }
                        ){
                            Icon(
                                modifier = Modifier
                                    .size(40.dp),
                                imageVector = Icons.Default.Close,
                                contentDescription = null
                            )
                        }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.9f)
                    ) {
                        items(
                            listOf(
                                "spoon",
                                "cup",
                                "ball",
                                "block",
                                "clock",
                                "colorpencil",
                                "doll",
                                "scissor",
                                "socks",
                                "toothbrush"
                            )
                        ) { GameItemCategory ->
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    modifier = Modifier.padding(start = 10.dp),
                                    text = GameItemCategory,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                ) {
                                    for (i in 1..3) {
                                        val imageName = "${GameItemCategory}_${i}"
                                        val imageResource = getResourceIdByName(imageName, context)
                                        val isSelected = i - 1 == selectedIdxMap.getOrDefault(GameItemCategory, -1)
                                        Image(
                                            modifier = Modifier
                                                .weight(1.0f)
                                                .padding(10.dp)
                                                .clickable {

                                                    if (isSelected) {
                                                        selectedGameItems.remove(imageName)
                                                        selectedIdxMap[GameItemCategory] = -1
                                                    } else {
                                                        selectedGameItems.removeAll {
                                                            it.startsWith(
                                                                GameItemCategory
                                                            )
                                                        }
                                                        selectedGameItems.add(imageName)
                                                        selectedIdxMap[GameItemCategory] = i - 1
                                                    }
                                                },
                                            painter = painterResource(id = imageResource),
                                            contentDescription = null,
                                            alpha =  if (isSelected) 1.0f else 0.5f
                                        )
                                    }
                                }
                            }
                        }

                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(10.dp),
                        onClick = {
                            Log.e("선택된 게임들", selectedGameItems.toList().toString())
                            setAddGameItem(false)
                        },
                        shape = RoundedCornerShape(12.dp)
                    ){
                        Text(
                            text = "카드 준비",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
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
                setSelectedLTO("")
                setSelectedSTO("")
                Log.e("버그위치", "버그위치")
                setSelectedSTODetailGameDataIndex(0)
            }
        )
        Divider(color = MaterialTheme.colorScheme.tertiary, thickness = 4.dp)
        //LTO ITEM----------------------------------------------------------------------------------
        LTOItemsRow(
            ltoViewModel = ltoViewModel,
            selectedDevIndex = selectedDEVIndex,
            selectedLTO = selectedLTO,
            setAddLTOItem = {setAddLTOItem(it)},
            selectLTOItem = { it: String, progressState:Int ->
                setSelectedSTO("")
                setSelectedLTO(it)
                setSelectedLTOIndex(ltoViewModel.getLTO(selectedDEVIndex).first.indexOf(it))
                setLTODetailListIndex(progressState)
                setSelectedSTODetailGameDataIndex(0)
            },
            deleteLTOItem = {
                ltoViewModel.removeLTO(selectedDEVIndex, it)
                stoViewModel.deleteSTOsByCriteria(selectedChildClass,selectedChildName,stoViewModel.developZoneItems[selectedDEVIndex], it)
                setSelectedLTO("")
                setSelectedSTOId(-1)
            }
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
            childViewModel = childViewModel,
            stoViewModel = stoViewModel,
            selectedDevIndex = selectedDEVIndex,
            selectedLTO = selectedLTO,
            selectedSTO = selectedSTO,
            setAddSTOItem = { setAddSTOItem(it) },
            selectedChildClass = selectedChildClass,
            selectedChildName = selectedChildName,
            selectSTOItem = { it: String, progressState:Int ->
                setSelectedSTO(it)
                setSelectedSTOId(stoViewModel.getSTOIdByCriteria(
                    childClass = selectedChildClass,
                    childName = selectedChildName,
                    selectedDEV = stoViewModel.developZoneItems[selectedDEVIndex],
                    selectedLTO = selectedLTO,
                    it
                )!!)
                setSTODetailListIndex(progressState)
                setSelectedSTODetailGameDataIndex(0)
            },
            deleteSTOItem = {
                stoViewModel.deleteSTO(stoViewModel.getSTOIdByCriteria(childClass = selectedChildClass, childName = selectedChildName, stoViewModel.developZoneItems[selectedDEVIndex],selectedLTO, it)!!)
                setSelectedSTO("")
                setSelectedSTOId(-1)
                setSelectedSTODetailGameDataIndex(0)
            }
        )
        Divider(color = MaterialTheme.colorScheme.tertiary, thickness = 4.dp)
        //STO Details Row---------------------------------------------------------------------------
        STODetailsRow(
            selectedSTO = selectedSTO,
            selectedSTOId = selectedSTOId,
            stoDetailListIndex = stoDetailListIndex,
            selectedChildClass = selectedChildClass,
            selectedChildName = selectedChildName,
            selectedLTO = selectedLTO,
            selectedDEVIndex = selectedDEVIndex,
            setSTODetailIndex = { setSTODetailListIndex(it) },
            setUpdateSTOItem = {setUpdateSTOItem(it)},
            gameStart = {
                setGameDialog(true)
            },
            stoViewModel = stoViewModel
        )
        //STO Detail 내용 및 게임결과
        STODetailTableAndGameResult(
            selectedSTO = selectedSTO,
            selectedSTOId = selectedSTOId,
            selectedSTODetailGameDataIndex = selectedSTODetailGameDataIndex,
            setSelectedSTODetailGameDataIndex = {setSelectedSTODetailGameDataIndex(it)},
            setSTODetailListIndex = {setSTODetailListIndex(it)},
            stoViewModel = stoViewModel,
            selectedLTO = selectedLTO,
            selectedChildName = selectedChildName,
            selectedChildClass = selectedChildClass,
            selectedDEVIndex = selectedDEVIndex
        )
        //게임준비
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                .border(4.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(8.dp))
        ) {
            if(selectedSTO != ""){
                Box(modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight()
                    .padding(5.dp)
                    .clickable {
                        setAddGameItem(true)
                    },
                    contentAlignment = Alignment.Center
                ){
                    Column(modifier = Modifier
                        .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Icon(
                            modifier = Modifier
                                .size(80.dp),
                            painter = painterResource(id = R.drawable.icon_add_square_light),
                            contentDescription = null
                        )
                    }

                }

                LazyRow(
                ) {
                    items(selectedGameItems.toList()) { selectedGameItem ->
                        val imageResource = getResourceIdByName(selectedGameItem, context)
                        val isSelected = selectedGameItem == mainGameItem

                        Image(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(top = 10.dp, end = 10.dp, bottom = 10.dp)
                                .clickable {
                                    // Update the selected item when clicked
                                    if (isSelected) {
                                        setMainGameItem("")
                                    } else {
                                        setMainGameItem(selectedGameItem)
                                    }
                                },
                            painter = painterResource(id = imageResource),
                            contentDescription = null,
                            alpha = if (isSelected) 1.0f else 0.5f
                        )
                    }
                }
            }


        }
        //그래프
        if(isLTOGraphSelected){
            GraphRow(
                childViewModel = childViewModel,
                stoViewModel = stoViewModel,
                selectedDEVIndex = selectedDEVIndex,
                selectedLTO =selectedLTO,
                selectedChildClass = selectedChildClass,
                selectedChildName = selectedChildName
            )
        }



    }
}





@Composable
fun getResourceIdByName(imageName: String, context: Context): Int {
    // 이 함수는 이미지 리소스 이름을 리소스 ID로 변환합니다.
    val packageName = context.packageName
    return context.resources.getIdentifier(imageName, "drawable", packageName)
}






