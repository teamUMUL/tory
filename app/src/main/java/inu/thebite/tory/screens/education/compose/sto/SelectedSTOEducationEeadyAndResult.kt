package inu.thebite.tory.screens.education.compose.sto

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.notice.NoticeViewModel
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Lato


@Composable
fun SelectedSTOEducationReadyAndResult(
    selectedChild: StudentResponse,
    selectedLTO: LtoResponse?,
    selectedSTO: StoResponse?,
//    points: List<String>?,
    imageViewModel: ImageViewModel,
    stoViewModel: STOViewModel,
    ltoViewModel: LTOViewModel,
    noticeViewModel: NoticeViewModel,
) {
    val points by stoViewModel.points.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        selectedSTO?.let { selectedSTO ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 20.dp, top = 30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "교육결과",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = fontFamily_Lato,
                        fontWeight = FontWeight(900),
                        color = Color(0xFF1D1C1D),

                        )
                )
                Row(
                    modifier = Modifier
                        .padding(end = 40.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,

                    ) {
                    val buttonList = listOf(
                        "완료",
                        "진행중",
                        "중지"
                    )
                    buttonList.forEach { button ->
                        OutlinedButton(
                            modifier = Modifier
                                .padding(horizontal = 2.dp),
                            onClick = {
                                stoViewModel.setSelectedSTOStatus(
                                    selectedSTO = selectedSTO,
                                    changeState = button
                                )
                            },
                            border = BorderStroke(
                                width = 1.dp,
                                color = when (button) {
                                    "완료" -> Color(0xFF34C648)
                                    "진행중" -> Color(0xFF40B9FC)
                                    "중지" -> Color(0xFFFC605C)
                                    else -> Color.Black
                                }
                            ),
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = when (button) {
                                    "완료" -> if (selectedSTO.status == button) Color(0xFF34C648) else Color.Transparent
                                    "진행중" -> if (selectedSTO.status == button) Color(0xFF40B9FC) else Color.Transparent
                                    "중지" -> if (selectedSTO.status == button) Color(0xFFFC605C) else Color.Transparent
                                    else -> if (selectedSTO.status == button) Color.White else Color.White
                                },
                                contentColor = when (button) {
                                    "완료" -> if (selectedSTO.status == button) Color.White else Color(
                                        0xFF34C648
                                    )

                                    "진행중" -> if (selectedSTO.status == button) Color.White else Color(
                                        0xFF40B9FC
                                    )

                                    "중지" -> if (selectedSTO.status == button) Color.White else Color(
                                        0xFFFC605C
                                    )

                                    else -> if (selectedSTO.status == button) Color.White else Color.Black
                                }
                            ),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = button,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontFamily = fontFamily_Lato,
                                    fontWeight = FontWeight(500),
                                    textAlign = TextAlign.Center,
                                )
                            )
                        }
                    }
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.75f)
                    .padding(horizontal = 30.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = "정반응 :  ${points?.count { it == "+" } ?: "0"}",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = fontFamily_Inter,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF606060),

                            )
                    )
                    Text(
                        text = "촉구 :  ${points?.count { it == "P" } ?: "0"}",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = fontFamily_Inter,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF606060),

                            )
                    )
                    Text(
                        text = "미반응 :  ${points?.count { it == "-" } ?: "0"}",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = fontFamily_Inter,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF606060),

                            )
                    )
                }
                Text(
                    text = "회차 :  ${selectedSTO?.round ?: "0"}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF606060),

                        )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5.5f),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                points?.let {points ->
                    EducationResultTable(
                        selectedChild = selectedChild,
                        selectedSTO = selectedSTO,
                        points = points,
                        stoViewModel = stoViewModel,
                        noticeViewModel = noticeViewModel
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2.75f)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "도전 행동 스톱워치",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = fontFamily_Lato,
                            color = Color.Black
                        )
                    )
                    Text(
                        text = "개발 예정",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = fontFamily_Lato,
                            color = Color.Black
                        ),
                        modifier = Modifier
                            .border(width = 1.dp, color = Color(0xFF0047B3), shape = RoundedCornerShape(20.dp))
                            .padding(vertical = 3.dp, horizontal = 10.dp)
                    )
                }
                AccidentRecorder(

                )
            }
        }
    }
}
