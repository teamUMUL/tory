package inu.thebite.tory.screens.teachingboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Icon
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
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Montserrat


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
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Teaching Board",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                    )
                )
                Row(
                    modifier = Modifier
                        .weight(4f)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(40.dp)

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
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0047B3)),
                    modifier = Modifier  //lto & sto button
                        .height(60.dp)
                        .weight(1f)
                        .padding(end = 16.dp),
                    shape = RoundedCornerShape(10.dp)

                ) {
                    Text(
                        text = "중재 노트",
                        style = TextStyle(
                            color = Color(0xFFFFFFFF),
                            fontFamily = fontFamily_Inter,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )
                    )
                }
            }
        }
        Column(modifier = Modifier.weight(1.0f)) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
//                    .height(112.dp)
                    .padding(horizontal = 16.dp, vertical = 20.dp),

                ) {
                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .weight(5f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0047B3),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_recent),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "최근 중재 목록",
                            style = TextStyle(
                                fontFamily = fontFamily_Montserrat,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 24.sp,
                                color = Color.White
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .weight(5f)
                        .fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF0047B3)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(width = 2.dp, color = Color(0xFF0047B3))
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_multipages),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "정기검사",
                            style = TextStyle(
                                fontFamily = fontFamily_Montserrat,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 24.sp,
                                color = Color(0xFF0047B3)
                            )
                        )
                    }
                }
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


