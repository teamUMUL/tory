package inu.thebite.tory.screens.auth.login

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import es.dmoral.toasty.Toasty
import inu.thebite.tory.R
import inu.thebite.tory.screens.auth.LoginState
import inu.thebite.tory.screens.auth.common.LabeledTextFieldLogin
import inu.thebite.tory.ui.theme.ToryTheme
import inu.thebite.tory.ui.theme.fontFamily_Poppins
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val authViewModel = LoginState.current
    val isLoading by authViewModel.isLoading.collectAsState()
    val userid = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val coroutineScope = rememberCoroutineScope()
    val brush = Brush.horizontalGradient(listOf(Color(0xFF0047B3), Color(0xff7E5DE3)))

    Box(modifier = Modifier
        .fillMaxSize()
        .background(brush),
        contentAlignment = Alignment.Center) {
        Box(modifier = Modifier
            .shadow(
                elevation = 4.dp,
                spotColor = Color(0x40000000),
                ambientColor = Color(0x40000000)
            )
            .width(1022.dp)
            .height(710.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp)) ,

            ){
            Row(modifier = Modifier
            ){
                Image(painter = painterResource(id = R.drawable.login_image), contentDescription ="logIn_image",modifier= Modifier
                    .weight(1f)
                    .fillMaxHeight())
                Column(modifier= Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(top = 30.dp, bottom = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.prom_logo),
                        contentDescription = null,
                        modifier = Modifier
                            .width(480.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Column(modifier= Modifier) {

                        LabeledTextFieldLogin(
                            userId = userid,
                            password = password
                        )

                    }

                    Spacer(modifier = Modifier.height(50.dp))
                    if (isLoading){
                        CircularProgressIndicator(modifier = Modifier.size(50.dp))
                    } else {
                        Button(modifier= Modifier

                            .width(320.dp)
                            .height(60.dp)
                            .background(
                                color = Color(0xFF0047B3),
                                shape = RoundedCornerShape(size = 10.dp)
                            ), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0047B3)),
                            onClick = {
                                if (userid.value.text.trim() == "" || password.value.text.trim() == ""){
                                    Toasty.warning(context, "아이디와 비밀번호를 입력해주세요", Toast.LENGTH_SHORT, true).show()
                                } else {
                                    coroutineScope.launch {
                                        authViewModel.login(context = context,id = userid.value.text.trim(), password = password.value.text.trim())
                                    }
                                }
                            }
                        ) {
                            Text(
                                text = "로그인",
                                style = TextStyle(
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFFFFFFFF),

                                    )
                            )

                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        ClickableText(
                            text = AnnotatedString("회원가입"),
                            onClick = {
                                navController.navigate("signupScreen")
                            },
                            style = TextStyle(
                                fontFamily = fontFamily_Poppins,
                                fontSize = 18.sp,
                                color = Color(0xff7F5AF0)
                            )
                        )
                        Text(
                            text = "|",
                            style = TextStyle(
                                fontFamily = fontFamily_Poppins,
                                fontSize = 18.sp,
                            )
                        )
                        ClickableText(
                            text = AnnotatedString("ID 찾기"),
                            onClick = {
                                navController.navigate("findIdScreen")
                            },
                            style = TextStyle(
                                fontFamily = fontFamily_Poppins,
                                fontSize = 18.sp,
                                color = Color(0xff7F5AF0)
                            )
                        )
                        Text(
                            text = "|",
                            style = TextStyle(
                                fontFamily = fontFamily_Poppins,
                                fontSize = 18.sp,
                            )
                        )
                        ClickableText(
                            text = AnnotatedString("비밀번호 찾기"),
                            onClick = {
                                navController.navigate("findPasswordScreen")
                            },
                            style = TextStyle(
                                fontFamily = fontFamily_Poppins,
                                fontSize = 18.sp,
                                color = Color(0xff7F5AF0)
                            )
                        )
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 720, widthDp = 1080)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginPreview(){
    ToryTheme {
    }
}
