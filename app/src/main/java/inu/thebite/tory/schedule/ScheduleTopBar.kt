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
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.R
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.model.todo.UpdateTodoList
import inu.thebite.tory.screens.education.viewmodel.DEVViewModel
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.teachingboard.viewmodel.ChildSelectViewModel
import inu.thebite.tory.ui.theme.fontFamily_Lato
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ScheduleTopBar(
    modifier: Modifier = Modifier,
    currentRoute: String,
    selectedChild: StudentResponse,
//    dummySTOList : MutableList<StoResponse>,
    todoViewModel: TodoViewModel,
    devViewModel: DEVViewModel,
    ltoViewModel: LTOViewModel,
    stoViewModel: STOViewModel,
    childSelectViewModel: ChildSelectViewModel
) {
    val context = LocalContext.current

    val todoList by todoViewModel.todoResponse.collectAsState()
    val tempTodoList by todoViewModel.tempTodoResponse.collectAsState()
    val todoSTOIdList by todoViewModel.todoSTOIdList.collectAsState()
    val isLoading by todoViewModel.isLoading.collectAsState()

    val isSTOListLoading by stoViewModel.isSTOListLoading.collectAsState()
    val todoSTOList by stoViewModel.todoSTOList.collectAsState()

    val selectedChild by childSelectViewModel.selectedChildInfo.collectAsState()

//    var result = listOf<Long>()
    LaunchedEffect(Unit){
        selectedChild?.let { selectedChild ->
            stoViewModel.getAllSTOs(studentId = selectedChild.id)
            todoViewModel.getTodoList(studentId = selectedChild.id, context = context)
        }
    }

    LaunchedEffect(selectedChild){
        selectedChild?.let {selectedChild ->
            todoViewModel.getTodoList(studentId = selectedChild.id, context = context)
        }
    }

//    LaunchedEffect(tempTodoList){
//        tempTodoList?.let {
//            result = it.stoList
//            Log.d("moveTodoList", result.toString())
//        }
//    }
    val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
    Row(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(),
//            .weight(4f),
        horizontalArrangement = Arrangement.spacedBy(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (currentRoute == "Education") {
            Row(
                modifier = Modifier.weight(3f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_todo),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(30.dp),
                    tint = Color.Unspecified
                )
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(
                            color = Color.White,
                            RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = todoList?.date ?: currentDate,
                        style = TextStyle(
                            fontFamily = fontFamily_Lato,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                    )
                }
            }
            Row(
                modifier = Modifier
                    .weight(7f)
                    .fillMaxHeight()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Log.d("isSTOListLoading", isSTOListLoading.toString())
                Log.d("isLoading", isLoading.toString())

                if (isSTOListLoading || isLoading){
                    CircularProgressIndicator(modifier = Modifier.size(30.dp))
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

                                },
                                devViewModel = devViewModel,
                                ltoViewModel = ltoViewModel,
                                stoViewModel = stoViewModel,
                                todoViewModel = todoViewModel,
                                childSelectViewModel = childSelectViewModel
                            )
                        }
                    }
                }
            }
        }

    }
}