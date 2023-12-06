package inu.thebite.tory.screens.education.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.sto.StoSummaryResponse
import inu.thebite.tory.model.todo.TodoResponse
import inu.thebite.tory.schedule.TodoViewModel
import inu.thebite.tory.screens.education.compose.lto.SelectedLTORow
import inu.thebite.tory.screens.education.compose.sto.SelectedSTOInfo
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.notice.NoticeViewModel
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel

@Composable
fun SelectedLTOAndSTOInfo(
    allLTOs: List<LtoResponse>?,
    selectedLTO: LtoResponse?,
    selectedSTO: StoResponse?,
    points: List<String>?,
    todoList: TodoResponse?,
    imageViewModel: ImageViewModel,
    stoViewModel: STOViewModel,
    ltoViewModel: LTOViewModel,
    todoViewModel: TodoViewModel,
    noticeViewModel: NoticeViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SelectedLTORow(
            modifier = Modifier.weight(0.5f),
            allLTOs = allLTOs,
            selectedLTO = selectedLTO,
            selectedSTO = selectedSTO,
            ltoViewModel = ltoViewModel,
            stoViewModel = stoViewModel
        )
        Divider(thickness = 1.dp, color = Color.LightGray)
        SelectedSTOInfo(
            modifier = Modifier.weight(9.5f),
            selectedSTO = selectedSTO,
            selectedLTO = selectedLTO,
            points = points,
            todoList = todoList,
            imageViewModel = imageViewModel,
            stoViewModel = stoViewModel,
            ltoViewModel = ltoViewModel,
            todoViewModel = todoViewModel,
            noticeViewModel = noticeViewModel
        )
    }
}