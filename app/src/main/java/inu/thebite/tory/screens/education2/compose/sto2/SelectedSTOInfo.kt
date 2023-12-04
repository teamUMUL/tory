package inu.thebite.tory.screens.education2.compose.sto2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.sto.StoSummaryResponse
import inu.thebite.tory.schedule.TodoViewModel
import inu.thebite.tory.screens.education2.viewmodel.STOViewModel
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel


@Composable
fun SelectedSTOInfo(
    modifier: Modifier = Modifier,
    selectedSTO: StoResponse?,
    points: List<String>?,
    todoList: List<StoSummaryResponse>?,
    imageViewModel: ImageViewModel,
    stoViewModel: STOViewModel,
    todoViewModel: TodoViewModel
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
            todoViewModel = todoViewModel
        )
        SelectedSTODetail(
            modifier = Modifier.weight(9.25f),
            selectedSTO = selectedSTO,
            points = points,
            imageViewModel = imageViewModel,
            stoViewModel = stoViewModel
        )
    }
}