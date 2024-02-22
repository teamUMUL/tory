package inu.thebite.tory.screens.notice.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.Point
import es.dmoral.toasty.Toasty
import inu.thebite.tory.model.detail.DetailGraphResponse
import inu.thebite.tory.model.detail.DetailObjectResponse
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.sto.StoResponse
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter

@Composable
fun NoticeItemGraphRow(
    stoList: List<DetailObjectResponse>,
    stoViewModel: STOViewModel,
) {
    val context = LocalContext.current
    val dummyReportSTOList = mutableListOf<DetailObjectResponse>().toMutableStateList()
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(550.dp)
            .background(Color(0xFFE7EBF0))
    ) {
        items(stoList) { sto ->
            val plusList = mutableListOf<Float>()
            val minusList = mutableListOf<Float>()
            sto.detailGraphResponse.results.forEachIndexed { index, data ->
                if (index % 2 == 0) {
                    // If the index is even, add it to plusList
                    plusList.add(data)
                } else {
                    // If the index is odd, add it to minusList
                    minusList.add(data)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp)
                    .padding(start = 15.dp, end = 15.dp, bottom = 30.dp, top = 10.dp)
                    .border(
                        width = 2.dp,
                        color = if (dummyReportSTOList.contains(sto)) {
                            Color(0xFF0047B3)
                        } else {
                            Color.Transparent
                        },
                        shape = RoundedCornerShape(10.dp)
                    )
                    .shadow(
                        elevation = 16.dp,
                        spotColor = Color(0x40000000),
                        ambientColor = Color(0x40000000),
                        clip = false
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(size = 10.dp)
                    )
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        if (dummyReportSTOList.contains(sto)) {
                            dummyReportSTOList.remove(sto)
                        } else {
                            if (dummyReportSTOList.size < 4) {
                                dummyReportSTOList.add(sto)
                            } else {
                                Toasty
                                    .warning(
                                        context,
                                        "하나의 LTO 당 4개의 STO를 알림장에 넣을 수 있습니다.",
                                        Toast.LENGTH_SHORT,
                                        true
                                    )
                                    .show()

                            }
                        }
                        Log.d("dummy", dummyReportSTOList.toString())

                    }
            ) {
                Text(
                    text = stoViewModel.findSTOById(sto.detailGraphResponse.stoId)?.name ?: "",
                    style = TextStyle(
                        fontSize = 24.sp,
                        lineHeight = 15.sp,
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.padding(
                        start = 10.dp,
                        top = 20.dp,
                        bottom = 20.dp,
                        end = 10.dp
                    )
                )
                NoticeItemGraph(
                    plusList = plusList,
                    minusList = minusList,
                    dateList = sto.detailGraphResponse.dates,
                    successData = stoViewModel.findSTOById(sto.detailGraphResponse.stoId)?.goal?.toFloat() ?: 90f,
                    goalType = stoViewModel.findSTOById(sto.detailGraphResponse.stoId)?.goalType ?: "연속"
                )
            }
        }

    }
}

