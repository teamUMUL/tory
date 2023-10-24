package inu.thebite.tory.screens.education2.compose.lto

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.R
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.screens.education2.compose.dialog.lto.AddLTODialog
import inu.thebite.tory.screens.education2.compose.dialog.lto.UpdateLTOItemDialog
import inu.thebite.tory.screens.education2.viewmodel.DEVViewModel
import inu.thebite.tory.screens.education2.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education2.viewmodel.STOViewModel


@Composable
fun LTOItemColumn(
    context: Context,
    selectedLTOStatus : MutableState<String>,
    devViewModel: DEVViewModel,
    ltoViewModel: LTOViewModel,
    stoViewModel: STOViewModel
){
    val allLTOs by ltoViewModel.allLTOs.collectAsState()
    val ltos by ltoViewModel.ltos.collectAsState()
    val selectedLTO by ltoViewModel.selectedLTO.collectAsState()

    val allDEVs by devViewModel.allDEVs.collectAsState()
    val selectedDEV by devViewModel.selectedDEV.collectAsState()

    val (addLTODialog, setAddLTODialog) = remember {
        mutableStateOf(false)
    }
    val (updateLTODialog, setUpdateLTODialog) = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(selectedLTO){
        selectedLTO?.let { selectedLTO ->
            selectedLTOStatus.value = selectedLTO.status
        }
    }


    LaunchedEffect(allDEVs, selectedDEV){
        selectedDEV?.let {selectedDEV ->
            ltoViewModel.getLTOsByDEV(
                selectedDEV
            )
        }
    }
    LaunchedEffect(allLTOs){
        selectedDEV?.let {selectedDEV ->
            ltoViewModel.getLTOsByDEV(selectedDEV)
        }
    }


    if(addLTODialog){
        selectedDEV?.let {selectedDEV ->
            AddLTODialog(
                context = context,
                selectedDEV = selectedDEV,
                ltoViewModel = ltoViewModel,
                setAddLTOItem = {setAddLTODialog(it)}
            )
        }
    }
    if(updateLTODialog){
        selectedLTO?.let {selectedLTO ->
            UpdateLTOItemDialog(
                context = context,
                setUpdateLTOItem = {setUpdateLTODialog(it)},
                selectedLTO = selectedLTO,
                ltoViewModel = ltoViewModel
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(0.2f)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ){

        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
        ){
            ltos?.let { ltos ->
                items(ltos){lto ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                            .border(
                                width = 2.dp,
                                color = if (selectedLTO == lto) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(
                                color =
                                    when (lto.status) {
                                        "진행중" -> {
                                            Color.Blue.copy(alpha = 0.2f)
                                        }

                                        "중지" -> {
                                            Color.Red.copy(alpha = 0.2f)
                                        }

                                        "완료" -> {
                                            Color.Green.copy(alpha = 0.2f)
                                        }

                                        else -> {
                                            Color.Gray.copy(alpha = 0.2f)
                                        }
                                },
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                if (selectedLTO == lto) {
                                    ltoViewModel.clearSelectedCenter()
                                    selectedLTOStatus.value = ""
                                } else {
                                    ltoViewModel.setSelectedLTO(lto)
                                    stoViewModel.getSTOsByLTO(lto)
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp),
                            contentAlignment = Alignment.Center
                        ){
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(0.8f)
                                        .fillMaxHeight(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ){
                                    Text(
                                        text = "${lto.name}",
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 22.sp,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1
                                    )
                                    Text(
                                        text = "등록날짜 : ${lto.registerDate}",
                                        fontWeight = FontWeight.Light,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                    Text(
                                        text = "완료날짜 : ${lto.achieveDate}",
                                        fontWeight = FontWeight.Light,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                }
                                if(selectedLTO == lto){
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.SpaceBetween,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .clickable(
                                                    interactionSource = remember { MutableInteractionSource() },
                                                    indication = null
                                                ) {
                                                    //삭제 AlertDialog
                                                    ltoViewModel.deleteLTO(
                                                        lto
                                                    )
                                                }
                                                .size(30.dp)
                                        )
                                        Icon(
                                            painter = painterResource(id = R.drawable.icon_edit),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .clickable(
                                                    interactionSource = remember { MutableInteractionSource() },
                                                    indication = null
                                                ) {
                                                    setUpdateLTODialog(true)
                                                }
                                                .size(30.dp)
                                        )

                                    }

                                }

                            }

                        }

                    }

                }

            }

        }
        selectedDEV?.let {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp),
                onClick = {
                    setAddLTODialog(true)
                },
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(0.dp)
            ){
                Text(
                    text = "LTO 추가",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }


    }

}
