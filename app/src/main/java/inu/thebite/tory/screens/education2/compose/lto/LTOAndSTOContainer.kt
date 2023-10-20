package inu.thebite.tory.screens.education2.compose.lto

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.screens.education2.compose.sto.STOItemColumn
import inu.thebite.tory.screens.education2.viewmodel.DEVViewModel
import inu.thebite.tory.screens.education2.viewmodel.EducationViewModel
import inu.thebite.tory.screens.education2.viewmodel.LTOViewModel

@Composable
fun LTOAndSTOContainer(
    context : Context,
    devViewModel: DEVViewModel,
    ltoViewModel: LTOViewModel,
    educationViewModel : EducationViewModel
){
    val selectedLTO by ltoViewModel.selectedLTO.collectAsState()

    val selectedLTOStatus = remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxSize()) {
        LTOItemColumn(
            context = context,
            selectedLTOStatus = selectedLTOStatus,
            devViewModel = devViewModel,
            ltoViewModel = ltoViewModel
        )
        Divider(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight(),
            color = MaterialTheme.colorScheme.tertiary
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LTODetailRow(
                ltoViewModel = ltoViewModel,
                selectedLTOStatus = selectedLTOStatus
            )
            Divider(
                thickness = 4.dp,
                color = MaterialTheme.colorScheme.tertiary
            )
            STOItemColumn(
                ltoViewModel = ltoViewModel,
                educationViewModel = educationViewModel
            )
        }

    }
}


@Composable
fun LTODetailRow(
    ltoViewModel : LTOViewModel,
    selectedLTOStatus : MutableState<String>
){

    val ltos by ltoViewModel.ltos.collectAsState()
    val selectedLTO by ltoViewModel.selectedLTO.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        selectedLTO?.let { selectedLTO ->
            Text(
                text = "LTO : ",
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(start = 10.dp)
            )
            Text(
                text = selectedLTO.name,
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(7f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            LTOStatusButtons(
                modifier = Modifier.weight(3.5f),
                selectedLTO = selectedLTO,
                selectedLTOStatus = selectedLTOStatus,
                setSelectedLTOStatus =  {
                    selectedLTOStatus.value = it
                    selectedLTO.status = it
                    ltoViewModel.setSelectedLTO(selectedLTO)
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            OutlinedButton(
                modifier = Modifier
                    .weight(1.5f)
                    .height(40.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(5.dp),
                onClick = {
                    //STO 추가 Dialog
                },
                contentPadding = PaddingValues(6.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxSize(),
                    text = "STO 추가",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(10.dp))

        }
    }
}


@Composable
fun LTOStatusButtons(
    modifier : Modifier,
    selectedLTO : LtoResponse,
    selectedLTOStatus : MutableState<String>,
    setSelectedLTOStatus : (String) -> Unit
){
    val cornerRadius = 8.dp
    val ltoStatusList = listOf<String>(
        "진행중",
        "중지",
        "완료"
    )
    LaunchedEffect(Unit){
        selectedLTOStatus.value = selectedLTO.status
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ltoStatusList.forEachIndexed { index, item ->
            OutlinedButton(
                onClick = {

                    setSelectedLTOStatus(item)
                },
                shape = when (index) {
                    // left outer button
                    0 -> RoundedCornerShape(
                        topStart = cornerRadius,
                        topEnd = 0.dp,
                        bottomStart = cornerRadius,
                        bottomEnd = 0.dp
                    )
                    // right outer button
                    ltoStatusList.size - 1 -> RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = cornerRadius,
                        bottomStart = 0.dp,
                        bottomEnd = cornerRadius
                    )
                    // middle button
                    else -> RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                },
                border = BorderStroke(
                    1.dp,
                    if (selectedLTO.status == item) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondary.copy(
                        alpha = 0.75f
                    )
                ),
                modifier = when (index) {
                    0 ->
                        Modifier
                            .offset(0.dp, 0.dp)
                            .zIndex(if (selectedLTO.status == item) 1f else 0f)

                    else ->
                        Modifier
                            .offset((-1 * index).dp, 0.dp)
                            .zIndex(if (selectedLTO.status == item) 1f else 0f)
                },
                colors =
                if (selectedLTOStatus.value == item) {
                    ButtonDefaults.outlinedButtonColors(
                        containerColor =
                        when (index) {
                            0 -> {
                                Color.Blue
                            }

                            1 -> {
                                Color.Red
                            }

                            else -> {
                                Color.Green
                            }
                        },
                        contentColor = Color.White
                    )
                } else {
                    ButtonDefaults.outlinedButtonColors(
                        containerColor =
                        when (index) {
                            0 -> {
                                Color.Blue.copy(alpha = 0.2f)
                            }

                            1 -> {
                                Color.Red.copy(alpha = 0.2f)
                            }

                            else -> {
                                Color.Green.copy(alpha = 0.2f)
                            }
                        },
                        contentColor = Color.Black
                    )
                }

            ) {
                Text(
                    text = ltoStatusList[index]
                )
            }
        }
    }
}