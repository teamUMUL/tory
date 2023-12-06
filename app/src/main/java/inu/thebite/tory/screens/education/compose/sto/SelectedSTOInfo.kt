package inu.thebite.tory.screens.education.compose.sto

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.sto.StoSummaryResponse
import inu.thebite.tory.model.todo.TodoResponse
import inu.thebite.tory.schedule.TodoViewModel
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.notice.NoticeViewModel
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel


@Composable
fun SelectedSTOInfo(
    modifier: Modifier = Modifier,
    selectedSTO: StoResponse?,
    selectedLTO: LtoResponse?,
    points: List<String>?,
    todoList: TodoResponse?,
    imageViewModel: ImageViewModel,
    stoViewModel: STOViewModel,
    ltoViewModel: LTOViewModel,
    todoViewModel: TodoViewModel,
    noticeViewModel: NoticeViewModel
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        SelectedSTORow(
            modifier = Modifier.weight(0.7f),
            selectedSTO = selectedSTO,
            stoViewModel = stoViewModel,
            todoList = todoList,
            todoViewModel = todoViewModel,
            noticeViewModel = noticeViewModel
        )
        SelectedSTODetail(
            modifier = Modifier.weight(9.25f),
            selectedSTO = selectedSTO,
            selectedLTO = selectedLTO,
            points = points,
            imageViewModel = imageViewModel,
            stoViewModel = stoViewModel,
            ltoViewModel = ltoViewModel,
            noticeViewModel = noticeViewModel
        )
    }
}