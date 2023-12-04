package inu.thebite.tory.screens.education2.compose

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
import inu.thebite.tory.schedule.TodoViewModel
import inu.thebite.tory.screens.education2.compose.lto2.SelectedLTORow
import inu.thebite.tory.screens.education2.compose.sto2.SelectedSTOInfo
import inu.thebite.tory.screens.education2.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education2.viewmodel.STOViewModel
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel

@Composable
fun SelectedLTOAndSTOInfo(
    allLTOs: List<LtoResponse>?,
    selectedLTO: LtoResponse?,
    selectedSTO: StoResponse?,
    points: List<String>?,
    todoList: List<StoSummaryResponse>?,
    imageViewModel: ImageViewModel,
    stoViewModel: STOViewModel,
    ltoViewModel: LTOViewModel,
    todoViewModel: TodoViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SelectedLTORow(
            modifier = Modifier.weight(0.5f),
            allLTOs = allLTOs,
            selectedLTO = selectedLTO,
            ltoViewModel = ltoViewModel,
            stoViewModel = stoViewModel
        )
        Divider(thickness = 1.dp, color = Color.LightGray)
        SelectedSTOInfo(
            modifier = Modifier.weight(9.5f),
            selectedSTO = selectedSTO,
            points = points,
            todoList = todoList,
            imageViewModel = imageViewModel,
            stoViewModel = stoViewModel,
            todoViewModel = todoViewModel
        )
    }
}