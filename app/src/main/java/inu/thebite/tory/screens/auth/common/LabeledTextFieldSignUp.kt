package inu.thebite.tory.screens.auth.common

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inu.thebite.tory.screens.education.screen.clickableWithNoRipple
import inu.thebite.tory.ui.theme.fontFamily_Poppins

@Composable
fun LabeledTextFieldSignUp(
    userName: MutableState<TextFieldValue>,
    userId: MutableState<TextFieldValue>,
    password: MutableState<TextFieldValue>,
    userEmail: MutableState<TextFieldValue>,
    userPhoneNumber: MutableState<TextFieldValue>,
    userIdentity: MutableState<TextFieldValue>,
    userCenterCode: MutableState<TextFieldValue>
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        TextFieldWithTrailingIcon(
            title = "이름",
            placeholderText = "이름을 입력해주세요",
            input = userName,
        )
        Spacer(modifier = Modifier.height(5.dp))
        //아이디
        TextFieldWithTrailingIcon(
            title = "아이디",
            placeholderText = "ID를 입력해주세요",
            input = userId,
        )
        Spacer(modifier = Modifier.height(5.dp))
        //비밀번호
        TextFieldWithTrailingIcon(
            title = "비밀번호",
            placeholderText = "비밀번호를 입력해주세요",
            input = password,
            isPassword = true
        )
        Spacer(modifier = Modifier.height(5.dp))
        //이메일
        TextFieldWithTrailingIcon(
            title = "E-MAIL",
            placeholderText = "E-MAIL을 입력해주세요",
            input = userEmail,
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(5.dp))
        //휴대전화
        TextFieldWithTrailingIcon(
            title = "휴대전화",
            placeholderText = "전화번호를 입력해주세요(010-0000-0000)",
            input = userPhoneNumber,
            keyboardType = KeyboardType.Phone
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            RadioButton(
                selected = (userIdentity.value.text == "센터장/소장"),
                onClick = { userIdentity.value = TextFieldValue("센터장/소장") })
            Text(
                text = "센터장/소장",
                style = TextStyle(
                    fontFamily = fontFamily_Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            )
            RadioButton(
                selected = (userIdentity.value.text == "센터 소속 교사"),
                onClick = { userIdentity.value = TextFieldValue("센터 소속 교사") })
            Text(
                text = "센터 소속 교사",
                style = TextStyle(
                    fontFamily = fontFamily_Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            )
        }
        if (userIdentity.value.text == "센터 소속 교사"){
            Spacer(modifier = Modifier.height(5.dp))
            //센터코드
            TextFieldWithTrailingIcon(
                title = "센터 코드",
                placeholderText = "센터 코드를 입력해주세요",
                input = userCenterCode,
                keyboardType = KeyboardType.Number
            )
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldWithTrailingIcon(
    isPassword: Boolean = false,
    title: String,
    placeholderText: String,
    input: MutableState<TextFieldValue>,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val passwordVisible = remember {
        mutableStateOf(false)
    }
    if (isPassword) {
        Column(
            modifier = Modifier
                .width(400.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF7C838A),
                )
            )
            TextField(
                placeholder = {
                    Text(
                        text = placeholderText,
                        style = TextStyle(
                            textAlign = TextAlign.Start,
                            fontSize = 12.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0x80000000),
                        ),
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                    )
                },
                value = input.value,
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                onValueChange = { input.value = it },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFB0BAC3).copy(alpha = 0.4f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = true,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    val image =
                        if (passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description =
                        if (passwordVisible.value) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    } else {
        Column(
            modifier = Modifier

                .width(400.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF7C838A),
                )
            )
            TextField(
                placeholder = {
                    Text(
                        text = placeholderText,
                        style = TextStyle(
                            textAlign = TextAlign.Start,
                            fontSize = 12.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0x80000000),
                        ),
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                    )
                },
                value = input.value,
                onValueChange = { input.value = it },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFB0BAC3).copy(alpha = 0.4f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = true, keyboardType = keyboardType, imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = null,
                        modifier = Modifier
                            .clickableWithNoRipple {
                                input.value = TextFieldValue("")
                            }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }

}