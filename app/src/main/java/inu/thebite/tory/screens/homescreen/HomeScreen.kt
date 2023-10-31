package inu.thebite.tory.screens.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.screens.homescreen.viewmodel.CenterSelectViewModel
import inu.thebite.tory.screens.homescreen.viewmodel.ChildClassSelectViewModel
import inu.thebite.tory.screens.homescreen.viewmodel.ChildSelectViewModel


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    centerSelectViewModel: CenterSelectViewModel,
    childClassSelectViewModel: ChildClassSelectViewModel,
    childSelectViewModel: ChildSelectViewModel
){
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(color = Color(0xFFEFEFEF))

    ) {
        Column(modifier=Modifier.weight(1f)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Text(
                    text = "Dash board",
                    modifier = Modifier
                        .width(240.dp)
                        .height(40.dp)
                        .padding(start = 16.dp),
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Start,
                    )
                )
                //            Spacer(modifier = Modifier.width(590.dp))

                Button(
                    onClick = { /* Define the click action here */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0047B3)),
                    modifier = Modifier  //lto & sto button
                        .shadow(
                            elevation = 4.dp,
                            spotColor = Color(0x40000000),
                            ambientColor = Color(0x40000000)
                        )
                        .width(155.dp)
                        .padding(end = 16.dp)
                        .height(40.dp)
                        .background(
                            color = Color(0xFF0047B3),
                            shape = RoundedCornerShape(size = 10.dp)
                        )

                ) {
                    Text(
                        text = "LTO & STO",
                        style = TextStyle(color = Color(0xFFFFFFFF), background = Color(0xFF0047B3))
                    )
                }
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
//                    .height(112.dp)
                    .padding(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)

            ) {

                ChainCard(
                    modifier = Modifier.weight(1f),
                    centerSelectViewModel = centerSelectViewModel,
                    childClassSelectViewModel = childClassSelectViewModel,
                    childSelectViewModel = childSelectViewModel
                )// 지점 선택
                ClassCard(
                    modifier = Modifier.weight(1f),
                    childClassSelectViewModel = childClassSelectViewModel,
                    childSelectViewModel = childSelectViewModel
                ) //반 선택
                ChildrenCard(
                    modifier = Modifier.weight(1f),
                    childSelectViewModel = childSelectViewModel
                ) //아이 선택

            }
        }

        Column(modifier = Modifier
            .weight(8f)
            .padding(top = 10.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(446.dp)
                    .padding(start = 12.dp)
            ) {
                childInfor(modifier = Modifier.weight(1f))

                Row (modifier = modifier
                    .shadow(
                        elevation = 4.dp,
                        spotColor = Color(0x40000000),
                        ambientColor = Color(0x40000000)
                    )
                    .fillMaxHeight()
                    .padding(start = 8.dp, end = 16.dp)
                    .weight(3f)
                    .fillMaxHeight()
                    .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp))

                ) {

//                    pieChartPreview()
                    chart_bar()


                }

            }
        }
        Column(modifier= Modifier.weight(1f)) {
            reportList()
        }
//            Row (modifier = Modifier
//                .padding(16.dp),
//                horizontalArrangement = Arrangement.Center){
//                childInfor()
//                editProgram()
//                reportList()
//
//            }

    }

}

