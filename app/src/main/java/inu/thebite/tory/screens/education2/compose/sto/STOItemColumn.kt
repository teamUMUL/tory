package inu.thebite.tory.screens.education2.compose.sto

import android.content.Context
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.R
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.screens.education.Compose.STODetailTableAndGameResult
import inu.thebite.tory.screens.education2.compose.dialog.sto.AddSTODialog
import inu.thebite.tory.screens.education2.compose.dialog.sto.UpdateSTODialog
import inu.thebite.tory.screens.education2.viewmodel.EducationViewModel
import inu.thebite.tory.screens.education2.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education2.viewmodel.STOViewModel

@Composable
fun STOItemColumn(
    ltoViewModel: LTOViewModel,
    educationViewModel : EducationViewModel,
    stoViewModel: STOViewModel,
    addSTODialog : Boolean,
    setAddSTODialog : (Boolean) -> Unit
){
    val context = LocalContext.current
    val allLTOs by ltoViewModel.allLTOs.collectAsState()
    val ltos by ltoViewModel.ltos.collectAsState()
    val selectedLTO by ltoViewModel.selectedLTO.collectAsState()
    val selectedSTO by stoViewModel.selectedSTO.collectAsState()
    val stos by stoViewModel.stos.collectAsState()

    val (updateSTODialog, setUpdateSTODialog) = remember {
        mutableStateOf(false)
    }
    if(updateSTODialog){
        selectedSTO?.let {selectedSTO ->
            UpdateSTODialog(
                context = context,
                stoViewModel = stoViewModel,
                selectedSTO = selectedSTO,
                setUpdateSTOItem = {setUpdateSTODialog(it)},
                setSelectedTryNum = {}
            )
        }
    }

    if(addSTODialog){
        selectedLTO?.let {selectedLTO ->
            AddSTODialog(
                setAddSTOItem = {setAddSTODialog(it)},
                stoViewModel = stoViewModel,
                selectedLTO = selectedLTO
            )
        }
    }

    LaunchedEffect(selectedLTO){
        selectedLTO?.let { selectedLTO ->
            stoViewModel.getSTOsByLTO(selectedLTO)
        }
    }

    val selectedSTODetailGameDataIndex = remember { mutableIntStateOf(0) }
    val selectedSTOStatus = rememberSaveable{ mutableStateOf("") }

    val selectedEducation by educationViewModel.selectedEducation.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ){
        stos?.let { stos ->
            LazyColumn(
                modifier = Modifier.padding(10.dp)
            ){

                items(stos){sto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                            .border(
                                2.dp,
                                color = if (selectedSTO == sto) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                                shape = RoundedCornerShape(8.dp)
                            )
//                        .clickable(
//                            interactionSource = remember { MutableInteractionSource() },
//                            indication = null
//                        ) {
//
//                        }
                            .animateContentSize(
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = LinearOutSlowInEasing
                                )
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        )
                    ){
                        Column(
                            modifier = Modifier
                                .background(color =
                                if (selectedSTO == sto){
                                    when(selectedSTOStatus.value){
                                        "진행중" -> {
                                            Color.Blue.copy(alpha = 0.1f)
                                        }
                                        "준거 도달" -> {
                                            Color.Green.copy(alpha = 0.1f)

                                        }
                                        "중지" -> {
                                            Color.Red.copy(alpha = 0.1f)

                                        }
                                        else -> {
                                            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)
                                        }
                                    }
                                } else {
                                    when(sto.status){
                                        "진행중" -> {
                                            Color.Blue.copy(alpha = 0.1f)
                                        }
                                        "준거 도달" -> {
                                            Color.Green.copy(alpha = 0.1f)

                                        }
                                        "중지" -> {
                                            Color.Red.copy(alpha = 0.1f)

                                        }
                                        else -> {
                                            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)
                                        }
                                    }
                                }
                                )

                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        if (selectedSTO == sto) {
                                            stoViewModel.clearSelectedSTO()
                                            educationViewModel.clearSelectedEducation()
                                            selectedSTOStatus.value = ""
                                        } else {
                                            stoViewModel.setSelectedSTO(sto)
                                            educationViewModel.setSelectedEducation(selectedSTO = sto)
//                                        selectedSTOStatus.value = selectedSTO!!.status
                                        }
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = sto.name ,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .weight(6f)
                                        .padding(start = 20.dp),
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                                Row(
                                    modifier = Modifier
                                        .weight(
                                            if(selectedSTO == sto){
                                                6f
                                            } else {
                                                1f
                                            }
                                        ),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    if(selectedSTO == sto){
                                        selectedSTO?.let { selectedSTO->
                                            STOStatusButtons(
                                                modifier = Modifier.weight(4f),
                                                selectedSTO = selectedSTO,
                                                selectedSTOStatus = selectedSTOStatus,
                                                setSelectedSTOStatus = {
                                                    selectedSTOStatus.value = it
                                                    selectedSTO.status = it
                                                    Log.d("클릭 감지", selectedSTO.toString())
                                                }
                                            )
                                            STOSettingButtons(
                                                modifier = Modifier.weight(2f),
                                                setUpdateSTODialog = {
                                                    setUpdateSTODialog(it)
                                                }
                                            )
                                            Spacer(modifier = Modifier.width(10.dp))
                                        }
                                    }
                                }

                            }

                            if(selectedSTO == sto){
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(700.dp)
                                        .border(
                                            width = 2.dp,
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .background(
                                            color = Color.Transparent,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                ) {
                                    selectedSTO?.let { selectedSTO->
                                        selectedEducation?.let {selectedEducation ->
                                            STODetailTableAndGameResult(
                                                selectedSTO = selectedSTO,
                                                selectedEducation = selectedEducation,
                                                selectedSTODetailGameDataIndex = selectedSTODetailGameDataIndex,
                                                educationViewModel = educationViewModel,
                                                setSelectedSTOStatus = {
                                                    selectedSTOStatus.value = it
                                                    selectedSTO.status = it
                                                }
                                            )
                                        }
                                        GameReadyRow(
                                            selectedSTO = selectedSTO,
                                            setAddGameItem = {},
                                        )

                                    }

                                }
                            }
                        }
                    }

                }
            }
        }


    }
}


