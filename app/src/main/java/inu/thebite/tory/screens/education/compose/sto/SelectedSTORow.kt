package inu.thebite.tory.screens.education.compose.sto

import android.annotation.SuppressLint
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
import co.yml.charts.common.extensions.isNotNull
import es.dmoral.toasty.Toasty
import inu.thebite.tory.R
import inu.thebite.tory.model.image.ImageResponse
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.sto.UpdateStoRequest
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.model.todo.TodoListRequest
import inu.thebite.tory.model.todo.TodoResponse
import inu.thebite.tory.model.todo.UpdateTodoList
import inu.thebite.tory.schedule.TodoViewModel
import inu.thebite.tory.screens.education.compose.dialog.sto.UpdateSTODialog
import inu.thebite.tory.screens.education.screen.replaceNewLineWithSpace
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.game.GameScreen
import inu.thebite.tory.screens.game.GameTopBar
import inu.thebite.tory.screens.game.viewmodel.DragAndDropViewModel
import inu.thebite.tory.screens.game.viewmodel.GameViewModel
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
    gameViewModel: GameViewModel,
    dragAndDropViewModel: DragAndDropViewModel
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

@Composable
fun GameDialog(
    context : Context,
    selectedLTO : LtoResponse?,
    selectedSTO: StoResponse?,
    points : List<String>?,
    setGameDialog : (Boolean) -> Unit,
    dragAndDropViewModel: DragAndDropViewModel,
    gameViewModel: GameViewModel,
    imageViewModel: ImageViewModel,
    stoViewModel: STOViewModel

){
    val (gameButton1Index, setGameButton1Index) = remember {
        mutableStateOf(-1)
    }
    val (gameButton2Index, setGameButton2Index) = remember {
        mutableStateOf(-1)
    }
    val timerStart = remember { mutableStateOf(false) }
    val timerRestart = remember { mutableStateOf(false) }

    val (isCardSelectEnd, setIsCardSelectEnd) = rememberSaveable {
        mutableStateOf(false)
    }

    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        onDismissRequest = { setGameDialog(false) }
    ){
        val windowManager =
            remember { context.getSystemService(Context.WINDOW_SERVICE) as WindowManager }

        val metrics = DisplayMetrics().apply {
            windowManager.defaultDisplay.getRealMetrics(this)
        }
        val (width, height) = with(LocalDensity.current) {
            Pair(metrics.widthPixels.toDp(), metrics.heightPixels.toDp())
        }
        Column(
            modifier = Modifier
                .requiredSize(width = width, height = height)
        ){
            selectedLTO?.let { selectedLTO ->
                selectedSTO?.let { selectedSTO ->
                    points?.let {points ->
                        GameTopBar(
                            context = context,
                            dragAndDropViewModel = dragAndDropViewModel,
                            gameViewModel = gameViewModel,
                            selectedSTO = selectedSTO,
                            selectedLTO = selectedLTO,
                            points = points,
                            gameButton1Index = gameButton1Index,
                            gameButton2Index = gameButton2Index,
                            timerStart = timerStart,
                            timerRestart = timerRestart,
                            setGameDialog = {setGameDialog(it)},
                            setGameButton1Index = {setGameButton1Index(it)},
                            setGameButton2Index = {setGameButton2Index(it)},
                            setIsCardSelectEnd = {setIsCardSelectEnd(it)},
                            imageViewModel = imageViewModel
                        )
                    }
                }
            }

            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)

            ) {
                selectedLTO?.let {selectedLTO ->
                    selectedSTO?.let { selectedSTO ->
                        points?.let {points ->
                            GameScreen(
                                context = context,
                                dragAndDropViewModel = dragAndDropViewModel,
                                gameViewModel = gameViewModel,
                                stoViewModel = stoViewModel,
                                imageViewModel = imageViewModel,
                                selectedSTO = selectedSTO,
                                selectedLTO = selectedLTO,
                                points = points,
                                timerStart = timerStart,
                                timerRestart = timerRestart,
                                resetGameButtonIndex = {setGameButton1Index(-1)},
                                setIsCardSelectEnd = {setIsCardSelectEnd(it)},
                                isCardSelectEnd = isCardSelectEnd
                            )
                        }
                    }
                }
            }
        }
    }
}
fun getRandomIndex(itemSize: Int): Int {
    return Random.nextInt(0, itemSize)
}


@SuppressLint("SuspiciousIndentation")
fun getSTODescription(selectedSTO : StoResponse, isRandom : Boolean, dragAndDropViewModel: DragAndDropViewModel): String {


    var gameItemsByKorean = ""
        selectedSTO.imageList.forEachIndexed { index, gameItem ->
            gameItemsByKorean += if(index == 0){
                englishToKorean(extractWord(gameItem))
            } else {
                ", ${englishToKorean(extractWord(gameItem))}"
            }
    }

    val description =
        if (isRandom){
            "${selectedSTO.imageList.size} Array\n목표아이템 : 랜덤\n예시아이템 : $gameItemsByKorean "
        } else {
            "${selectedSTO.imageList.size} Array\n목표아이템 :${englishToKorean(extractWord(dragAndDropViewModel.mainItem.value!!.name))}\n예시아이템 : $gameItemsByKorean"

        }

    return description
}
fun extractWord(input: String): String {
    val regex = "([a-zA-Z]+)_\\d+".toRegex()
    val matchResult = input.let { regex.find(it) }

    return matchResult?.groups?.get(1)?.value ?: input
}

@SuppressLint("DiscouragedApi")
fun getResourceIdByName(imageName: String, context: Context): Int {
    // 이 함수는 이미지 리소스 이름을 리소스 ID로 변환합니다.
    val packageName = context.packageName
    return context.resources.getIdentifier(imageName, "drawable", packageName)
}

fun englishToKorean(english : String):String{
    var korean = ""
    when(english){
        "Ball" -> {
            korean = "공"
        }
        "Block" -> {
            korean = "블록"
        }
        "Watch" -> {
            korean = "시계"
        }
        "ColorPencil" -> {
            korean = "색연필"
        }
        "Cup" -> {
            korean = "컵"
        }
        "Doll" -> {
            korean = "인형"
        }
        "Scissors" -> {
            korean = "가위"
        }
        "Socks" -> {
            korean = "양말"
        }
        "Spoon" -> {
            korean = "숫가락"
        }
        "ToothBrush" -> {
            korean = "칫솔"
        }
        else -> korean = english
    }
    return korean
}
