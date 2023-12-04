package inu.thebite.tory.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import inu.thebite.tory.R
import inu.thebite.tory.model.todo.UpdateTodoList
import inu.thebite.tory.screens.education.viewmodel.DEVViewModel
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education.viewmodel.STOViewModel

@Composable
fun ScheduleTopBar(
    modifier: Modifier = Modifier,
    currentRoute: String,
//    dummySTOList : MutableList<StoResponse>,
    todoViewModel: TodoViewModel,
    devViewModel: DEVViewModel,
    ltoViewModel: LTOViewModel,
    stoViewModel: STOViewModel
) {
    val todoList by todoViewModel.todoList.collectAsState()
    val tempTodoList by todoViewModel.tempTodoList.collectAsState()

    Row(
        modifier = modifier
            .fillMaxHeight(),
//            .weight(4f),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (currentRoute == "Education") {
            Icon(
                painter = painterResource(id = R.drawable.icon_schedule),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 30.dp)
                    .size(25.dp),
                tint = Color.White
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight()
                    .background(
                        color = Color(0xFF9175E7),
                        shape = RoundedCornerShape(10.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                tempTodoList?.let { tempTodoList ->
                    DragDropList(
                        items = tempTodoList,
                        onMove = { fromIndex, toIndex ->
                            todoViewModel.moveTempTodoList(fromIndex, toIndex)
                        },
                        onDragEnd = {
                            tempTodoList?.let { tempTodoList ->
                                todoViewModel.updateTodoList(
                                    studentId = 1L,
                                    updateTodoList = UpdateTodoList(
                                        tempTodoList.map { it.id }
                                    )
                                )
                            }
                        },
                        devViewModel = devViewModel,
                        ltoViewModel = ltoViewModel,
                        stoViewModel = stoViewModel
                    )
                }


            }
        }

    }
}