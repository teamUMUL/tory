@file:Suppress("NAME_SHADOWING")

package inu.thebite.tory.screens.DataScreen

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import inu.thebite.tory.screens.DataScreen.Compose.AddLTOItemDialog
import inu.thebite.tory.screens.DataScreen.Compose.AddSTOItemDialog
import inu.thebite.tory.screens.DataScreen.Compose.DetailsRow
import inu.thebite.tory.screens.DataScreen.Compose.DevelopZoneRow
import inu.thebite.tory.screens.DataScreen.Compose.LTOItemsRow
import inu.thebite.tory.screens.DataScreen.Compose.STODetailsRow
import inu.thebite.tory.screens.DataScreen.Compose.STOItemsRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataScreen (
    ltoViewModel: LTOViewModel = viewModel(),
    stoViewModel: STOViewModel = viewModel(),
    stoDetailViewModel: STODetailViewModel = viewModel()
){
    val scrollState = rememberScrollState()
    val stoDetailScrollState = rememberScrollState()

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
    val (selectedSTODetailIndex, setSelectedSTODetailIndex) = rememberSaveable {
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
    val (isLTOSelected, setIsLTOSelected) = rememberSaveable {
        mutableStateOf(false)
    }
    val (isSTOSelected, setIsSTOSelected) = rememberSaveable {
        mutableStateOf(false)
    }
    val (isDEVSelected, setIsDEVSelected) = rememberSaveable {
        mutableStateOf(false)
    }
    val (progressState, setProgressState) = rememberSaveable {
        mutableStateOf(-1)
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

    val STDDetailTitles =
        listOf<String>(
            "STO 이름",
            "STO 내용",
            "시도 수",
            "준거도달 기준",
            "촉구방법",
            "강화스케줄",
            "메모",
        )

    //LTO 추가 Dialog
    if (addLTOItem){
        AddLTOItemDialog(
            setAddLTOItem = {setAddLTOItem(false)},
            ltoViewModel = ltoViewModel,
            stoViewModel = stoViewModel,
            stoDetailViewModel = stoDetailViewModel,
            selectedDevIndex = selectedDEVIndex,
        )
    }

    //STO 추가 Dialog
    if (addSTOItem){
        AddSTOItemDialog(
            setAddSTOItem = {setAddSTOItem(false)},
            stoViewModel = stoViewModel,
            selectedDevIndex = selectedDEVIndex,
            selectedLTOIndex = selectedLTOIndex,
            stoDetailViewModel = stoDetailViewModel,
            )
    }


    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
    ){
        Divider(color = MaterialTheme.colorScheme.tertiary, thickness = 4.dp)
        //발달영역 ITEM------------------------------------------------------------------------------
        DevelopZoneRow(
            developZoneItems = developZoneItems,
            selectDevelopItem = {
                setSelectedDEVIndex(developZoneItems.indexOf(it))
                setSelectedLTO("")
                setSelectedSTO("")
                Log.e("버그위치", "버그위치")
            }
        )
        Divider(color = MaterialTheme.colorScheme.tertiary, thickness = 4.dp)
        //LTO ITEM----------------------------------------------------------------------------------
        LTOItemsRow(
            ltoViewModel = ltoViewModel,
            stoViewModel = stoViewModel,
            selectedDevIndex = selectedDEVIndex,
            setSelectedLTO = {setSelectedLTO(it)},
            selectedLTO = selectedLTO,
            setAddLTOItem = {setAddLTOItem(it)},
            setSelectedLTOIndex = {setSelectedLTOIndex(it)},
            setDetailListIndex = {setLTODetailListIndex(it)},
            stoDetailViewModel = stoDetailViewModel,
            setSelectedSTO = {setSelectedSTO(it)}
            )
        //LTO Detail--------------------------------------------------------------------------------
        DetailsRow(
            selectedLTO = selectedLTO,
            isGraphSelected = isLTOGraphSelected,
            setGraphSelected = {setIsLTOGraphSelected(it)},
            setDetailListIndex = {setLTODetailListIndex(it)},
            ltoViewModel = ltoViewModel,
            selectedDevIndex = selectedDEVIndex,
            setProgressState = {setProgressState(it)},
            detailListIndex = ltoDetailListIndex
        )
        //STO ITEM ---------------------------------------------------------------------------------
        STOItemsRow(
            stoViewModel = stoViewModel,
            selectedDevIndex = selectedDEVIndex,
            selectedLTO = selectedLTO,
            selectedSTO = selectedSTO,
            selectedLTOIndex = selectedLTOIndex,
            setSelectedSTO = {setSelectedSTO(it)},
            setAddSTOItem = {setAddSTOItem(it)},
            setDetailListIndex = {setSTODetailListIndex(it)},
            setSelectedSTOIndex = {setSelectedSTOIndex(it)},
            stoDetailViewModel = stoDetailViewModel,
            selectedSTOIndex = selectedSTOIndex,
            )
        Divider(color = MaterialTheme.colorScheme.tertiary, thickness = 4.dp)
//        STO Details Row---------------------------------------------------------------------------
        STODetailsRow(
            selectedSTO = selectedSTO,
            isSTOGraphSelected = isSTOGraphSelected,
            setIsSTOGraphSelected = {setIsSTOGraphSelected(it)},
            stoViewModel = stoViewModel,
            selectedDevIndex = selectedDEVIndex,
            selectedLTOIndex = selectedLTOIndex,
            stoDetailListIndex = stoDetailListIndex,
            setProgressState = {setProgressState(it)},
            stoDetailViewModel = stoDetailViewModel,
            )
        Row(modifier = Modifier.fillMaxWidth()) {
            if(selectedSTO != ""){
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(500.dp)
                        .padding(10.dp)
                        .border(4.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(8.dp))
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ){
                        items(STDDetailTitles) { stoDetailItem ->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .height(if(STDDetailTitles.indexOf(stoDetailItem)!=6) 40.dp else 300.dp)){
                                TableCell(text = stoDetailItem, weight = 0.3f)
                                Divider(color = MaterialTheme.colorScheme.tertiary, modifier = Modifier.fillMaxHeight().width(2.dp))
                                TableCell(text = stoDetailViewModel.getSTODetail(selectedLTOIndex, selectedDEVIndex, selectedSTOIndex).first[STDDetailTitles.indexOf(stoDetailItem)], weight = 0.7f)
                            }
                            Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.tertiary)
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(start = 0.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
                    .border(4.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(8.dp)),
                horizontalArrangement = Arrangement.End
            ) {

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(10.dp)
                .border(4.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(8.dp))
        ) {

        }

    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .weight(weight)
            .padding(8.dp)
    )

}





