package inu.thebite.tory.screens.DataScreen.Compose

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.screens.DataScreen.STODetailViewModel

@Composable
fun STODetailTableAndGameResult(
    selectedDEVIndex: Int,
    selectedLTOIndex: Int,
    selectedSTOIndex: Int,
    selectedSTO: String,
    selectedSTODetail: MutableList<String>,
    selectedSTODetailGameDataIndex: Int,
    stoDetailViewModel: STODetailViewModel,
    setSelectedSTODetailGameDataIndex: (Int) -> Unit,
){
    val STODetailTitles =
        listOf<String>(
            "STO 이름",
            "STO 내용",
            "시도 수",
            "준거도달 기준",
            "촉구방법",
            "강화스케줄",
            "메모",
        )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 0.dp,
                bottom = 0.dp,
                top = 0.dp,
                end = 0.dp
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(500.dp)
                .padding(10.dp)
                .border(4.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(8.dp))
        ) {
            if(selectedSTO != ""){
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    items(STODetailTitles) { stoDetailItem ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(if (STODetailTitles.indexOf(stoDetailItem) != 6) 40.dp else 300.dp)
                        ) {
                            TableCell(text = stoDetailItem, weight = 0.3f)
                            Divider(
                                color = MaterialTheme.colorScheme.tertiary, modifier = Modifier
                                    .fillMaxHeight()
                                    .width(2.dp)
                            )
                            TableCell(
                                text = selectedSTODetail[STODetailTitles.indexOf(stoDetailItem)], weight = 0.7f
                            )

                        }
                        Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.tertiary)
                    }
                }
            }

        }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(start = 0.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
                    .border(4.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(8.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                if(selectedSTO != ""){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.1f)
                            .padding(10.dp)
                            .border(
                                2.dp,
                                MaterialTheme.colorScheme.tertiary,
                                RoundedCornerShape(8.dp)
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text = "정반응 :", modifier = Modifier.padding(horizontal = 5.dp))
                            Log.e("인덱스", selectedDEVIndex.toString()+selectedLTOIndex.toString()+selectedSTOIndex.toString())
                            Text(
                                text = stoDetailViewModel.getSTODetail(
                                    selectedLTOIndex,
                                    selectedDEVIndex,
                                    selectedSTOIndex
                                ).second.count { it == "+" }.toString(),
                                modifier = Modifier.padding(horizontal = 5.dp)
                            )
                            Divider(
                                color = MaterialTheme.colorScheme.tertiary, modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp)
                            )
                            Text(text = "촉구 :", modifier = Modifier.padding(horizontal = 5.dp))
                            Text(
                                text = stoDetailViewModel.getSTODetail(
                                    selectedLTOIndex,
                                    selectedDEVIndex,
                                    selectedSTOIndex
                                ).second.count { it == "P" }.toString(),
                                modifier = Modifier.padding(horizontal = 5.dp)
                            )

                            Divider(
                                color = MaterialTheme.colorScheme.tertiary, modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp)
                            )
                            Text(text = "미반응 :", modifier = Modifier.padding(horizontal = 5.dp))
                            Text(
                                text = stoDetailViewModel.getSTODetail(
                                    selectedLTOIndex,
                                    selectedDEVIndex,
                                    selectedSTOIndex
                                ).second.count { it == "-" }.toString(),
                                modifier = Modifier.padding(horizontal = 5.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "회차 :", modifier = Modifier.padding(horizontal = 5.dp))
                            Text(text = "1", modifier = Modifier.padding(horizontal = 5.dp))
                        }
                    }
                    Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.tertiary)
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.8f)
                            .padding(10.dp)
                    ) {
                        items(15 / 5) { verticalIndex ->
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(2.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                items(5) { horizonIndex ->
                                    val stoGameDataList =
                                        stoDetailViewModel.getSTODetail(
                                            selectedLTOIndex,
                                            selectedDEVIndex,
                                            selectedSTOIndex
                                        ).second
                                    val stoGameData =
                                        stoGameDataList[(5 * verticalIndex) + horizonIndex]
                                    Card(
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .width(85.dp)
                                            .fillMaxHeight(0.3f)
                                            .clickable {
                                                setSelectedSTODetailGameDataIndex(
                                                    (5 * verticalIndex) + horizonIndex
                                                )
                                            },
                                        shape = RoundedCornerShape(16.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = when (stoGameData) {
                                                "+" -> {
                                                    Color.Green.copy(alpha = 0.85f)
                                                }

                                                "-" -> {
                                                    Color.Red.copy(alpha = 0.85f)
                                                }

                                                "P" -> {
                                                    Color.Yellow.copy(alpha = 0.85f)
                                                }

                                                else -> {
                                                    Color.Gray.copy(alpha = 0.85f)
                                                }
                                            }
                                        ),
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(16.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = if (stoGameData != "n") stoGameData else "",
                                                fontSize = 24.sp,
                                                color = Color.White,
                                            )
                                        }
                                    }
                                }
                            }
                        }

                    }
                    Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.tertiary)
                    LazyRow(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val buttonList = listOf<String>("+", "-", "P", "삭제")
                        items(buttonList) { buttonItem ->
                            OutlinedButton(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(vertical = 5.dp, horizontal = 2.dp),
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(
                                    width = 2.dp, color =
                                    when (buttonItem) {
                                        "+" -> {
                                            Color.Green.copy(alpha = 0.95f)
                                        }

                                        "-" -> {
                                            Color.Red.copy(alpha = 0.95f)
                                        }

                                        "P" -> {
                                            Color.Yellow.copy(alpha = 0.95f)
                                        }

                                        else -> {
                                            Color.LightGray.copy(alpha = 0.95f)
                                        }
                                    }
                                ),
                                onClick = {
                                    if(selectedSTODetailGameDataIndex < stoDetailViewModel.getSTODetail(
                                            selectedLTOIndex,
                                            selectedDEVIndex,
                                            selectedSTOIndex
                                        ).second.size)
                                        when (buttonItem) {
                                            "+" -> {
                                                val changeList =
                                                    stoDetailViewModel.getSTODetail(
                                                        selectedLTOIndex,
                                                        selectedDEVIndex,
                                                        selectedSTOIndex
                                                    ).second.toMutableList()
                                                changeList[selectedSTODetailGameDataIndex] = "+"
                                                stoDetailViewModel.addOrUpdateSTODetail(
                                                    selectedLTOIndex,
                                                    selectedDEVIndex,
                                                    selectedSTODetail,
                                                    changeList.toList()
                                                )
                                                setSelectedSTODetailGameDataIndex(selectedSTODetailGameDataIndex+1)
                                            }

                                            "-" -> {
                                                val changeList =
                                                    stoDetailViewModel.getSTODetail(
                                                        selectedLTOIndex,
                                                        selectedDEVIndex,
                                                        selectedSTOIndex
                                                    ).second.toMutableList()
                                                changeList[selectedSTODetailGameDataIndex] = "-"
                                                stoDetailViewModel.addOrUpdateSTODetail(
                                                    selectedLTOIndex,
                                                    selectedDEVIndex,
                                                    selectedSTODetail,
                                                    changeList.toList()
                                                )
                                                setSelectedSTODetailGameDataIndex(selectedSTODetailGameDataIndex+1)
                                            }

                                            "P" -> {
                                                val changeList =
                                                    stoDetailViewModel.getSTODetail(
                                                        selectedLTOIndex,
                                                        selectedDEVIndex,
                                                        selectedSTOIndex
                                                    ).second.toMutableList()
                                                changeList[selectedSTODetailGameDataIndex] = "P"
                                                stoDetailViewModel.addOrUpdateSTODetail(
                                                    selectedLTOIndex,
                                                    selectedDEVIndex,
                                                    selectedSTODetail,
                                                    changeList.toList()
                                                )
                                                setSelectedSTODetailGameDataIndex(selectedSTODetailGameDataIndex+1)

                                            }

                                            else -> {
                                                val changeList =
                                                    stoDetailViewModel.getSTODetail(
                                                        selectedLTOIndex,
                                                        selectedDEVIndex,
                                                        selectedSTOIndex
                                                    ).second.toMutableList()
                                                changeList[selectedSTODetailGameDataIndex] = "n"
                                                stoDetailViewModel.addOrUpdateSTODetail(
                                                    selectedLTOIndex,
                                                    selectedDEVIndex,
                                                    selectedSTODetail,
                                                    changeList.toList()
                                                )
                                                setSelectedSTODetailGameDataIndex(selectedSTODetailGameDataIndex+1)

                                            }
                                        }
                                }
                            ) {
                                Text(
                                    modifier = Modifier.padding(16.dp),
                                    text = when (buttonItem) {
                                        "+" -> {
                                            "+"
                                        }

                                        "-" -> {
                                            "-"
                                        }

                                        "P" -> {
                                            "P"
                                        }

                                        else -> {
                                            "삭제"
                                        }
                                    },
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.SemiBold,

                                    )
                            }
                        }

                    }
                }


        }


    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .weight(weight)
            .padding(8.dp)
    )

}