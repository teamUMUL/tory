package inu.thebite.tory.screens.education.compose.sto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.notice.NoticeViewModel
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel


@Composable
fun SelectedSTODetail(
    modifier: Modifier = Modifier,
    selectedSTO: StoResponse?,
    points: List<String>?,
    imageViewModel: ImageViewModel,
    stoViewModel: STOViewModel,
    noticeViewModel: NoticeViewModel
) {
    Row(
        modifier = modifier
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp, top = 0.dp)
            .fillMaxWidth()
            .background(
                color = selectedSTO?.let { selectedSTO ->
                    when (selectedSTO.status) {
                        "진행중" -> {
                            Color(0xFFC0E9EF).copy(0.5f)
                        }

                        "준거 도달" -> {
                            Color(0xFFCCEFC0).copy(0.5f)

                        }

                        "중지" -> {
                            Color(0xFFEFC0C0).copy(0.5f)

                        }

                        else -> {
                            MaterialTheme.colorScheme.tertiary
                        }
                    }
                } ?: MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(size = 10.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .weight(5f)
                .fillMaxHeight()
                .padding(10.dp)
                .background(color = Color.White, shape = RoundedCornerShape(10.dp))
        ) {
            SelectedSTODetailsTable(
                selectedSTO = selectedSTO
            )
        }
        Column(
            modifier = Modifier
                .weight(5f)
                .fillMaxHeight()
                .padding(10.dp)
                .background(color = Color.White, shape = RoundedCornerShape(10.dp))
        ) {
            SelectedSTOEducationReadyAndResult(
                selectedSTO = selectedSTO,
//                points = points,
                imageViewModel = imageViewModel,
                stoViewModel = stoViewModel,
                noticeViewModel = noticeViewModel
            )
        }
    }
}