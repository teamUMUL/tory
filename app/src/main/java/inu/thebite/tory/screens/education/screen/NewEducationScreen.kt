package inu.thebite.tory.screens.education.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.schedule.TodoViewModel
import inu.thebite.tory.screens.education.compose.LTOAndSTOSelector
import inu.thebite.tory.screens.education.compose.SelectedLTOAndSTOInfo
import inu.thebite.tory.screens.education.compose.dev.DEVSelector
import inu.thebite.tory.screens.education.compose.sidebar.Sidebar
import inu.thebite.tory.screens.education.viewmodel.DEVViewModel
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.game.viewmodel.DragAndDropViewModel
import inu.thebite.tory.screens.game.viewmodel.GameViewModel
import inu.thebite.tory.screens.notice.NoticeViewModel
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel
import inu.thebite.tory.screens.teachingboard.viewmodel.ChildSelectViewModel
import inu.thebite.tory.ui.theme.fontFamily_Lato

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NewEducationScreen(
    selectedChild: StudentResponse,
    devViewModel: DEVViewModel,
    ltoViewModel: LTOViewModel,
    stoViewModel: STOViewModel,
    imageViewModel: ImageViewModel,
    gameViewModel: GameViewModel,
    dragAndDropViewModel: DragAndDropViewModel,
    todoViewModel: TodoViewModel,
    noticeViewModel: NoticeViewModel,
    childSelectViewModel: ChildSelectViewModel
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val context = LocalContext.current

    val selectedDEV by devViewModel.selectedDEV.collectAsState()

    val allLTOs by ltoViewModel.allLTOs.collectAsState()
    val ltos by ltoViewModel.ltos.collectAsState()
    val selectedLTO by ltoViewModel.selectedLTO.collectAsState()

    val selectedSTO by stoViewModel.selectedSTO.collectAsState()
    val points by stoViewModel.points.collectAsState()
    val hasFocus by stoViewModel.hasFocus.collectAsState()

    val todoList by todoViewModel.todoResponse.collectAsState()

    val selectedChild by childSelectViewModel.selectedChildInfo.collectAsState()

    LaunchedEffect(Unit) {
//        todoViewModel.getTodoList(studentId = 1L)
        selectedChild?.let { selectedChild ->
            ltoViewModel.getAllLTOs(studentId = selectedChild.id)
            stoViewModel.getAllSTOs(studentId = selectedChild.id)
        }
    }

    LaunchedEffect(selectedDEV) {
        selectedDEV?.let { selectedDEV ->
            selectedChild?.let { selectedChild ->
                ltoViewModel.getLTOsByDomain(
                    studentId = selectedChild.id,
                    domainId = selectedDEV.id
                )
                stoViewModel.clearSelectedSTO()
                ltoViewModel.clearSelectedLTO()
            }
        }
    }
    LaunchedEffect(selectedChild){
        selectedChild?.let { selectedChild ->
            ltoViewModel.getAllLTOs(studentId = selectedChild.id)
            stoViewModel.getAllSTOs(studentId = selectedChild.id)
            selectedDEV?.let {selectedDEV ->
                ltoViewModel.getLTOsByDomain(studentId = selectedChild.id, domainId = selectedDEV.id)
            }
        }
    }

    LaunchedEffect(selectedLTO) {
//        selectedLTO?.let { stoViewModel.setSTOsByLTO(selectedLTO = it) }
    }
    LaunchedEffect(selectedSTO) {
        selectedSTO?.let { stoViewModel.getPointList(selectedSTO = it) }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    // TextField 외부를 탭했을 때만 포커스 해제
                    focusManager.clearFocus()
                    keyboardController?.hide()
                })
            }
    ) {
        Divider(thickness = 1.dp, color = Color.LightGray)
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.4f)
            ) {
                Sidebar(
                    childSelectViewModel = childSelectViewModel,
                    devViewModel = devViewModel,
                    ltoViewModel = ltoViewModel,
                    stoViewModel = stoViewModel
                )
            }
            VerticalDivider(color = Color.LightGray)
            Column(
                modifier = Modifier
                    .weight(1.6f)
                    .fillMaxHeight()
            ) {
                selectedChild?.let {selectedChild ->
                    DEVSelector(
                        modifier = Modifier
                            .weight(0.5f),
                        devViewModel = devViewModel,
                        ltoViewModel = ltoViewModel,
                        selectedChild = selectedChild
                    )
                }
                Divider(thickness = 1.dp, color = Color.LightGray)
                ltos?.let {
                    selectedChild?.let { selectedChild ->
                        LTOAndSTOSelector(
                            modifier = Modifier
                                .weight(9.5f),
                            selectedChild = selectedChild,
                            selectedDEV = selectedDEV,
                            selectedLTO = selectedLTO,
                            selectedSTO = selectedSTO,
                            ltos = it,
                            ltoViewModel = ltoViewModel,
                            stoViewModel = stoViewModel,
                            dragAndDropViewModel = dragAndDropViewModel
                        )
                    }
                } ?: LTOAndSTONull(modifier = Modifier.weight(9.5f).background(Color(0xFFF3F3F3)))
            }
            VerticalDivider()
            Column(
                modifier = Modifier
                    .weight(8f)
                    .fillMaxHeight()
            ) {
                selectedChild?.let {selectedChild ->
                    SelectedLTOAndSTOInfo(
                        allLTOs = allLTOs,
                        selectedChild = selectedChild,
                        selectedLTO = selectedLTO,
                        selectedSTO = selectedSTO,
                        points = points,
                        todoList = todoList,
                        imageViewModel = imageViewModel,
                        stoViewModel = stoViewModel,
                        ltoViewModel = ltoViewModel,
                        todoViewModel = todoViewModel,
                        noticeViewModel = noticeViewModel,
                        dragAndDropViewModel = dragAndDropViewModel,
                        gameViewModel = gameViewModel
                    )
                }

            }
        }
    }
}


fun replaceNewLineWithSpace(input: String): String {
    return input.replace("\n", " ")
}


@Composable
fun LTOAndSTONull(
    modifier: Modifier = Modifier
) {


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "발달영역을 선택해주세요",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = fontFamily_Lato,
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),
            )
        )
    }
}


fun Modifier.clickableWithNoRipple(onClick: () -> Unit): Modifier {
    return this.clickable(
        interactionSource = MutableInteractionSource(),
        indication = null,
        onClick = onClick
    )
}


@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier,
    color: Color = Color.LightGray,
    thickness: Dp = 1.dp
) {
    Box(
        modifier
            .fillMaxHeight()
            .width(thickness)
            .background(color = color)
    )
}
