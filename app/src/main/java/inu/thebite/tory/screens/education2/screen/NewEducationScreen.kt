package inu.thebite.tory.screens.education2.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.schedule.TodoViewModel
import inu.thebite.tory.screens.education2.compose.LTOAndSTOSelector
import inu.thebite.tory.screens.education2.compose.SelectedLTOAndSTOInfo
import inu.thebite.tory.screens.education2.compose.dev.DEVSelector
import inu.thebite.tory.screens.education2.viewmodel.DEVViewModel
import inu.thebite.tory.screens.education2.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education2.viewmodel.STOViewModel
import inu.thebite.tory.screens.game.viewmodel.DragAndDropViewModel
import inu.thebite.tory.screens.game.viewmodel.GameViewModel
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel
import inu.thebite.tory.ui.theme.fontFamily_Lato

@Composable
fun NewEducationScreen(
    devViewModel: DEVViewModel,
    ltoViewModel: LTOViewModel,
    stoViewModel: STOViewModel,
    imageViewModel: ImageViewModel,
    gameViewModel: GameViewModel,
    dragAndDropViewModel: DragAndDropViewModel,
    todoViewModel: TodoViewModel
) {
    val context = LocalContext.current

    val selectedDEV by devViewModel.selectedDEV.collectAsState()

    val allLTOs by ltoViewModel.allLTOs.collectAsState()
    val ltos by ltoViewModel.ltos.collectAsState()
    val selectedLTO by ltoViewModel.selectedLTO.collectAsState()

    val selectedSTO by stoViewModel.selectedSTO.collectAsState()
    val points by stoViewModel.points.collectAsState()

    val todoList by todoViewModel.todoList.collectAsState()

    LaunchedEffect(selectedDEV) {
        selectedDEV?.let { ltoViewModel.getLTOsByDEV(selectedDEV = it) }
    }

    LaunchedEffect(selectedLTO) {
        selectedLTO?.let { stoViewModel.getSTOsByLTO(selectedLTO = it) }
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
        ) {
            DEVSelector(
                modifier = Modifier
                    .weight(0.5f),
                devViewModel = devViewModel,
                ltoViewModel = ltoViewModel
            )
            Divider(thickness = 1.dp, color = Color.LightGray)
            ltos?.let {
                LTOAndSTOSelector(
                    modifier = Modifier
                        .weight(9.5f),
                    selectedDEV = selectedDEV,
                    selectedSTO = selectedSTO,
                    ltos = it,
                    ltoViewModel = ltoViewModel,
                    stoViewModel = stoViewModel
                )
            } ?: LTOAndSTONull(modifier = Modifier.weight(9.5f))
        }
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp), color = Color.LightGray
        )
        Column(
            modifier = Modifier
                .weight(8f)
                .fillMaxHeight()
        ) {
            SelectedLTOAndSTOInfo(
                allLTOs = allLTOs,
                selectedSTO = selectedSTO,
                points = points,
                todoList = todoList,
                imageViewModel = imageViewModel,
                stoViewModel = stoViewModel,
                ltoViewModel = ltoViewModel,
                todoViewModel = todoViewModel
            )

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
            text = "데이터 없음",
            style = TextStyle(
                fontSize = 35.sp,
                lineHeight = 22.sp,
                fontFamily = fontFamily_Lato,
                fontWeight = FontWeight(800),
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
