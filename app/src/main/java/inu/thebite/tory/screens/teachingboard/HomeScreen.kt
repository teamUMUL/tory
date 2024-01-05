package inu.thebite.tory.screens.teachingboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.R
import inu.thebite.tory.screens.education.viewmodel.DEVViewModel
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.teachingboard.recentlto.RecentLTOScreen
import inu.thebite.tory.screens.teachingboard.viewmodel.CenterSelectViewModel
import inu.thebite.tory.screens.teachingboard.viewmodel.ChildClassSelectViewModel
import inu.thebite.tory.screens.teachingboard.viewmodel.ChildSelectViewModel


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    centerSelectViewModel: CenterSelectViewModel,
    childClassSelectViewModel: ChildClassSelectViewModel,
    childSelectViewModel: ChildSelectViewModel,
    devViewModel: DEVViewModel,
    ltoViewModel: LTOViewModel,
    stoViewModel: STOViewModel,
    navigateToEducation: () -> Unit,
    navigateToNotice: () -> Unit,
) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(0xFFF3F3F3))

    ) {
        Column(modifier = Modifier.weight(1f)) {
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
                        .weight(1f)
                        .height(40.dp)
                        .padding(start = 16.dp),
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Start,
                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(4f)
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
                        childSelectViewModel = childSelectViewModel,
                        ltoViewModel = ltoViewModel,
                        stoViewModel = stoViewModel,
                        devViewModel = devViewModel,
                        classSelectViewModel = childClassSelectViewModel
                    ) //아이 선택

                }

                Button(
                    onClick = {
                        navigateToEducation()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7F5AF0)),
                    modifier = Modifier  //lto & sto button
                        .shadow(
                            elevation = 4.dp,
                            spotColor = Color(0x40000000),
                            ambientColor = Color(0x40000000)
                        )
                        .weight(1f)
                        .width(60.dp)
                        .padding(end = 16.dp)
                        .height(60.dp)
                        .background(
                            color = Color(0xFF7F5AF0),
                            shape = RoundedCornerShape(size = 10.dp)
                        )

                ) {
                    Text(
                        text = "LTO & STO & PLAY",
                        style = TextStyle(color = Color(0xFFFFFFFF), background = Color(0xFF7F5AF0))
                    )
                }
            }
        }
        Column(modifier = Modifier.weight(1.3f)) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
//                    .height(112.dp)
                    .padding(start = 16.dp, end = 16.dp),

                ) {
                Image(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .clickable { /* Define the click action here */ },
                    painter = painterResource(id = R.drawable.recent_list_btn),
                    contentDescription = "Recent List Button"
                )
                Image(
                    modifier = Modifier
                        .weight(1f)
                        .size(700.dp)
                        .width(810.dp)
                        .clickable {
                            navigateToNotice()
                        },
                    painter = painterResource(id = R.drawable.report_btn),
                    contentDescription = "Report Button"
                )

            }
        }

        Column(
            modifier = Modifier
                .weight(8f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp)
            ) {
                childInfor(
                    modifier = Modifier.weight(1f),
                    childSelectViewModel = childSelectViewModel
                )

                Row(
                    modifier = modifier
                        .fillMaxHeight()
                        .padding(start = 16.dp, end = 16.dp, bottom = 4.dp)
                        .weight(3f)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(size = 10.dp)
                        )

                ) {
                    HorizonPager(
                        devViewModel = devViewModel,
                        ltoViewModel = ltoViewModel
                    )
//                    pieChartPreview()


                }

            }
        }
        Column(modifier = Modifier.weight(1f)) {
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizonPager(
    modifier: Modifier = Modifier,
    devViewModel: DEVViewModel,
    ltoViewModel: LTOViewModel

    ) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(9.5f),
            state = pagerState
        ) { index ->
            when (index) {
                0 -> {
                    chart_bar(
                        devViewModel = devViewModel,
                        ltoViewModel = ltoViewModel
                    )
                }

                1 -> {
                    RecentLTOScreen()
                }

                else -> {}
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(2) { index ->
                val color =
                    if (pagerState.currentPage == index) Color(0xFF7F5AF0) else Color(0xFF7F5AF0).copy(
                        0.5f
                    )
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(color, CircleShape)
                        .size(10.dp)
                )
            }
        }
    }
}


