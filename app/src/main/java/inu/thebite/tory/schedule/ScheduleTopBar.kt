package inu.thebite.tory.schedule

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import co.yml.charts.common.extensions.isNotNull
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
    val todoList by todoViewModel.todoResponse.collectAsState()
    val tempTodoList by todoViewModel.tempTodoResponse.collectAsState()
    val todoSTOIdList by todoViewModel.todoSTOIdList.collectAsState()
    val isLoading by todoViewModel.isLoading.collectAsState()

    val isSTOListLoading by stoViewModel.isSTOListLoading.collectAsState()
    val todoSTOList by stoViewModel.todoSTOList.collectAsState()


//    var result = listOf<Long>()
    LaunchedEffect(Unit){
        stoViewModel.getAllSTOs(studentId = 1L)
        todoViewModel.getTodoList(studentId = 1L)
    }

//    LaunchedEffect(tempTodoList){
//        tempTodoList?.let {
//            result = it.stoList
//            Log.d("moveTodoList", result.toString())
//        }
//    }

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
                Log.d("isSTOListLoading", isSTOListLoading.toString())
                Log.d("isLoading", isLoading.toString())

                if (isSTOListLoading || isLoading){
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                } else {
                    if(!tempTodoList.isNotNull()){
                        Log.d("tempTodoListIsNull", "null")
                    }
                    Log.d("updatedTempTodoList", tempTodoList.toString())
                    tempTodoList?.let { tempTodoList ->
                        stoViewModel.setTodoSTOListByIds(tempTodoList.stoList)
                        todoSTOList?.let {todoSTOList ->
                            DragDropList(
                                items = todoSTOList,
                                onMove = { fromIndex, toIndex ->
                                    todoViewModel.moveTempTodoList(fromIndex, toIndex)
                                    Log.d("resultTempTodoResponseSTOList", todoViewModel.tempTodoResponse.value!!.stoList.toString())
                                },
                                onDragEnd = {
                                    Log.d("resultTempTodoResponseSTOList", todoViewModel.tempTodoResponse.value!!.stoList.toString())
                                    todoViewModel.updateTodoList(
                                        studentId = 1L,
                                        updateTodoList = UpdateTodoList(
                                            todoViewModel.tempTodoResponse.value!!.stoList
                                        )
                                    )
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

    }
}