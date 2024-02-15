package inu.thebite.tory.screens.teachingboard.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Lato
import inu.thebite.tory.ui.theme.fontFamily_Poppins

@Composable
fun RecentListDialog(
    setRecentListDialog: (Boolean) -> Unit
) {
    var stoState by remember {
        mutableStateOf("")
    }
    Dialog(
        onDismissRequest = {
            setRecentListDialog(false)
        },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.8f)
                .background(Color(0xFFE3E3E3), shape = RoundedCornerShape(10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RecentListDialogTopBar(stoState = stoState, changeState = { stoState = it })
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "11/ 16",
                            style = TextStyle(
                                color = Color(0xFF898989),
                                fontFamily = fontFamily_Inter,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier
                                .padding(start = 30.dp, end = 20.dp)
                        )
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .fillMaxHeight()
                                .padding(vertical = 10.dp)
                                .background(
                                    color = Color(0xFFE3E3E3),
                                    shape = RoundedCornerShape(10.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "홍길동",
                                style = TextStyle(
                                    color = Color(0xFF3A3A3A),
                                    fontFamily = fontFamily_Inter,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                        LazyRow(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        ) {
                            item {
                                Surface(
                                    modifier = Modifier
                                        .width(150.dp)
                                        .fillMaxHeight(),
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color.White,
                                    elevation = 2.dp
                                ) {
                                    Column{
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .weight(1f)
                                                .background(
                                                    color = Color(0xFFCCEFC0),
                                                    shape = RoundedCornerShape(
                                                        topStart = 10.dp,
                                                        topEnd = 10.dp,
                                                        bottomEnd = 0.dp,
                                                        bottomStart = 0.dp
                                                    )
                                                ),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = "같은 사진 매칭(6)",
                                                style = TextStyle(
                                                    fontFamily = fontFamily_Inter,
                                                    fontWeight = FontWeight.SemiBold,
                                                    fontSize = 12.sp,
                                                    color = Color.Black
                                                )
                                            )
                                        }
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .weight(1f),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = "STO1",
                                                style = TextStyle(
                                                    fontFamily = fontFamily_Inter,
                                                    fontWeight = FontWeight.SemiBold,
                                                    fontSize = 12.sp,
                                                    color = Color.Black
                                                )
                                            )
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }
            Button(
                onClick = {
                    setRecentListDialog(false)
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFBFBFBF)
                ),
                modifier = Modifier
                    .width(320.dp)
                    .height(60.dp)
                    .padding(vertical = 10.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "취소",
                    style = TextStyle(
                        fontFamily = fontFamily_Poppins,
                        fontWeight = FontWeight.Medium,
                        fontSize = 26.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                )

            }
        }
    }

}

@Composable
fun RecentListDialogTopBar(
    stoState: String,
    changeState: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "최근 중재 목록",
            style = TextStyle(
                fontFamily = fontFamily_Inter,
                fontWeight = FontWeight(400),
                fontSize = 33.sp,
                color = Color.Black
            ),
            modifier = Modifier.padding(start = 10.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {

                },
                modifier = Modifier
                    .height(35.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 10.dp)
            ) {
                Text(
                    text = "2023/11/01",
                    style = TextStyle(
                        fontFamily = fontFamily_Lato,
                        fontWeight = FontWeight(400),
                        fontSize = 18.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                )
            }

            Text(
                text = "~",
                style = TextStyle(
                    fontFamily = fontFamily_Lato,
                    fontWeight = FontWeight(400),
                    fontSize = 18.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
            )
            Button(
                onClick = {

                },
                modifier = Modifier
                    .height(35.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 10.dp)
            ) {
                Text(
                    text = "2024/01/30",
                    style = TextStyle(
                        fontFamily = fontFamily_Lato,
                        fontWeight = FontWeight(400),
                        fontSize = 18.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                )
            }
            Button(
                onClick = {

                },
                modifier = Modifier
                    .height(35.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0047B3),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 35.dp)
            ) {
                Text(
                    text = "조회",
                    style = TextStyle(
                        fontFamily = fontFamily_Lato,
                        fontWeight = FontWeight(400),
                        fontSize = 18.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(1.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val buttonList = listOf(
                    "진행중",
                    "준거 도달",
                    "중지"
                )
                buttonList.forEach { button ->
                    OutlinedButton(
                        modifier = Modifier
                            .height(35.dp)
                            .padding(horizontal = 2.dp),
                        onClick = {
                            changeState(button)
                        },
                        border = BorderStroke(
                            width = 1.dp,
                            color = when (button) {
                                "준거 도달" -> Color(0xFF34C648)
                                "진행중" -> Color(0xFF40B9FC)
                                "중지" -> Color(0xFFFC605C)
                                else -> Color.Black
                            }
                        ),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when (button) {
                                "준거 도달" -> if (stoState == button) Color(
                                    0xFF34C648
                                ) else Color.Transparent

                                "진행중" -> if (stoState == button) Color(0xFF40B9FC) else Color.Transparent
                                "중지" -> if (stoState == button) Color(0xFFFC605C) else Color.Transparent
                                else -> if (stoState == button) Color.White else Color.White
                            },
                            contentColor = when (button) {
                                "준거 도달" -> if (stoState == button) Color.White else Color(
                                    0xFF34C648
                                )

                                "진행중" -> if (stoState == button) Color.White else Color(
                                    0xFF40B9FC
                                )

                                "중지" -> if (stoState == button) Color.White else Color(
                                    0xFFFC605C
                                )

                                else -> if (stoState == button) Color.White else Color.Black
                            }
                        ),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = button,
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = fontFamily_Lato,
                                fontWeight = FontWeight(500),
                                textAlign = TextAlign.Center,
                                color = when (button) {
                                    "준거 도달" -> if (stoState == button) Color.White else Color(
                                        0xFF34C648
                                    )

                                    "진행중" -> if (stoState == button) Color.White else Color(
                                        0xFF40B9FC
                                    )

                                    "중지" -> if (stoState == button) Color.White else Color(
                                        0xFFFC605C
                                    )

                                    else -> if (stoState == button) Color.White else Color.Black
                                }
                            )
                        )
                    }
                }
            }
        }
    }
}