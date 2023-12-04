package inu.thebite.tory.screens.education2.compose.sto2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.R
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.sto.StoSummaryResponse
import inu.thebite.tory.model.todo.TodoListRequest
import inu.thebite.tory.model.todo.UpdateTodoList
import inu.thebite.tory.schedule.TodoViewModel
import inu.thebite.tory.screens.education2.compose.dialog.sto.UpdateSTODialog
import inu.thebite.tory.screens.education2.screen.replaceNewLineWithSpace
import inu.thebite.tory.screens.education2.viewmodel.STOViewModel
import inu.thebite.tory.ui.theme.fontFamily_Lato


@Composable
fun SelectedSTORow(
    modifier: Modifier = Modifier,
    selectedSTO: StoResponse?,
    todoList: List<StoSummaryResponse>?,
    stoViewModel: STOViewModel,
    todoViewModel: TodoViewModel
) {
    val context = LocalContext.current

    val (deleteSTODialog, setDeleteSTODialog) = rememberSaveable {
        mutableStateOf(false)
    }

    if (deleteSTODialog){
        selectedSTO?.let { selectedSTO ->
            AlertDialog(
                title = {Text(text = "STO : ${selectedSTO.name} 삭제하시겠습니까?")},
                onDismissRequest = {setDeleteSTODialog(false)},
                confirmButton = { TextButton(onClick = {
                    stoViewModel.deleteSTO(selectedSTO = selectedSTO)
                }) {
                    Text(text = "삭제")
                }},
                dismissButton = { TextButton(onClick = { setDeleteSTODialog(false) }) {
                    Text(text = "닫기")
                }}
            )
        }
    }

    val (updateSTODialog, setUpdateSTODialog) = rememberSaveable {
        mutableStateOf(false)
    }

    if(updateSTODialog){
        selectedSTO?.let {selectedSTO ->
            UpdateSTODialog(
                context = context,
                stoViewModel = stoViewModel,
                selectedSTO = selectedSTO,
                setUpdateSTOItem = {setUpdateSTODialog(it)},
            )
        }
    }

    val purpleGradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF0047B3), Color(0xFF7F5AF0))
    )
    val gray = Brush.horizontalGradient(
        colors = listOf(Color(0xFF888888), Color(0xFF888888))
    )

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

                                "준거 도달" -> {
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedSTO.name,
                            style = TextStyle(
                                fontSize = 15.sp,
                                lineHeight = 22.sp,
                                fontFamily = fontFamily_Lato,
                                fontWeight = FontWeight(900),
                                color = Color(0xFF1D1C1D),

                                )
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

                                )
                        )

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
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    onClick = {},

                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(width = 2.dp, color = Color(0xFF0047B3)),
                    contentPadding = PaddingValues(vertical = 5.dp, horizontal = 20.dp),

                    ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow, contentDescription = null,
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "교육 시작",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = fontFamily_Lato,
                            fontWeight = FontWeight(900),
                            color = Color(0xFF1D1C1D),

                            )
                    )
                }
                IconButton(
                    onClick = {
                        todoList?.let { todoList ->
                            if (todoList.any { it.id == selectedSTO.id }){
                                val deleteList = todoList.map { it.id }.toMutableList()
                                deleteList.remove(selectedSTO.id)
                                //삭제
                                todoViewModel.updateTodoList(studentId = 1L, updateTodoList = UpdateTodoList(stoList = deleteList))
                            } else {
                                todoViewModel.addTodoList(studentId = 1L, todoListRequest = TodoListRequest(stoId = selectedSTO.id))
                            }
                        } ?: todoViewModel.addTodoList(studentId = 1L, todoListRequest = TodoListRequest(stoId = selectedSTO.id))
                    }
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.icon_calendar_2),
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .graphicsLayer(alpha = 0.99f)
                            .drawWithCache {
                                onDrawWithContent {
                                    drawContent()
                                    drawRect(
                                        brush =
                                        todoList?.let { todoList ->
                                            if (todoList.any { it.id == selectedSTO.id }) purpleGradient else gray
                                        } ?: gray,
                                        blendMode = BlendMode.SrcAtop
                                    )
                                }
                            },
                    )
                }
                IconButton(
                    onClick = {
                        setUpdateSTODialog(true)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_edit),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = Color.Gray
                    )

                }
                IconButton(
                    onClick = {
                        setDeleteSTODialog(true)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}