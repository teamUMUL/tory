package inu.thebite.tory.screens.teachingboard.dialog

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.schedule.TodoViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Lato
import inu.thebite.tory.ui.theme.fontFamily_Poppins
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecentListDialog(
    setRecentListDialog: (Boolean) -> Unit,
    todoViewModel: TodoViewModel,
    selectedChild: StudentResponse
) {

    val recentTodosFilterState by todoViewModel.recentTodosFilterState.collectAsState()

    val recentTodos by todoViewModel.recentTodos.collectAsState()
    val filteredRecentTodos by todoViewModel.filteredRecentTodos.collectAsState()

    val isRecentTodoLoading by todoViewModel.isRecentTodoLoading.collectAsState()
    val startDate by todoViewModel.startDate.collectAsState()
    val endDate by todoViewModel.endDate.collectAsState()


    LaunchedEffect(recentTodosFilterState){
        recentTodosFilterState?.let { recentTodosFilterState ->
            todoViewModel.filterRecentTodoByState(state = recentTodosFilterState)
        }
    }
    LaunchedEffect(Unit) {
        if (startDate.isNullOrEmpty() && endDate.isNullOrEmpty()){
            todoViewModel.setStartDate(getDates().second)
            todoViewModel.setFinishDate(getDates().first)
            Log.d("selectedChild", selectedChild.toString())

            delay(500)
            startDate?.let {startDate ->
                endDate?.let { endDate ->
                    todoViewModel.getRecentTodoListWithDate(
                        studentId = selectedChild.id,
                        startDate = startDate,
                        endDate = endDate
                    )
                }
            }
        }

    }

    Dialog(
        onDismissRequest = {
            setRecentListDialog(false)
        },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.8f)
                .background(Color(0xFFE3E3E3), shape = RoundedCornerShape(10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RecentListDialogTopBar(
                stoState = recentTodosFilterState ?: "",
                changeState = {
                    if (recentTodosFilterState.toString() == it){
                        todoViewModel.clearFilteredRecentTodos()
                        todoViewModel.clearRecentTodosFilterState()
                    } else {
                        todoViewModel.setRecentTodosFilterState(it)
                    }
                },
                todoViewModel = todoViewModel,
                selectedChild = selectedChild
            )
            if (isRecentTodoLoading){
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    filteredRecentTodos?.let { filteredRecentTodos ->
                        items(filteredRecentTodos){todo ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(10.dp)
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = todo.date.toString(),
                                    style = TextStyle(
                                        color = Color(0xFF898989),
                                        fontFamily = fontFamily_Inter,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    modifier = Modifier
                                        .padding(start = 30.dp, end = 20.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .width(60.dp)
                                        .fillMaxHeight()
                                        .padding(vertical = 10.dp)
                                        .background(
                                            color = Color(0xFFE3E3E3),
                                            shape = RoundedCornerShape(10.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = todo.teacher,
                                        style = TextStyle(
                                            color = Color(0xFF3A3A3A),
                                            fontFamily = fontFamily_Inter,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                }
                                LazyRow(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(10.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    items(todo.sto){sto ->
                                        Surface(
                                            modifier = Modifier
                                                .width(150.dp)
                                                .fillMaxHeight(),
                                            shape = RoundedCornerShape(10.dp),
                                            color = Color.White,
                                            elevation = 2.dp
                                        ) {
                                            Column {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .weight(1f)
                                                        .background(
                                                            color = when (todo.stoStatus[todo.sto.indexOf(
                                                                sto
                                                            )]) {
                                                                "완료" -> Color(0xFFCCEFC0)
                                                                "진행중" -> Color(0xFFC0C5EF)
                                                                "중지" -> Color(0xFFEFC0C0)
                                                                else -> Color(0xFFE3E3E3)
                                                            },
                                                            shape = RoundedCornerShape(
                                                                topStart = 10.dp,
                                                                topEnd = 10.dp,
                                                                bottomEnd = 0.dp,
                                                                bottomStart = 0.dp
                                                            )
                                                        ),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = todo.lto[todo.sto.indexOf(sto)],
                                                        style = TextStyle(
                                                            fontFamily = fontFamily_Inter,
                                                            fontWeight = FontWeight.SemiBold,
                                                            fontSize = 12.sp,
                                                            color = Color.Black
                                                        )
                                                    )
                                                }
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .weight(1f),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = sto,
                                                        style = TextStyle(
                                                            fontFamily = fontFamily_Inter,
                                                            fontWeight = FontWeight.SemiBold,
                                                            fontSize = 12.sp,
                                                            color = Color.Black
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } ?: run {
                        recentTodos?.let { recentTodos ->
                            items(recentTodos){todo ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(10.dp)
                                        ),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        text = todo.date.toString(),
                                        style = TextStyle(
                                            color = Color(0xFF898989),
                                            fontFamily = fontFamily_Inter,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.SemiBold
                                        ),
                                        modifier = Modifier
                                            .padding(start = 30.dp, end = 20.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .width(60.dp)
                                            .fillMaxHeight()
                                            .padding(vertical = 10.dp)
                                            .background(
                                                color = Color(0xFFE3E3E3),
                                                shape = RoundedCornerShape(10.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = todo.teacher,
                                            style = TextStyle(
                                                color = Color(0xFF3A3A3A),
                                                fontFamily = fontFamily_Inter,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        )
                                    }
                                    LazyRow(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(10.dp),
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        items(todo.sto){sto ->
                                            Surface(
                                                modifier = Modifier
                                                    .width(150.dp)
                                                    .fillMaxHeight(),
                                                shape = RoundedCornerShape(10.dp),
                                                color = Color.White,
                                                elevation = 2.dp
                                            ) {
                                                Column {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .weight(1f)
                                                            .background(
                                                                color = when (todo.stoStatus[todo.sto.indexOf(
                                                                    sto
                                                                )]) {
                                                                    "완료" -> Color(0xFFCCEFC0)
                                                                    "진행중" -> Color(0xFFC0C5EF)
                                                                    "중지" -> Color(0xFFEFC0C0)
                                                                    else -> Color(0xFFE3E3E3)
                                                                },
                                                                shape = RoundedCornerShape(
                                                                    topStart = 10.dp,
                                                                    topEnd = 10.dp,
                                                                    bottomEnd = 0.dp,
                                                                    bottomStart = 0.dp
                                                                )
                                                            ),
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.Center
                                                    ) {
                                                        Text(
                                                            text = todo.lto[todo.sto.indexOf(sto)],
                                                            style = TextStyle(
                                                                fontFamily = fontFamily_Inter,
                                                                fontWeight = FontWeight.SemiBold,
                                                                fontSize = 12.sp,
                                                                color = Color.Black
                                                            )
                                                        )
                                                    }
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .weight(1f),
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.Center
                                                    ) {
                                                        Text(
                                                            text = sto,
                                                            style = TextStyle(
                                                                fontFamily = fontFamily_Inter,
                                                                fontWeight = FontWeight.SemiBold,
                                                                fontSize = 12.sp,
                                                                color = Color.Black
                                                            )
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
            }

            Button(
                onClick = {
                    setRecentListDialog(false)
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFBFBFBF)
                ),
                modifier = Modifier
                    .width(320.dp)
                    .height(60.dp)
                    .padding(vertical = 10.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "취소",
                    style = TextStyle(
                        fontFamily = fontFamily_Poppins,
                        fontWeight = FontWeight.Medium,
                        fontSize = 26.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                )

            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecentListDialogTopBar(
    stoState: String,
    changeState: (String) -> Unit,
    todoViewModel: TodoViewModel,
    selectedChild: StudentResponse
) {


    val startDate by todoViewModel.startDate.collectAsState()
    val endDate by todoViewModel.endDate.collectAsState()






    val startDateCalendarState = rememberSheetState()
    CalendarDialog(
        state = startDateCalendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
        ),
        selection = CalendarSelection.Date { date ->
            todoViewModel.setStartDate(formatDate(date))
        }
    )
    val finishDateCalendarState = rememberSheetState()
    CalendarDialog(
        state = finishDateCalendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
        ),
        selection = CalendarSelection.Date { date ->
            todoViewModel.setFinishDate(formatDate(date))
        }
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "최근 중재 목록",
            style = TextStyle(
                fontFamily = fontFamily_Inter,
                fontWeight = FontWeight(400),
                fontSize = 33.sp,
                color = Color.Black
            ),
            modifier = Modifier.padding(start = 10.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    startDateCalendarState.show()
                },
                modifier = Modifier
                    .height(35.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 10.dp)
            ) {
                Text(
                    text = startDate ?: "YYYY-MM-DD",
                    style = TextStyle(
                        fontFamily = fontFamily_Lato,
                        fontWeight = FontWeight(400),
                        fontSize = 18.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                )
            }

            Text(
                text = "~",
                style = TextStyle(
                    fontFamily = fontFamily_Lato,
                    fontWeight = FontWeight(400),
                    fontSize = 18.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
            )
            Button(
                onClick = {
                    finishDateCalendarState.show()
                },
                modifier = Modifier
                    .height(35.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 10.dp)
            ) {
                Text(
                    text = endDate ?: "YYYY-MM-DD",
                    style = TextStyle(
                        fontFamily = fontFamily_Lato,
                        fontWeight = FontWeight(400),
                        fontSize = 18.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                )
            }
            Button(
                onClick = {
                    startDate?.let {startDate ->
                        endDate?.let { endDate ->
                            todoViewModel.getRecentTodoListWithDate(
                                studentId = selectedChild.id,
                                startDate = startDate,
                                endDate = endDate
                            )
                        }
                    }
                },
                modifier = Modifier
                    .height(35.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0047B3),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 35.dp)
            ) {
                Text(
                    text = "조회",
                    style = TextStyle(
                        fontFamily = fontFamily_Lato,
                        fontWeight = FontWeight(400),
                        fontSize = 18.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(1.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val buttonList = listOf(
                    "진행중",
                    "완료",
                    "중지"
                )
                buttonList.forEach { button ->
                    OutlinedButton(
                        modifier = Modifier
                            .height(35.dp)
                            .padding(horizontal = 2.dp),
                        onClick = {
                            changeState(button)
                        },
                        border = BorderStroke(
                            width = 1.dp,
                            color = when (button) {
                                "완료" -> Color(0xFF34C648)
                                "진행중" -> Color(0xFF40B9FC)
                                "중지" -> Color(0xFFFC605C)
                                else -> Color.Black
                            }
                        ),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when (button) {
                                "완료" -> if (stoState == button) Color(
                                    0xFF34C648
                                ) else Color.Transparent

                                "진행중" -> if (stoState == button) Color(0xFF40B9FC) else Color.Transparent
                                "중지" -> if (stoState == button) Color(0xFFFC605C) else Color.Transparent
                                else -> if (stoState == button) Color.White else Color.White
                            },
                            contentColor = when (button) {
                                "완료" -> if (stoState == button) Color.White else Color(
                                    0xFF34C648
                                )

                                "진행중" -> if (stoState == button) Color.White else Color(
                                    0xFF40B9FC
                                )

                                "중지" -> if (stoState == button) Color.White else Color(
                                    0xFFFC605C
                                )

                                else -> if (stoState == button) Color.White else Color.Black
                            }
                        ),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = button,
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = fontFamily_Lato,
                                fontWeight = FontWeight(500),
                                textAlign = TextAlign.Center,
                                color = when (button) {
                                    "완료" -> if (stoState == button) Color.White else Color(
                                        0xFF34C648
                                    )

                                    "진행중" -> if (stoState == button) Color.White else Color(
                                        0xFF40B9FC
                                    )

                                    "중지" -> if (stoState == button) Color.White else Color(
                                        0xFFFC605C
                                    )

                                    else -> if (stoState == button) Color.White else Color.Black
                                }
                            )
                        )
                    }
                }
            }
        }
    }
}

fun getDates(): Pair<String, String> {
    val today = LocalDate.now()
    val fiveDaysAgo = today.minusDays(5)

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formattedToday = today.format(formatter)
    val formattedFiveDaysAgo = fiveDaysAgo.format(formatter)

    return Pair(formattedToday, formattedFiveDaysAgo)
}

fun formatDate(date: LocalDate): String {
    return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}