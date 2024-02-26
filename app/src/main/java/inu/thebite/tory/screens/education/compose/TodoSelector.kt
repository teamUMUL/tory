package inu.thebite.tory.screens.education.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.model.todo.TodoResponse
import inu.thebite.tory.screens.education.viewmodel.DEVViewModel
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.todo.TodoViewModel
import inu.thebite.tory.ui.theme.fontFamily_Lato

@Composable
fun TodoSelector(
    todoList : TodoResponse?,
    selectedChild: StudentResponse,
    stoViewModel: STOViewModel,
    ltoViewModel: LTOViewModel,
    devViewModel: DEVViewModel,
    todoViewModel: TodoViewModel
){
    val context = LocalContext.current

    val selectedSTO by stoViewModel.selectedSTO.collectAsState()
    val todoSTOList by stoViewModel.todoSTOList.collectAsState()

    val isLoading by todoViewModel.isLoading.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = if (isLoading) Arrangement.Center else Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading){
            CircularProgressIndicator(strokeWidth = 3.dp, modifier = Modifier.size(100.dp))
        } else {
            todoSTOList?.let { todoSTOList ->
                todoSTOList.forEachIndexed { index, sto ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) {
                                stoViewModel
                                    .findSTOById(sto.id)
                                    ?.let { foundSTO ->
                                        ltoViewModel
                                            .findLTOById(foundSTO.ltoId)
                                            ?.let { foundLTO ->
                                                devViewModel
                                                    .findDEVById(foundLTO.domainId)
                                                    ?.let { foundDEV ->
                                                        devViewModel.setSelectedDEV(foundDEV)
                                                        ltoViewModel.setSelectedLTO(foundLTO)
                                                        stoViewModel.setSelectedSTO(foundSTO)
                                                    }
                                            }

                                    }
                            }
                            .background(
                                if (selectedSTO == sto) Color(0xFF1264A3) else Color.Transparent
                            )
                            .padding(start = 20.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(
                                        color = when (sto.status) {
                                            "진행중" -> {
                                                Color(0xFF40B9FC)
                                            }

                                            "완료" -> {
                                                Color(0xFF34C648)
                                            }

                                            "중지" -> {
                                                Color(0xFFFC605C)
                                            }

                                            else -> {
                                                MaterialTheme.colorScheme.primary
                                            }
                                        },
                                        shape = CircleShape
                                    )
                            )
                            Text(
                                text = sto.name,
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    lineHeight = 22.sp,
                                    fontFamily = fontFamily_Lato,
                                    fontWeight = FontWeight(400),
                                    color = if (selectedSTO == sto) Color.White else Color.Black
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
            }
        }


    }
}