package inu.thebite.tory.screens.education.compose.sto

import android.annotation.SuppressLint
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import inu.thebite.tory.R
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.model.todo.TodoListRequest
import inu.thebite.tory.model.todo.TodoResponse
import inu.thebite.tory.model.todo.UpdateTodoList
import inu.thebite.tory.todo.TodoViewModel
import inu.thebite.tory.screens.education.screen.replaceNewLineWithSpace
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.notice.NoticeViewModel
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel
import inu.thebite.tory.ui.theme.fontFamily_Lato
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.random.Random


@Composable
fun SelectedSTORow(
    modifier: Modifier = Modifier,
    selectedChild: StudentResponse,
    selectedLTO: LtoResponse?,
    selectedSTO: StoResponse?,
    todoList: TodoResponse?,
    stoViewModel: STOViewModel,
    todoViewModel: TodoViewModel,
    noticeViewModel: NoticeViewModel,
    imageViewModel: ImageViewModel,
) {
    val context = LocalContext.current



    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        selectedSTO?.let { selectedSTO ->
            Row(
                modifier = Modifier
                    .weight(6f)
                    .padding(start = 15.dp)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .background(
                            color =
                            when (selectedSTO.status) {
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
                Spacer(modifier = Modifier.width(15.dp))
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,

                    ) {
                    BoxWithConstraints {
                        val nameWidth = maxWidth * 0.7f
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = selectedSTO.name,
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    lineHeight = 22.sp,
                                    fontFamily = fontFamily_Lato,
                                    fontWeight = FontWeight(900),
                                    color = Color(0xFF1D1C1D),
                                ),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier
                                    .widthIn(max = nameWidth)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = selectedSTO.registerDate,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp,
                                    fontFamily = fontFamily_Lato,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF616061),
                                ),
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = replaceNewLineWithSpace(selectedSTO.contents),
                            style = TextStyle(
                                fontSize = 15.sp,
                                lineHeight = 22.sp,
                                fontFamily = fontFamily_Lato,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF1D1C1D),

                                ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }



            Row(
                modifier = Modifier
                    .weight(4f)
                    .fillMaxHeight()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = {
                        todoList?.let { todoList ->
                            if (todoList.stoList.any { it == selectedSTO.id }){
                                val deleteList = todoList.stoList.map { it }.toMutableList()
                                deleteList.remove(selectedSTO.id)
                                //삭제
                                todoViewModel.updateTodoList(studentId = selectedChild.id, updateTodoList = UpdateTodoList(stoList = deleteList))
                            } else {
                                todoViewModel.addTodoList(studentId = selectedChild.id, todoListRequest = TodoListRequest(stoId = selectedSTO.id))
                            }
                        } ?: todoViewModel.addTodoList(studentId = selectedChild.id, todoListRequest = TodoListRequest(stoId = selectedSTO.id))
                    }
                ) {
                    var tint = Color(0xFF8E8E8E)
                    todoList?.let {
                        if (todoList.stoList.any{it == selectedSTO.id}){
                            tint = Color.Unspecified
                        }
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.icon_todo),
                        contentDescription = null,
                        tint = tint
                    )
                }
            }
        }
    }
}

fun getCurrentYear(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy", Locale.KOREAN)
    return currentDate.format(formatter)
}

fun getCurrentMonth(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("MM", Locale.KOREAN)
    var month = currentDate.format(formatter)

    // 앞에 첫 글자가 0인 경우 제거
    if (month.startsWith("0")) {
        month = month.substring(1)
    }
    Log.d("addDetailInfo", month)
    return month
}

fun getCurrentDate(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd", Locale.KOREAN)
    var date = currentDate.format(formatter)

    // 앞에 첫 글자가 0인 경우 제거
    if (date.startsWith("0")) {
        date = date.substring(1)
    }
    Log.d("addDetailInfo", date)
    return date
}
