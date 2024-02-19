package inu.thebite.tory.screens.centerdashboardscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.extensions.isNotNull
import inu.thebite.tory.R
import inu.thebite.tory.screens.centerdashboardscreen.dialog.EditProfileDialog
import inu.thebite.tory.screens.education.compose.dialog.lto.AddLTODialog
import inu.thebite.tory.screens.setting.viewmodel.CenterViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildClassViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildInfoViewModel
import inu.thebite.tory.screens.teachingboard.ChainCard
import inu.thebite.tory.screens.teachingboard.ChildrenCard
import inu.thebite.tory.screens.teachingboard.ClassCard
import inu.thebite.tory.screens.teachingboard.TeacherInfor
import inu.thebite.tory.screens.teachingboard.dialog.CenterDialog
import inu.thebite.tory.screens.teachingboard.dialog.ChildDialog
import inu.thebite.tory.screens.teachingboard.dialog.ClassDialog
import inu.thebite.tory.screens.teachingboard.viewmodel.CenterSelectViewModel
import inu.thebite.tory.screens.teachingboard.viewmodel.ChildClassSelectViewModel
import inu.thebite.tory.screens.teachingboard.viewmodel.ChildSelectViewModel
import inu.thebite.tory.ui.theme.fontFamily_Inter

@Composable
fun CenterDashboardScreen(
    modifier: Modifier = Modifier,
    centerViewModel: CenterViewModel,
    childClassViewModel: ChildClassViewModel,
    childInfoViewModel: ChildInfoViewModel,
    navigateToTeachingBoard : () -> Unit
){
    val allCenters by centerViewModel.allCenters.collectAsState()
    val allChildClasses by childClassViewModel.allChildClasses.collectAsState()
    val allChildInfos by childInfoViewModel.allChildInfos.collectAsState()

    val (editProfileDialog, setEditProfileDialog) = rememberSaveable {
        mutableStateOf(false)
    }

    if (editProfileDialog){
        EditProfileDialog(setEditProfileDialog = {setEditProfileDialog(it)})

    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(color = Color(0xFFF3F3F3))

    ) {
        Column(modifier= Modifier.weight(1f)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    text = "센터 보드",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 30.dp),
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Start,
                    )
                )


                Button(
                    onClick = { navigateToTeachingBoard() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0047B3)),
                    modifier = Modifier  //lto & sto button
                        .padding(end = 16.dp)
                        .shadow(
                            elevation = 4.dp,
                            spotColor = Color(0x40000000),
                            ambientColor = Color(0x40000000)
                        ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "티칭 보드",
                        style = TextStyle(
                            color = Color(0xFFFFFFFF),
                            fontFamily = fontFamily_Inter,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier
                            .padding(horizontal = 30.dp, vertical = 10.dp)
                    )
                }
            }
        }


        Column(modifier = Modifier.weight(8f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp)
            ) {
                TeacherInfor(modifier = Modifier.weight(1f), onClickEdit = {setEditProfileDialog(true)})

                Column(modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 16.dp, end = 16.dp, bottom = 4.dp)
                    .weight(3f)
                    .fillMaxHeight()
                    .background(color = Color(0xFFE7EBF0), shape = RoundedCornerShape(size = 10.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)

                    ) {
                        CenterCardInCenterDashboard(
                            modifier = Modifier.weight(1f),
                            allCentersSize = allCenters?.size.toString()
                        )// 지점 선택
                        ClassCardInCenterDashboard(
                            modifier = Modifier.weight(1f),
                            allChildClassesSize = allChildClasses?.size.toString()

                        ) //반 선택
                        ChildrenCardInCenterDashboard(
                            modifier = Modifier.weight(1f),
                            allChildInfosSize = allChildInfos?.size.toString()
                        ) //아이 선택

                    }
                    Row {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                                .padding(16.dp)
                                .background(
                                    color = Color(0xFFF8F9FA),
                                    shape = RoundedCornerShape(size = 10.dp)
                                )
                        ){
                            Column {
                                Text(modifier =Modifier.padding(16.dp),text = "NOTICE",
                                    style = TextStyle(
                                        fontSize = 28.sp,
                                        lineHeight = 24.sp,
                                        fontWeight = FontWeight(600),
                                        color = Color(0xFF000000),

                                        letterSpacing = 0.28.sp,)
                                )
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .shadow(
                                        elevation = 4.dp,
                                        spotColor = Color(0x1A000000),
                                        ambientColor = Color(0x1A000000)
                                    )
                                    .height(60.dp)
                                    .background(
                                        color = Color(0xFFFFFFFF),
                                        shape = RoundedCornerShape(size = 5.dp)
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier. padding(16.dp),
                                        text = "23/11/03",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 24.sp,
                                            fontWeight = FontWeight(600),
                                            color = Color(0xFF7C838A),
                                            letterSpacing = 0.16.sp,
                                        )
                                    )
                                    Text(
                                        modifier = Modifier. padding(16.dp),
                                        text = "보고서 기능 개선",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 24.sp,
                                            fontWeight = FontWeight(600),
                                            color = Color(0xFF7C838A),
                                            letterSpacing = 0.16.sp,
                                        )
                                    )
                                }
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .shadow(
                                        elevation = 4.dp,
                                        spotColor = Color(0x1A000000),
                                        ambientColor = Color(0x1A000000)
                                    )
                                    .height(60.dp)
                                    .background(
                                        color = Color(0xFFFFFFFF),
                                        shape = RoundedCornerShape(size = 5.dp)
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier. padding(16.dp),
                                        text = "23/10/25",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 24.sp,
                                            fontWeight = FontWeight(600),
                                            color = Color(0xFF7C838A),
                                            letterSpacing = 0.16.sp,
                                        )
                                    )
                                    Text(
                                        modifier = Modifier. padding(16.dp),
                                        text = "긴급 공지사항",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 24.sp,
                                            fontWeight = FontWeight(600),
                                            color = Color(0xFF7C838A),
                                            letterSpacing = 0.16.sp,
                                        )
                                    )
                                }
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .shadow(
                                        elevation = 4.dp,
                                        spotColor = Color(0x1A000000),
                                        ambientColor = Color(0x1A000000)
                                    )
                                    .height(60.dp)
                                    .background(
                                        color = Color(0xFFFFFFFF),
                                        shape = RoundedCornerShape(size = 5.dp)
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier. padding(16.dp),
                                        text = "23/10/15",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 24.sp,
                                            fontWeight = FontWeight(600),
                                            color = Color(0xFF7C838A),
                                            letterSpacing = 0.16.sp,
                                        )
                                    )
                                    Text(
                                        modifier = Modifier. padding(16.dp),
                                        text = "지점별 권한 부여 기능 추가",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 24.sp,
                                            fontWeight = FontWeight(600),
                                            color = Color(0xFF7C838A),
                                            letterSpacing = 0.16.sp,
                                        )
                                    )
                                }
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .shadow(
                                        elevation = 4.dp,
                                        spotColor = Color(0x1A000000),
                                        ambientColor = Color(0x1A000000)
                                    )
                                    .height(60.dp)
                                    .background(
                                        color = Color(0xFFFFFFFF),
                                        shape = RoundedCornerShape(size = 5.dp)
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier. padding(16.dp),
                                        text = "23/10/4",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 24.sp,
                                            fontWeight = FontWeight(600),
                                            color = Color(0xFF7C838A),
                                            letterSpacing = 0.16.sp,
                                        )
                                    )
                                    Text(
                                        modifier = Modifier. padding(16.dp),
                                        text = "Teaching Board 기능 개선",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 24.sp,
                                            fontWeight = FontWeight(600),
                                            color = Color(0xFF7C838A),
                                            letterSpacing = 0.16.sp,
                                        )
                                    )
                                }
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .shadow(
                                        elevation = 4.dp,
                                        spotColor = Color(0x1A000000),
                                        ambientColor = Color(0x1A000000)
                                    )
                                    .height(60.dp)
                                    .background(
                                        color = Color(0xFFFFFFFF),
                                        shape = RoundedCornerShape(size = 5.dp)
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier. padding(16.dp),
                                        text = "23/10/4",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 24.sp,
                                            fontWeight = FontWeight(600),
                                            color = Color(0xFF7C838A),
                                            letterSpacing = 0.16.sp,
                                        )
                                    )
                                    Text(
                                        modifier = Modifier. padding(16.dp),
                                        text = "Teaching Board 기능 개선",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 24.sp,
                                            fontWeight = FontWeight(600),
                                            color = Color(0xFF7C838A),
                                            letterSpacing = 0.16.sp,
                                        )
                                    )
                                }


                            }

                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                                .padding(16.dp)
                                .background(
                                    color = Color(0xFFF8F9FA),
                                    shape = RoundedCornerShape(size = 10.dp)
                                )
                        ){
                            Column {
                                Text(modifier =Modifier.padding(16.dp),text = "DM",
                                    style = TextStyle(
                                        fontSize = 28.sp,
                                        lineHeight = 24.sp,
                                        fontWeight = FontWeight(600),
                                        color = Color(0xFF000000),

                                        letterSpacing = 0.28.sp,)
                                )
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .shadow(
                                        elevation = 4.dp,
                                        spotColor = Color(0x1A000000),
                                        ambientColor = Color(0x1A000000)
                                    )
                                    .height(60.dp)
                                    .background(
                                        color = Color(0xFFFFFFFF),
                                        shape = RoundedCornerShape(size = 5.dp)
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        Modifier
                                            .height(40.dp)
                                            .width(40.dp)
                                            .padding(start = 8.dp)
                                            .background(
                                                color = Color(0xFF7E5DE3),
                                                shape = RoundedCornerShape(size = 12.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "송",
                                            style = TextStyle(
                                                fontSize = 20.sp,
                                                lineHeight = 24.sp,
                                                fontWeight = FontWeight(600),
                                                color = Color(0xFFFFFFFF),
                                                textAlign = TextAlign.Center,
                                                letterSpacing = 0.26.sp,
                                            )
                                        )

                                    }

                                    Column(modifier = Modifier
                                        .padding(start = 24.dp, top = 4.dp, bottom = 4.dp)
                                        .weight(6f)
                                    ){
                                        Text(
                                            text = "지언/ QBA",
                                            style = TextStyle(
                                                fontSize = 14.sp,
                                                lineHeight = 24.sp,
                                                fontWeight = FontWeight(600),
                                                color = Color(0xFF000000),
                                                letterSpacing = 0.16.sp,
                                            )
                                        )
                                        Text(
                                            text = "확인 부탁드립니다.",
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                lineHeight = 24.sp,
                                                fontWeight = FontWeight(600),
                                                color = Color(0xFF1B1B1B),
                                                letterSpacing = 0.14.sp,
                                            )
                                        )
                                    }
                                    Text(
                                        modifier = Modifier
                                            .padding(16.dp),
                                        text = "오후 3: 11",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 24.sp,
                                            fontWeight = FontWeight(600),
                                            color = Color(0xFFB8B8B8),

                                            letterSpacing = 0.16.sp,
                                        ))

                                }
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .shadow(
                                        elevation = 4.dp,
                                        spotColor = Color(0x1A000000),
                                        ambientColor = Color(0x1A000000)
                                    )
                                    .height(60.dp)
                                    .background(
                                        color = Color(0xFFFFFFFF),
                                        shape = RoundedCornerShape(size = 5.dp)
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        Modifier
                                            .height(40.dp)
                                            .width(40.dp)
                                            .padding(start = 8.dp)
                                            .background(
                                                color = Color(0xFF7E5DE3),
                                                shape = RoundedCornerShape(size = 12.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "이",
                                            style = TextStyle(
                                                fontSize = 20.sp,
                                                lineHeight = 24.sp,
                                                fontWeight = FontWeight(600),
                                                color = Color(0xFFFFFFFF),
                                                textAlign = TextAlign.Center,
                                                letterSpacing = 0.26.sp,
                                            )
                                        )

                                    }

                                    Column(modifier = Modifier
                                        .padding(start = 24.dp, top = 4.dp, bottom = 4.dp)
                                        .weight(6f)
                                    ){
                                        Text(
                                            text = "하나",
                                            style = TextStyle(
                                                fontSize = 14.sp,
                                                lineHeight = 24.sp,
                                                fontWeight = FontWeight(600),
                                                color = Color(0xFF000000),
                                                letterSpacing = 0.16.sp,
                                            )
                                        )
                                        Text(
                                            text = "확인 부탁드립니다.",
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                lineHeight = 24.sp,
                                                fontWeight = FontWeight(600),
                                                color = Color(0xFF1B1B1B),
                                                letterSpacing = 0.14.sp,
                                            )
                                        )
                                    }
                                    Text(
                                        modifier = Modifier
                                            .padding(16.dp),
                                        text = "오후 3: 11",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 24.sp,
                                            fontWeight = FontWeight(600),
                                            color = Color(0xFFB8B8B8),

                                            letterSpacing = 0.16.sp,
                                        ))

                                }
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .shadow(
                                        elevation = 4.dp,
                                        spotColor = Color(0x1A000000),
                                        ambientColor = Color(0x1A000000)
                                    )
                                    .height(60.dp)
                                    .background(
                                        color = Color(0xFFFFFFFF),
                                        shape = RoundedCornerShape(size = 5.dp)
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        Modifier
                                            .height(40.dp)
                                            .width(40.dp)
                                            .padding(start = 8.dp)
                                            .background(
                                                color = Color(0xFF7E5DE3),
                                                shape = RoundedCornerShape(size = 12.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "김",
                                            style = TextStyle(
                                                fontSize = 20.sp,
                                                lineHeight = 24.sp,
                                                fontWeight = FontWeight(600),
                                                color = Color(0xFFFFFFFF),
                                                textAlign = TextAlign.Center,
                                                letterSpacing = 0.26.sp,
                                            )
                                        )

                                    }

                                    Column(modifier = Modifier
                                        .padding(start = 24.dp, top = 4.dp, bottom = 4.dp)
                                        .weight(6f)
                                    ){
                                        Text(
                                            text = "지언",
                                            style = TextStyle(
                                                fontSize = 14.sp,
                                                lineHeight = 24.sp,
                                                fontWeight = FontWeight(600),
                                                color = Color(0xFF000000),
                                                letterSpacing = 0.16.sp,
                                            )
                                        )
                                        Text(
                                            text = "확인 부탁드립니다.",
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                lineHeight = 24.sp,
                                                fontWeight = FontWeight(600),
                                                color = Color(0xFF1B1B1B),
                                                letterSpacing = 0.14.sp,
                                            )
                                        )
                                    }
                                    Text(
                                        modifier = Modifier
                                            .padding(16.dp),
                                        text = "오후 3: 11",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 24.sp,
                                            fontWeight = FontWeight(600),
                                            color = Color(0xFFB8B8B8),

                                            letterSpacing = 0.16.sp,
                                        ))

                                }
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .shadow(
                                        elevation = 4.dp,
                                        spotColor = Color(0x1A000000),
                                        ambientColor = Color(0x1A000000)
                                    )
                                    .height(60.dp)
                                    .background(
                                        color = Color(0xFFFFFFFF),
                                        shape = RoundedCornerShape(size = 5.dp)
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        Modifier
                                            .height(40.dp)
                                            .width(40.dp)
                                            .padding(start = 8.dp)
                                            .background(
                                                color = Color(0xFF7E5DE3),
                                                shape = RoundedCornerShape(size = 12.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "전",
                                            style = TextStyle(
                                                fontSize = 20.sp,
                                                lineHeight = 24.sp,
                                                fontWeight = FontWeight(600),
                                                color = Color(0xFFFFFFFF),
                                                textAlign = TextAlign.Center,
                                                letterSpacing = 0.26.sp,
                                            )
                                        )

                                    }

                                    Column(modifier = Modifier
                                        .padding(start = 24.dp, top = 4.dp, bottom = 4.dp)
                                        .weight(6f)
                                    ){
                                        Text(
                                            text = "은지 / BCBA(D-120)",
                                            style = TextStyle(
                                                fontSize = 14.sp,
                                                lineHeight = 24.sp,
                                                fontWeight = FontWeight(600),
                                                color = Color(0xFF000000),
                                                letterSpacing = 0.16.sp,
                                            )
                                        )
                                        Text(
                                            text = "확인 부탁드립니다.",
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                lineHeight = 24.sp,
                                                fontWeight = FontWeight(600),
                                                color = Color(0xFF1B1B1B),
                                                letterSpacing = 0.14.sp,
                                            )
                                        )
                                    }
                                    Text(
                                        modifier = Modifier
                                            .padding(16.dp),
                                        text = "오후 3: 11",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 24.sp,
                                            fontWeight = FontWeight(600),
                                            color = Color(0xFFB8B8B8),

                                            letterSpacing = 0.16.sp,
                                        ))

                                }
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .shadow(
                                        elevation = 4.dp,
                                        spotColor = Color(0x1A000000),
                                        ambientColor = Color(0x1A000000)
                                    )
                                    .height(60.dp)
                                    .background(
                                        color = Color(0xFFFFFFFF),
                                        shape = RoundedCornerShape(size = 5.dp)
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        Modifier
                                            .height(40.dp)
                                            .width(40.dp)
                                            .padding(start = 8.dp)
                                            .background(
                                                color = Color(0xFF7E5DE3),
                                                shape = RoundedCornerShape(size = 12.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "전",
                                            style = TextStyle(
                                                fontSize = 20.sp,
                                                lineHeight = 24.sp,
                                                fontWeight = FontWeight(600),
                                                color = Color(0xFFFFFFFF),
                                                textAlign = TextAlign.Center,
                                                letterSpacing = 0.26.sp,
                                            )
                                        )

                                    }

                                    Column(modifier = Modifier
                                        .padding(start = 24.dp, top = 4.dp, bottom = 4.dp)
                                        .weight(6f)
                                    ){
                                        Text(
                                            text = "은지 / BCBA(D-120)",
                                            style = TextStyle(
                                                fontSize = 14.sp,
                                                lineHeight = 24.sp,
                                                fontWeight = FontWeight(600),
                                                color = Color(0xFF000000),
                                                letterSpacing = 0.16.sp,
                                            )
                                        )
                                        Text(
                                            text = "확인 부탁드립니다.",
                                            style = TextStyle(
                                                fontSize = 12.sp,
                                                lineHeight = 24.sp,
                                                fontWeight = FontWeight(600),
                                                color = Color(0xFF1B1B1B),
                                                letterSpacing = 0.14.sp,
                                            )
                                        )
                                    }
                                    Text(
                                        modifier = Modifier
                                            .padding(16.dp),
                                        text = "오후 3: 11",
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            lineHeight = 24.sp,
                                            fontWeight = FontWeight(600),
                                            color = Color(0xFFB8B8B8),

                                            letterSpacing = 0.16.sp,
                                        ))

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CenterCardInCenterDashboard(
    modifier: Modifier = Modifier,
    allCentersSize : String
){
    Box(modifier = modifier   //카드 하얀 배경
        .height(50.dp)
        .shadow(
            elevation = 4.dp,
            spotColor = Color(0x40000000),
            ambientColor = Color(0x40000000)
        )
        .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp))

    ) {

        Row(
            modifier = Modifier
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(modifier= Modifier

                .width(21.dp)
                .align(Alignment.CenterVertically)
                .height(24.dp),
                painter = painterResource(id = R.drawable.center_icon),
                contentDescription = "null",
                contentScale = ContentScale.Crop)
            Text(modifier = Modifier
                .width(53.dp)
                .height(32.dp)
                .padding(top = 2.dp),
                text = "Center",
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFFA6ACBE),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.14.sp,
                )
            )
            Text(
                modifier = Modifier
                    .width(140.dp)
                    .height(32.dp),
//                text = if(allCentersSize == "null") "없음" else allCentersSize,
                text = "",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF0047B3),
                    letterSpacing = 0.28.sp,
                    textAlign = TextAlign.Center)
            )


        }
    }

}
@Composable
fun ClassCardInCenterDashboard(
    modifier: Modifier = Modifier,
    allChildClassesSize : String
){
    Box(modifier = modifier   //카드 하얀 배경
        .height(50.dp)
        .shadow(
            elevation = 4.dp,
            spotColor = Color(0x40000000),
            ambientColor = Color(0x40000000)
        )
        .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp))

    ) {

        Row(
            modifier = Modifier
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(modifier= Modifier
                .width(22.dp)
                .align(Alignment.CenterVertically)
                .height(26.dp),
                painter = painterResource(id = R.drawable.class_icon),
                contentDescription = "null",
                contentScale = ContentScale.Fit)

            Text(modifier = Modifier
                .width(53.dp)
                .height(32.dp)
                .padding(top = 2.dp),
                text = "Class",
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFFA6ACBE),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.14.sp,
                )
            )
            Text(
                modifier = Modifier
                    .width(140.dp)

                    .height(32.dp),
//                text = if(allChildClassesSize == "null") "없음" else allChildClassesSize,
                text = "",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF0047B3),
                    letterSpacing = 0.28.sp,
                    textAlign = TextAlign.Center)
            )




        }
    }

}
@Composable
fun ChildrenCardInCenterDashboard(
    modifier: Modifier = Modifier,
    allChildInfosSize : String,
){
    Box(modifier = modifier   //카드 하얀 배경
        .height(50.dp)
        .shadow(
            elevation = 4.dp,
            spotColor = Color(0x40000000),
            ambientColor = Color(0x40000000)
        )
        .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp)),

        ) {

        Row(
            modifier = Modifier
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(modifier= Modifier
                .width(20.dp)
                .align(Alignment.CenterVertically)
                .height(20.dp),
                painter = painterResource(id = R.drawable.child_icon),
                contentDescription = "null",
                contentScale = ContentScale.Fit)

            Text(modifier = Modifier
                .width(53.dp)
                .height(32.dp)
                .padding(top = 2.dp),
                text = "Child",
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFFA6ACBE),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.14.sp,
                )
            )
            Text(
                modifier = Modifier
                    .width(140.dp)
                    .height(32.dp),
//                text = if(allChildInfosSize == "null") "없음" else allChildInfosSize,
                text = "",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF0047B3),
                    letterSpacing = 0.28.sp,
                    textAlign = TextAlign.Center)
            )




        }
    }

}