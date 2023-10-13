package inu.thebite.tory.screens.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import inu.thebite.tory.R
import inu.thebite.tory.screens.DataScreen.LTOViewModel
import inu.thebite.tory.screens.HomeViewModel

//import inu.thebite.tory.screens.DataScreen.STOViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
){
    val ltoViewModel : LTOViewModel = viewModel()
//    val stoViewModel : STOViewModel = viewModel()

    val (dialogOpen, setDialogOpen) = remember {
        mutableStateOf(false)
    }
    var isDialogVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFFF1F1F1))
        , verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(44.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ChainCard()
            ClassCard()
            ChildrenCard()

        }
        Box(
            modifier = Modifier
                .border(
                    width = 3.dp,
                    color = Color(0x33000000),
                    shape = RoundedCornerShape(size = 20.dp)
                )
                .width(1000.dp)
                .height(708.dp)
                .background(color = Color(0xFFF4F4F4), shape = RoundedCornerShape(size = 20.dp))
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)



        ) {
            Row (modifier = Modifier
                .padding(16.dp),
                horizontalArrangement = Arrangement.Center){
                childInfor()
                editProgram()
                reportList()
//                Column (modifier = Modifier
//                    .padding(start = 12.dp, top = 16.dp)
//                    .width(132.dp)
//                    .height(405.dp)
//
//                ){
//                    Box(modifier = Modifier
//                        .width(131.dp)
//                        .height(124.dp)
//                        .padding(8.dp)
//                        .background(color = Color(0xFF13A694), shape = RoundedCornerShape(size = 20.dp))
//                        ){
//                        Text(modifier = Modifier
//                            .width(67.dp)
//                            .height(22.dp)
//                            .align(Alignment.Center),
//                            text = "상담일지",
//                            style = TextStyle(
//                                fontSize = 18.sp,
//                                fontWeight = FontWeight(400),
//                                color = Color(0xFFFFFFFF),
//
//                                )
//                        )
//                    }
//                    Box(modifier = Modifier
//                        .width(131.dp)
//                        .height(124.dp)
//                        .padding(8.dp)
//                        .background(color = Color(0xFF13A694), shape = RoundedCornerShape(size = 20.dp))
//                    ){
//                        Text(modifier = Modifier
//                            .width(67.dp)
//                            .height(22.dp)
//                            .align(Alignment.Center),
//                            text = "아동영상",
//                            style = TextStyle(
//                                fontSize = 18.sp,
//                                fontWeight = FontWeight(400),
//                                color = Color(0xFFFFFFFF),
//
//                                )
//                        )
//                    }
//                    Box(modifier = Modifier
//                        .width(131.dp)
//                        .height(124.dp)
//                        .padding(8.dp)
//                        .background(color = Color(0xFF636363), shape = RoundedCornerShape(size = 20.dp))
//                    ){
//                        Text(modifier = Modifier
//                            .width(50.dp)
//                            .height(22.dp)
//                            .align(Alignment.Center),
//                            text = "보고서",
//                            style = TextStyle(
//                                fontSize = 18.sp,
//                                fontWeight = FontWeight(400),
//                                color = Color(0xFFFFFFFF),
//
//                                )
//                        )
//                    }
//
//                }
//                Column (modifier = Modifier
//                    .padding(start = 12.dp, top = 16.dp)
//                    .width(132.dp)
//                    .height(405.dp)
//
//                ){
//                    Box(modifier = Modifier
//                        .width(131.dp)
//                        .height(124.dp)
//                        .padding(8.dp)
//                        .background(color = Color(0xFF00CD6B), shape = RoundedCornerShape(size = 20.dp))
//                    ){
//                        Text(modifier = Modifier
//                            .width(67.dp)
//                            .height(22.dp)
//                            .align(Alignment.Center),
//                            text = "완료목록",
//                            style = TextStyle(
//                                fontSize = 18.sp,
//                                fontWeight = FontWeight(400),
//                                color = Color(0xFFFFFFFF),
//
//                                )
//                        )
//                    }
//                    Box(modifier = Modifier
//                        .width(131.dp)
//                        .height(124.dp)
//                        .padding(8.dp)
//                        .background(color = Color(0xFF636363), shape = RoundedCornerShape(size = 20.dp))
//                    ){
//                        Text(modifier = Modifier
//                            .width(50.dp)
//                            .height(22.dp)
//                            .align(Alignment.Center),
//                            text = "그래프",
//                            style = TextStyle(
//                                fontSize = 18.sp,
//                                fontWeight = FontWeight(400),
//                                color = Color(0xFFFFFFFF),
//
//                                )
//                        )
//                    }
//                    Box(modifier = Modifier
//                        .width(131.dp)
//                        .height(124.dp)
//                        .padding(8.dp)
//                        .background(color = Color(0xFF636363), shape = RoundedCornerShape(size = 20.dp))
//                    ){
//                        Text(modifier = Modifier
//                            .width(83.dp)
//                            .height(44.dp)
//                            .align(Alignment.Center),
//                            text = "크리테리아\n그래프",
//                            style = TextStyle(
//                                fontSize = 18.sp,
//                                fontWeight = FontWeight(400),
//                                color = Color(0xFFFFFFFF),
//                                textAlign = TextAlign.Center
//
//                                )
//                        )
//                    }
//
//                }
            }
        }
    }

}


