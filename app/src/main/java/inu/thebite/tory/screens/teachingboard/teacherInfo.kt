package inu.thebite.tory.screens.teachingboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import inu.thebite.tory.screens.auth.AuthViewModel
import inu.thebite.tory.screens.auth.LoginState
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Poppins

@Composable
fun TeacherInfor(
    modifier: Modifier = Modifier,
    onClickEdit : () -> Unit,
 ) {
    val authViewModel = LoginState.current

    val userInfo by authViewModel.userInfo.collectAsState()

    val verticalScrollState = rememberScrollState()

    Row(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                spotColor = Color(0x40000000),
                ambientColor = Color(0x40000000)
            )
//            .width(241.dp)
//            .height(440.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 25.dp, end = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(16.dp),
                painter = painterResource(id = R.drawable.teacher_image),
                contentDescription = ""
            )
            Column(
                modifier = Modifier
                    .verticalScroll(verticalScrollState)
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start
            )
            {
                Text(
                    text = "이름",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF0047B3)
                    )
                )
                Text(
                    text = userInfo?.let { it.name } ?: run {""},
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),

                        )
                )
                Text(
                    text = "전문분야",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF0047B3),

                        )
                )
                Text(
                    text = userInfo?.let { it.forte } ?: run {""},
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),

                        )
                )
                Text(
                    text = "소속",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF0047B3),
                    )
                )
                Text(
                    text = userInfo?.let { it.centerName } ?: run {""},
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),
                    )
                )
                Text(
                    text = "자격",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF0047B3),
                    )
                )
                userInfo?.let { userInfo ->
                    userInfo.qualification.forEach { qualification ->
                        Text(
                            text = qualification,
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight(600),
                                color = Color(0xFF000000),
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            Button(
                onClick = { onClickEdit()},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0047B3)
                ),
                modifier = Modifier  //lto & sto button
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)


            ) {
                Text(
                    text = "프로필 편집",
                    style = TextStyle(
                        color = Color(0xFFFFFFFF),
                        fontSize = 24.sp,
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }

}

