package inu.thebite.tory.screens.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.ModifierLocalReadScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.components.Legends
import co.yml.charts.common.model.LegendsConfig
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.barchart.GroupBarChart
import co.yml.charts.ui.barchart.models.BarPlotData
import co.yml.charts.ui.barchart.models.BarStyle
import co.yml.charts.ui.barchart.models.GroupBarChartData
import co.yml.charts.ui.barchart.models.GroupSeparatorConfig
import inu.thebite.tory.R

@Composable
fun chart_bar(
    modifier: Modifier = Modifier
){
        Column(
            modifier= Modifier
                .padding(8.dp)
                .width(500.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier
                .padding(20.dp)
                .height(30.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = " Individual Child Daily",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),
                        letterSpacing = 0.14.sp
                    )
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "January",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        letterSpacing = 0.14.sp,
                    )
                    )
                Spacer(modifier = Modifier.width(30.dp))
                
                Box(modifier = Modifier
                    .padding(top = 5.dp)
                    .width(8.dp)
                    .height(9.45067.dp)
                    .background(
                        color = Color(0xFF9297FF),
                        shape = RoundedCornerShape(size = 10.dp)
                    ))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "23.07",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFFA6ACBE),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.14.sp,
                    )
                )
                Spacer(modifier = Modifier.width(15.dp))
                Box(modifier = Modifier
                    .padding(top = 5.dp)
                    .width(8.dp)
                    .height(9.45067.dp)
                    .background(
                        color = Color(0xFF0047B3),
                        shape = RoundedCornerShape(size = 10.dp)
                    ),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "23.10",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFFA6ACBE),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.14.sp,
                    )
                )

                
            }

            VerticalGroupBarChart()

        }

}
@Composable
fun VerticalGroupBarChart() {
    val maxRange = 20 // Y 축의 Maxsize
    val barSize = 2  //그룹내에 바 개수
    val groupBarData = DataUtils.getGroupBarChartData(10, maxRange, barSize)
    val yStepSize = 10 //y 축 눈금선 개수
    val xAxisData = AxisData.Builder()
        .axisStepSize(20.dp)
        .bottomPadding(5.dp)
        .startDrawPadding(30.dp)
        .labelData { index -> index.toString() }
        .build()
    val yAxisData = AxisData.Builder()
        .steps(yStepSize)
        .labelAndAxisLinePadding(20.dp)
        .axisOffset(20.dp)
        .labelData { index -> (index * (maxRange / yStepSize)).toString() }
        .build()
    val colorPaletteList = getColorPaletteList(barSize)
    val legendsConfig = LegendsConfig(
        legendLabelList = DataUtils.getLegendsLabelData(colorPaletteList),
        gridColumnCount = 3
    )
    val groupBarPlotData = BarPlotData(
        groupBarList = groupBarData,
        barStyle = BarStyle(barWidth = 8.dp),
        barColorPaletteList = colorPaletteList
    )
    val groupBarChartData = GroupBarChartData(
        barPlotData = groupBarPlotData,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        groupSeparatorConfig = GroupSeparatorConfig(0.dp)
    )
    Column(
        Modifier
            .height(450.dp).width(440.dp)
    ) {
        GroupBarChart(
            modifier = Modifier
                .height(400.dp)
                ,
            groupBarChartData = groupBarChartData
        )
        Legends(
            legendsConfig = legendsConfig
        )
    }
}

@Composable
fun getColorPaletteList(listSize: Int): List<Color> {
    val colorList = mutableListOf<Color>(
        Color(0xFF9297FF),
        Color(0xFF0047B3)
    )


    return colorList
}