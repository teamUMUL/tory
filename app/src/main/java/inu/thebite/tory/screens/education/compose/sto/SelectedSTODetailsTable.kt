package inu.thebite.tory.screens.education.compose.sto

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.model.sto.EtcRequest
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Lato


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SelectedSTODetailsTable(
    modifier: Modifier = Modifier,
    selectedSTO: StoResponse,
    stoViewModel: STOViewModel
) {
    val focusRequester = remember { FocusRequester() }

    val verticalScrollState = rememberScrollState()
    val STODetailTitles =
        listOf<String>(
            "이름",
            "내용",
            "시도 수",
            "준거 도달 기준",
            "촉구방법",
            "강화 메세지",
        )
//    var stoSignificant by rememberSaveable(stateSaver = TextFieldValue.Saver) {
//        mutableStateOf(TextFieldValue())
//    }
    val stoSignificant by stoViewModel.userInput.collectAsState()
    val isSaving by stoViewModel.isSavingData.collectAsState()

    val selectedAccidentActivities = remember {
        mutableStateListOf<String>()
    }
    val selectedStressState = remember {
        mutableStateOf("")
    }
    val selectedFocusState = remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "STO 세부사항",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = fontFamily_Lato,
                fontWeight = FontWeight(400),
                color = Color(0xFF1D1C1D),
            ),
            modifier = Modifier.padding(start = 20.dp, top = 30.dp)
        )
        Column(
            modifier = modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .fillMaxWidth()
                .weight(4f)
//                .border(width = 2.dp, color = Color(0x4D000000), shape = RectangleShape)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 3.dp, color = Color(0xFFF3F3F3), shape = RectangleShape)

            ) {
                items(STODetailTitles) { stoDetailItem ->
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val selectedSTODetail = listOf(
                            selectedSTO.name ?: "",
                            selectedSTO.contents ?: "",
                            "${selectedSTO.count ?: ""}회",
                            if (selectedSTO.goalType == "퍼센트") "${selectedSTO.goalAmount ?: ""}%" else "연속 ${selectedSTO.goalAmount}회",
                            selectedSTO.urgeContent ?: "",
                            selectedSTO.enforceContent ?: "",
                            selectedSTO.memo ?: "",
                        )
                        TableCell(
                            text = stoDetailItem,
                            weight = 0.3f,
                            isBigger = stoDetailItem == "내용"
                        )

                        TableCellWithLeftLine(
                            text = selectedSTODetail[STODetailTitles.indexOf(stoDetailItem)],
                            weight = 0.7f,
                            isBigger = stoDetailItem == "내용"
                        )

                    }
                    Divider(thickness = 2.dp, color = Color(0xFFF3F3F3))
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(verticalScrollState)
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .weight(6f)
        ) {
            val accidentStates = listOf(
                "고함", "장난", "폭력", "회피", "자해", "말 끊기", "물건 던지기", "반복 행동", "무발언", "반복 발언", "거부"
            )
            val stressStates = listOf(
                "매우 나쁨", "나쁨", "보통", "좋음", "매우 좋음"
            )
            val concentrations = listOf(
                "매우 나쁨", "나쁨", "보통", "좋음", "매우 좋음"
            )

            Text(
                text = "돌발행동",
                style = TextStyle(
                    fontFamily = fontFamily_Lato,
                    color = Color(0xFF1D1C1D),
                    fontSize = 18.sp
                ),
            )
            STOStateButtonsLazyHorizontalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                gridCells = 2,
                stateList = accidentStates,
                onClick = {
                    if (selectedSTO.looseCannonList.contains(it)){
                        stoViewModel.removeSTOLooseCannon(stoId = selectedSTO.id, etcRequest = EtcRequest(it))
                    } else {
                        stoViewModel.updateSTOLooseCannon(stoId = selectedSTO.id, etcRequest = EtcRequest(it))
                    }
                },
                selectedCells = selectedSTO.looseCannonList
            )
            Text(
                text = "스트레스 상태",
                style = TextStyle(
                    fontFamily = fontFamily_Lato,
                    color = Color(0xFF1D1C1D),
                    fontSize = 18.sp
                ),
            )
            STOStateButtonsLazyHorizontalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp),
                gridCells = 1,
                stateList = stressStates,
                onClick = {
                    if (selectedSTO.stressStatus == it){
                        stoViewModel.updateSTOStressStatus(stoId = selectedSTO.id, etcRequest = EtcRequest("없음"))
                    } else {
                        stoViewModel.updateSTOStressStatus(stoId = selectedSTO.id, etcRequest = EtcRequest(it))
                    }
                },
                selectedCell = selectedSTO.stressStatus
            )
            Text(
                text = "집중도",
                style = TextStyle(
                    fontFamily = fontFamily_Lato,
                    color = Color(0xFF1D1C1D),
                    fontSize = 18.sp
                ),
            )
            STOStateButtonsLazyHorizontalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp),
                gridCells = 1,
                stateList = concentrations,
                onClick = {
                    if (selectedSTO.concentration == it){
                        stoViewModel.updateSTOConcentration(stoId = selectedSTO.id, etcRequest = EtcRequest("없음"))
                    } else {
                        stoViewModel.updateSTOConcentration(stoId = selectedSTO.id, etcRequest = EtcRequest(it))
                    }
                },
                selectedCell = selectedSTO.concentration
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "특이사항",
                    style = TextStyle(
                        fontFamily = fontFamily_Lato,
                        color = Color(0xFF1D1C1D),
                        fontSize = 18.sp
                    ),
                )
                if (isSaving){
                    Spacer(modifier = Modifier.width(5.dp))
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 3.dp)
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "저장 중...")
                }
            }

            TextField(
                value = stoSignificant,
                onValueChange = {
                    stoViewModel.updateInput(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0xFF0047B3).copy(alpha = 0.5f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        stoViewModel.updateFocus(focusState.hasFocus)
                    },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                singleLine = false,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.None)
            )

        }
    }

}


@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    isBigger: Boolean = false
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 14.sp,
            fontFamily = fontFamily_Inter,
            fontWeight = FontWeight(500),
            color = Color.Black,

            textAlign = TextAlign.Center,
        ),
        modifier = Modifier
            .weight(weight)
            .padding(horizontal = 8.dp, vertical = if (isBigger) 15.dp else 5.dp),

        )

}

@Composable
fun RowScope.TableCellWithLeftLine(
    text: String,
    weight: Float,
    isBigger: Boolean = false
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 14.sp,
            fontFamily = fontFamily_Inter,
            fontWeight = FontWeight(500),
            color = Color(0xFF000000),

            textAlign = TextAlign.Center,
        ),
        modifier = Modifier
            .weight(weight)
            .padding(horizontal = 8.dp, vertical = if (isBigger) 15.dp else 5.dp)
            .drawBehind {
                val strokeWidth = 3f
                val x = size.width - strokeWidth
                val y = size.height - strokeWidth
                //left line
                drawLine(
                    color = Color(0xFFF3F3F3),
                    start = Offset(0f - 10f, 0f - 40f), //(0,0) at top-left point of the box
                    end = Offset(0f - 10f, y + 40f),//bottom-left point of the box
                    strokeWidth = strokeWidth
                )
            }
    )

}

@Composable
fun STOStateButtonsLazyHorizontalGrid(
    modifier : Modifier = Modifier,
    gridCells: Int,
    stateList: List<String>,
    onClick : (String) -> Unit,
    selectedCell : String = "",
    selectedCells : List<String> = listOf()
){
    LazyHorizontalGrid(
        rows = GridCells.Fixed(gridCells),
        modifier = modifier,
        userScrollEnabled = false
    ) {
        items(stateList) { stateItem ->
            Button(
                onClick = {
                    onClick(stateItem)
                },
                modifier = Modifier
                    .height(30.dp)
                    .padding(bottom = 2.dp, end = 5.dp, top = 2.dp),
                border = BorderStroke(width = 1.dp, color = Color(0xFF0047B3).copy(alpha = 0.5f)),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                        if (gridCells == 2) {
                            if (selectedCells.contains(stateItem)){
                                Color(0xFFE0E9F5)
                            } else {
                                Color.Transparent
                            }
                        } else {
                            if (selectedCell == stateItem){
                                Color(0xFFE0E9F5)
                            } else {
                                Color.Transparent
                            }
                        },
                    contentColor = Color.Black
                ),
                contentPadding = PaddingValues(vertical = 1.dp, horizontal = 10.dp)
            ) {
                Text(text = stateItem)
            }
        }
    }
}