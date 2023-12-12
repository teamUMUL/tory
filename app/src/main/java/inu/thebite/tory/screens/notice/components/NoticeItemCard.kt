package inu.thebite.tory.screens.notice.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import inu.thebite.tory.model.detail.DetailGraphResponse
import inu.thebite.tory.model.domain.DomainResponse
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.notice.DateResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.notice.NoticeViewModel

@Composable
fun LTOItem(
    lto: LtoResponse,
    selectedChild: StudentResponse,
    selectedDate: DateResponse,
    selectedNoticeDetailList: List<DetailGraphResponse>,
    noticeViewModel: NoticeViewModel,
    stoViewModel: STOViewModel
) {
    val context = LocalContext.current
//    val dummySTOList = mutableListOf<StoResponse>().toMutableStateList()
//    for (i in 1..8) {
//        dummySTOList.add(
//            StoResponse(
//                id = i.toLong(),
//                templateNum = i,
//                status = "진행중",
//                name = "같은 사진 매칭(${i} array)ssssssssasasasasss",
//                contents = "",
//                count = i,
//                goal = i,
//                goalPercent = i,
//                achievementOrNot = "",
//                urgeType = "",
//                urgeContent = "",
//                enforceContent = "",
//                memo = "",
//                hitGoalDate = "",
//                registerDate = "",
//                delYN = "",
//                round = i,
//                imageList = listOf(),
//                pointList = listOf(),
//                lto = LtoResponse(
//                    id = i.toLong(),
//                    templateNum = i,
//                    status = "",
//                    name = "더미용 LTO ${i}",
//                    contents = "",
//                    game = "",
//                    achieveDate = "",
//                    registerDate = "",
//                    delYN = "",
//                    domain = DomainResponse(
//                        id = i.toLong(),
//                        templateNum = i,
//                        type = "",
//                        status = "",
//                        name = "더미용 Domain ${i}",
//                        contents = "",
//                        useYN = "",
//                        delYN = "",
//                        registerDate = ""
//                    )
//                )
//            )
//        )
//    }

    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF0047B3), Color(0xFF7F5AF0))
    )
    val expandedState = rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(horizontal = 25.dp, vertical = 8.dp)
            .shadow(
                elevation = 16.dp,
                spotColor = Color(0x40000000),
                ambientColor = Color(0x40000000),
                clip = false
            )
            .fillMaxWidth()
            .background(color = Color(0xFFE7EBF0), shape = RoundedCornerShape(size = 10.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFE7EBF0), shape = RoundedCornerShape(size = 10.dp))
        ) {
            NoticeItemTopBar(lto = lto, gradient = gradient, expandedState = expandedState)
            NoticeItemTextField(
                selectedDate = selectedDate,
                selectedChild = selectedChild,
                selectedDetail = selectedNoticeDetailList.first { it.ltoId == lto.id },
                noticeViewModel = noticeViewModel
            )
        }
        AnimatedVisibility(visible = expandedState.value) {
            NoticeItemGraphRow(
                stoList = selectedNoticeDetailList.filter { it.ltoId == lto.id },
                stoViewModel = stoViewModel
            )
        }

    }

}