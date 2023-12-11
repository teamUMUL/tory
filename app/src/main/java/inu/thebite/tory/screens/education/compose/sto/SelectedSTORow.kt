package inu.thebite.tory.screens.education.compose.sto

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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

    val (deleteSTODialog, setDeleteSTODialog) = rememberSaveable {
        mutableStateOf(false)
    }

    val points by stoViewModel.points.collectAsState()

    if (deleteSTODialog){
        selectedSTO?.let { selectedSTO ->
            AlertDialog(
                title = {Text(text = "STO : ${selectedSTO.name} 삭제하시겠습니까?")},
                onDismissRequest = {setDeleteSTODialog(false)},
                confirmButton = { TextButton(onClick = {
                    stoViewModel.deleteSTO(selectedSTO = selectedSTO)
                    setDeleteSTODialog(false)
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

    val (gameDialog, setGameDialog) = remember {
        mutableStateOf(false)
    }

    if(gameDialog){
        GameDialog(
            context = context,
            selectedLTO = selectedLTO,
            selectedSTO = selectedSTO,
            points = points,
            setGameDialog = {setGameDialog(it)},
            dragAndDropViewModel = dragAndDropViewModel,
            gameViewModel = gameViewModel,
            imageViewModel = imageViewModel,
            stoViewModel = stoViewModel
        )
    }

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
                    onClick = {
                        selectedLTO?.let {selectedLTO ->
                            when(selectedLTO.game){
                                "같은 사진 매칭" -> {
                                    //타겟 아이템 설정
                                    dragAndDropViewModel.setTargetItems(
                                        imageViewModel.findImagesByNames(selectedSTO.imageList)
                                    )
                                    //타겟 아이템 설정 확인
                                    if(dragAndDropViewModel.targetItems.value.isNotNull() && dragAndDropViewModel.targetItems.value != emptyList<ImageResponse>()){
                                        //타겟 아이템 설정 후 랜덤 유무 설정
                                        if(dragAndDropViewModel.mainItem.value.isNotNull()){
                                            //선택한 메인 아이템이 있을 경우 랜덤게임이 아니라고 설정
                                            dragAndDropViewModel.isNotRandomGame()
                                        } else {
                                            //선택한 메인 아이템이 없을 경우 랜덤게임이라고 설정 및 메인 아이템 랜덤 설정
                                            dragAndDropViewModel.setMainItem(
                                                dragAndDropViewModel.targetItems.value!![getRandomIndex(dragAndDropViewModel.targetItems.value!!.size)]
                                            )
                                            dragAndDropViewModel.isRandomGame()
                                        }

                                        setGameDialog(true)
                                    } else {
                                        Toasty.warning(context, "게임아이템을 설정해주세요", Toast.LENGTH_SHORT, true).show()
                                    }
                                }
                                "일반화 매칭" -> {
                                    //타겟 아이템 설정
                                    dragAndDropViewModel.setTargetItems(
                                        imageViewModel.findImagesByNames(selectedSTO.imageList)
                                    )
                                    //타겟 아이템 설정 확인
                                    if(dragAndDropViewModel.targetItems.value.isNotNull() && dragAndDropViewModel.targetItems.value != emptyList<ImageResponse>()){
                                        //타겟 아이템 설정 후 랜덤 유무 확인
                                        if(dragAndDropViewModel.mainItem.value.isNotNull()){
                                            dragAndDropViewModel.isNotRandomGame()
                                        } else {
                                            //선택한 메인 아이템이 없을 경우 랜덤게임이라고 설정 및 메인 아이템 랜덤 설정
                                            dragAndDropViewModel.setMainItem(
                                                dragAndDropViewModel.targetItems.value!![getRandomIndex(dragAndDropViewModel.targetItems.value!!.size)]
                                            )
                                            dragAndDropViewModel.isRandomGame()
                                        }
                                        dragAndDropViewModel.resetMainItemsGeneralMode(imageViewModel.getImagesByCategory(dragAndDropViewModel.mainItem.value!!.category.name))
                                        setGameDialog(true)

                                    } else {
                                        Toasty.warning(context, "게임아이템을 설정해주세요", Toast.LENGTH_SHORT, true).show()
                                    }
                                }
                            }
                        }
                    },

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
                            if (todoList.stoList.any { it == selectedSTO.id }){
                                val deleteList = todoList.stoList.map { it }.toMutableList()
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
                                            if (todoList.stoList.any { it == selectedSTO.id }) purpleGradient else gray
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
    val (gameButton1Index, setGameButton1Index) = rememberSaveable {
        mutableStateOf(-1)
    }
    val (gameButton2Index, setGameButton2Index) = rememberSaveable {
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