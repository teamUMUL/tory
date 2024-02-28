package inu.thebite.tory.screens.education.compose.sto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.model.todo.TodoResponse
import inu.thebite.tory.todo.TodoViewModel
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.notice.NoticeViewModel
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel


@Composable
fun SelectedSTOInfo(
    modifier: Modifier = Modifier,
    selectedChild: StudentResponse,
    selectedSTO: StoResponse?,
    selectedLTO: LtoResponse?,
    points: List<String>?,
    todoList: TodoResponse?,
    imageViewModel: ImageViewModel,
    stoViewModel: STOViewModel,
    ltoViewModel: LTOViewModel,
    todoViewModel: TodoViewModel,
    noticeViewModel: NoticeViewModel,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White),
    ) {
        SelectedSTORow(
            modifier = Modifier.weight(0.7f),
            selectedSTO = selectedSTO,
            selectedChild = selectedChild,
            stoViewModel = stoViewModel,
            selectedLTO = selectedLTO,
            todoList = todoList,
            todoViewModel = todoViewModel,
            noticeViewModel = noticeViewModel,
            imageViewModel = imageViewModel
        )
        SelectedSTODetail(
            modifier = Modifier.weight(9.25f),
            selectedChild = selectedChild,
            selectedSTO = selectedSTO,
            selectedLTO = selectedLTO,
            points = points,
            imageViewModel = imageViewModel,
            stoViewModel = stoViewModel,
            ltoViewModel = ltoViewModel,
            noticeViewModel = noticeViewModel,
        )
    }
}