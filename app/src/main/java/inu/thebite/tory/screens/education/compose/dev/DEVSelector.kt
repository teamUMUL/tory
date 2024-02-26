package inu.thebite.tory.screens.education.compose.dev

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.screens.education.viewmodel.DEVViewModel
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.teachingboard.viewmodel.CenterSelectViewModel
import inu.thebite.tory.ui.theme.fontFamily_Lato


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DEVSelector(
    modifier : Modifier = Modifier,
    devViewModel: DEVViewModel,
    ltoViewModel: LTOViewModel,
    stoViewModel: STOViewModel,
    selectedChild: StudentResponse
){


    val selectedDEV by devViewModel.selectedDEV.collectAsState()
    val allDEVs by devViewModel.allDEVs.collectAsState()


    val expandedState = rememberSaveable { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState.value) 180f else 0f, label = ""
    )

    LaunchedEffect(expandedState.value){
        if (expandedState.value){
            Log.d("isSelectedDEVSelector", "isSelected")
            devViewModel.getAllDEVs(selectedChild.childClass.center.id)
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF3F3F3)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){

        Text(
            text = "발달영역 : ",
            style = TextStyle(
                fontSize = 15.sp,
                lineHeight = 22.sp,
                fontFamily = fontFamily_Lato,
                fontWeight = FontWeight(400),
                color = Color(0xFF1D1C1D),
                ),
            modifier = Modifier
                .padding(start = 10.dp)
        )
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth(),
            expanded = expandedState.value,
            onExpandedChange = {expandedState.value = !expandedState.value}


        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BasicTextField(
                    value = selectedDEV?.name ?: "",
                    onValueChange = {},
                    readOnly = true,
                    textStyle = TextStyle(
                        fontSize = 15.sp,
                        lineHeight = 22.sp,
                        fontFamily = fontFamily_Lato,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF1D1C1D),

                        ),
                    modifier = Modifier
//                        .fillMaxHeight()
                        .weight(9f)
                        .menuAnchor()
                )
                Icon(
                    modifier = Modifier
                        .weight(1f)
                        .rotate(rotationState)
                        .size(30.dp),
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
            ExposedDropdownMenu(
                expanded = expandedState.value,
                onDismissRequest = {expandedState.value = false},
                modifier = Modifier
                    .exposedDropdownSize()
            ){
                allDEVs?.let {allDEVs ->
                    allDEVs.forEach { dev ->
                        DropdownMenuItem(
                            text = {
                                Text(text = dev.name)
                            },
                            onClick = {
                                devViewModel.setSelectedDEV(dev)
                                stoViewModel.clearSelectedSTO()
                                ltoViewModel.clearSelectedLTO()
                                expandedState.value = false
//                                ltoViewModel.getLTOsByDEV(dev)
                            }
                        )
                    }
                }

            }
        }

    }
}