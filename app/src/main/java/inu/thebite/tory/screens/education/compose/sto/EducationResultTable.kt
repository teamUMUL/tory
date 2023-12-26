package inu.thebite.tory.screens.education.compose.sto

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.R
import inu.thebite.tory.model.point.AddPointRequest
import inu.thebite.tory.model.point.DeletePointRequest
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.screens.education.screen.clickableWithNoRipple
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.notice.NoticeViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Lato


@Composable
fun EducationResultTable(
    modifier: Modifier = Modifier,
    selectedChild: StudentResponse,
    selectedSTO: StoResponse?,
    stoViewModel: STOViewModel,
    noticeViewModel: NoticeViewModel,
    points: List<String>
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        selectedSTO?.let { selectedSTO ->
            LazyColumn(
                modifier = Modifier
                    .weight(7f)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                items(selectedSTO.count / 5) { verticalIndex ->
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        items(5) { horizonIndex ->
                            val stoGameData = points?.let { points ->
                                if (points.size > (5 * verticalIndex) + horizonIndex) {
                                    points[(5 * verticalIndex) + horizonIndex]
                                } else {
                                    "n"
                                }
                            } ?: "n"

                            Card(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .shadow(
                                        elevation = 4.dp,
                                        spotColor = Color(0x40000000),
                                        ambientColor = Color(0x40000000),
                                        clip = false
                                    )
                                    .width(80.dp)
                                    .height(40.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor =
                                    when (stoGameData) {
                                        "+" -> {
                                            Color(0xFF34C648)
                                        }

                                        "-" -> {
                                            Color(0xFFFC605C)
                                        }

                                        "P" -> {
                                            Color(0xFFFCBB40)
                                        }

                                        else -> {
                                            Color(0xFFD9D9D9)
                                        }
                                    }

                                ),
                            ) {
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .background(Color(0xFFD1D1D1)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(30.dp)

            ) {
                val pointButtons = listOf("+", "-", "P", "삭제")
                Spacer(modifier = Modifier.width(1.dp))
                pointButtons.forEach { button ->
                    Button(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .weight(1f)
                            .fillMaxHeight(),
                        onClick = {
                            if (points.size < selectedSTO.count) {
                                when (button) {
                                    "+" -> {
                                        stoViewModel.addPoint(
                                            selectedSTO = selectedSTO,
                                            addPointRequest = AddPointRequest(
                                                result = "+",
                                                registrant = "Test"
                                            )
                                        )
                                    }

                                    "-" -> {
                                        stoViewModel.addPoint(
                                            selectedSTO = selectedSTO,
                                            addPointRequest = AddPointRequest(
                                                result = "-",
                                                registrant = "Test"
                                            )
                                        )
                                    }

                                    "P" -> {
                                        stoViewModel.addPoint(
                                            selectedSTO = selectedSTO,
                                            addPointRequest = AddPointRequest(
                                                result = "P",
                                                registrant = "Test"
                                            )
                                        )
                                    }

                                    "삭제" -> {
                                        stoViewModel.deletePoint(
                                            selectedSTO = selectedSTO,
                                            deletePointRequest = DeletePointRequest(
                                                registrant = "Test"
                                            )
                                        )
                                    }
                                }
                            } else {
                                if (button == "삭제"){
                                    stoViewModel.deletePoint(
                                        selectedSTO = selectedSTO,
                                        deletePointRequest = DeletePointRequest(
                                            registrant = "Test"
                                        )
                                    )
                                }
                            }

                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when (button) {
                                "+" -> {
                                    Color(0xFF34C648)
                                }

                                "-" -> {
                                    Color(0xFFFC605C)
                                }

                                "P" -> {
                                    Color(0xFFFCBB40)
                                }

                                else -> {
                                    Color(0xFF777777)
                                }
                            }
                        ),
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        when (button) {
                            "삭제" -> {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_delete),
                                    contentDescription = null,
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            else -> {
                                Text(
                                    text = button,
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontFamily = fontFamily_Inter,
                                        fontWeight = FontWeight(600),
                                        color = Color(0xFFFFFFFF),

                                        )
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.width(1.dp))
            }
            Row(
                modifier = Modifier
                    .weight(1f)
            ) {
                if (points.size == selectedSTO.count) {
                    OutlinedButton(
                        onClick = {
                            val status = if ((points.count { it == "+" }
                                    .toFloat() / selectedSTO.count.toFloat()) * 100 >= 90f) {
                                "준거 도달"
                            } else {
                                "진행중"
                            }
                            val plusRate = (points.count { it == "+" }
                                .toFloat() / selectedSTO.count.toFloat()) * 100
                            val minusRate = (points.count { it == "-" }
                                .toFloat() / selectedSTO.count.toFloat()) * 100
                            stoViewModel.addRound(
                                selectedSTO = selectedSTO,
                                registrant = "테스트",
                                plusRate = plusRate,
                                minusRate = minusRate,
                                status = status
                            )
                            noticeViewModel.addDetail(
                                studentId = selectedChild.id,
                                stoId = selectedSTO.id,
                                year = getCurrentYear(),
                                month = getCurrentMonth(),
                                date = getCurrentDate()
                            )
                        },
                        contentPadding = PaddingValues(
                            horizontal = 60.dp,
                            vertical = 3.dp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(width = 1.dp, color = Color(0xFFCECECE))
                    ) {
                        Text(
                            text = "회차 추가",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = fontFamily_Lato,
                                fontWeight = FontWeight(900),
                                color = Color(0xFF000000),
                            )
                        )
                    }
                }


            }

        }


    }

}