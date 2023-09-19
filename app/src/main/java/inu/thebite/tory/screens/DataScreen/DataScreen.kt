@file:Suppress("NAME_SHADOWING")

package inu.thebite.tory.screens.DataScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import inu.thebite.tory.screens.DataScreen.Compose.Dialog.AddLTOItemDialog
import inu.thebite.tory.screens.DataScreen.Compose.Dialog.AddSTOItemDialog
import inu.thebite.tory.screens.DataScreen.Compose.DevelopZoneRow
import inu.thebite.tory.screens.DataScreen.Compose.Dialog.UpdateLTOItemDialog
import inu.thebite.tory.screens.DataScreen.Compose.Dialog.UpdateSTOItemDialog
import inu.thebite.tory.screens.DataScreen.Compose.LTODetailsRow
import inu.thebite.tory.screens.DataScreen.Compose.LTOItemsRow
import inu.thebite.tory.screens.DataScreen.Compose.STODetailTableAndGameResult
import inu.thebite.tory.screens.DataScreen.Compose.STODetailsRow
import inu.thebite.tory.screens.DataScreen.Compose.STOItemsRow

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataScreen (
    ltoViewModel: LTOViewModel = viewModel(),
    stoViewModel: STOViewModel = viewModel(),
    stoDetailViewModel: STODetailViewModel = viewModel()
) {

    val scrollState = rememberScrollState()

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
    val (selectedLTO, setSelectedLTO) = rememberSaveable {
        mutableStateOf("")
    }
    val (selectedSTO, setSelectedSTO) = rememberSaveable {
        mutableStateOf("")
    }

    val (selectedDEVIndex, setSelectedDEVIndex) = rememberSaveable {
        mutableStateOf(0)
    }
    val (selectedLTOIndex, setSelectedLTOIndex) = rememberSaveable {
        mutableStateOf(0)
    }
    val (selectedSTOIndex, setSelectedSTOIndex) = rememberSaveable {
        mutableStateOf(0)
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
    val (isSTOGraphSelected, setIsSTOGraphSelected) = rememberSaveable {
        mutableStateOf(false)
    }

    val (ltoProgressState, setLTOProgressState) = rememberSaveable {
        mutableStateOf(-1)
    }
    val (stoProgressState, setSTOProgressState) = rememberSaveable {
        mutableStateOf(-1)
    }
    val (stoDetailState, setSTODetailState) = rememberSaveable {
        mutableStateOf(mutableListOf<String>())
    }
    val (selectedSTODetail, setSelectedSTODetail) = rememberSaveable {
        mutableStateOf(mutableListOf<String>())
    }
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




    //LTO 추가 Dialog
    if (addLTOItem) {
        AddLTOItemDialog(
            setAddLTOItem = { setAddLTOItem(false) },
            ltoViewModel = ltoViewModel,
            stoViewModel = stoViewModel,
            stoDetailViewModel = stoDetailViewModel,
            selectedDevIndex = selectedDEVIndex,
        )
    }

    //LTO 추가 Dialog
    if (updateLTOItem) {
        UpdateLTOItemDialog(
            setUpdateLTOItem = {setUpdateLTOItem(false)},
            ltoViewModel = ltoViewModel,
            selectedDevIndex = selectedDEVIndex,
            selectedLTOIndex = selectedLTOIndex,
            setSelectedLTO = {setSelectedLTO(it)}
        )
    }

    //STO 추가 Dialog
    if (addSTOItem) {
        AddSTOItemDialog(
            setAddSTOItem = { setAddSTOItem(false) },
            stoViewModel = stoViewModel,
            selectedDevIndex = selectedDEVIndex,
            selectedLTOIndex = selectedLTOIndex,
            stoDetailViewModel = stoDetailViewModel,
        )
    }
    //STO 추가 Dialog
    if (updateSTOItem) {
        UpdateSTOItemDialog(
            stoViewModel = stoViewModel,
            stoDetailViewModel = stoDetailViewModel,
            selectedDevIndex = selectedDEVIndex,
            selectedLTOIndex = selectedLTOIndex,
            selectedSTOIndex = selectedSTOIndex,
            selectedSTO = selectedSTO,
            setUpdateSTOItem = {setUpdateSTOItem(it)},
            setSelectedSTO = {setSelectedSTO(it)},
            setSelectedSTODetailList = {setSelectedSTODetail(it.toMutableList())}
        )
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
                stoViewModel.removeLTOAtIndex(ltoViewModel.getLTO(selectedDEVIndex).first.indexOf(it),selectedDEVIndex)
                stoDetailViewModel.removeLTOAtIndex(ltoViewModel.getLTO(selectedDEVIndex).first.indexOf(it),selectedDEVIndex)
                ltoViewModel.removeLTO(selectedDEVIndex, it)
                setSelectedLTO("")
                setSelectedSTO("")
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
            setProgressState = { setLTOProgressState(it) },
            ltoDetailListIndex = ltoDetailListIndex,
            setLTOUpdateDialog = {setUpdateLTOItem(it)}
        )
        //STO ITEM ---------------------------------------------------------------------------------
        STOItemsRow(
            stoViewModel = stoViewModel,
            selectedDevIndex = selectedDEVIndex,
            selectedLTO = selectedLTO,
            selectedSTO = selectedSTO,
            selectedLTOIndex = selectedLTOIndex,
            setAddSTOItem = { setAddSTOItem(it) },
            selectSTOItem = { it: String, progressState:Int ->
                val newIndex = stoViewModel.getSTO(selectedLTOIndex, selectedDEVIndex).first.indexOf(it)
                setSelectedSTO(it)
                setSelectedSTOIndex(newIndex)
                Log.e("선택 STO", selectedSTOIndex.toString())
                // 이 부분을 LaunchedEffect로 감싸서 업데이트를 다음 프레임으로 보내줍니다.
                //selectedSTODetailList가 바로 적용되지 않고 한턴씩 밀림 UI에 볂
                setSelectedSTODetail(stoDetailViewModel.getSTODetail(selectedLTOIndex, selectedDEVIndex, newIndex).first.toMutableList())
                setSTODetailListIndex(progressState)
                setSelectedSTODetailGameDataIndex(0)
            },
            deleteSTOItem = {
                stoDetailViewModel.removeSTODetail(selectedLTOIndex, selectedDEVIndex, listOf(it,selectedSTODetail[1],selectedSTODetail[2],selectedSTODetail[3],selectedSTODetail[4],selectedSTODetail[5],selectedSTODetail[6]))
                stoViewModel.removeSTO(selectedLTOIndex, selectedDEVIndex,it)
                setSelectedSTO("")
                setSelectedSTODetailGameDataIndex(0)
            }
        )
        Divider(color = MaterialTheme.colorScheme.tertiary, thickness = 4.dp)
//      STO Details Row---------------------------------------------------------------------------
        STODetailsRow(
            selectedSTO = selectedSTO,
            isSTOGraphSelected = isSTOGraphSelected,
            setIsSTOGraphSelected = { setIsSTOGraphSelected(it) },
            stoViewModel = stoViewModel,
            selectedDevIndex = selectedDEVIndex,
            selectedLTOIndex = selectedLTOIndex,
            stoDetailListIndex = stoDetailListIndex,
            setProgressState = { setSTOProgressState(it) },
            setSTODetailIndex = { setSTODetailListIndex(it) },
            setUpdateSTOItem = {setUpdateSTOItem(it)}
        )
        //STO Detail 내용 및 게임결과
        STODetailTableAndGameResult(
            selectedDEVIndex = selectedDEVIndex,
            selectedLTOIndex = selectedLTOIndex,
            selectedSTOIndex = selectedSTOIndex,
            selectedSTO = selectedSTO,
            selectedSTODetail = selectedSTODetail,
            selectedSTODetailGameDataIndex = selectedSTODetailGameDataIndex,
            stoDetailViewModel = stoDetailViewModel,
            setSelectedSTODetailGameDataIndex = {setSelectedSTODetailGameDataIndex(it)},
        )
        //게임준비
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                .border(4.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(8.dp))
        ) {

        }
        //그래프
        if(isLTOGraphSelected){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                    .border(4.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(8.dp))
            ) {

            }
        }

    }
}











