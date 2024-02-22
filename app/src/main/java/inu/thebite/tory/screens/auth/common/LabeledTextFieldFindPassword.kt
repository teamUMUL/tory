package inu.thebite.tory.screens.auth.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabeledTextFieldFindPassword(
    userId : MutableState<TextFieldValue>,
    userName : MutableState<TextFieldValue>,
    userPhoneNumber : MutableState<TextFieldValue>
){



    Column {
        //아이디
        TextFieldWithTrailingIcon(
            title = "아이디",
            placeholderText = "ID를 입력해주세요",
            input = userId,
        )
        Spacer(modifier = Modifier.height(10.dp))
        //이메일
        TextFieldWithTrailingIcon(
            title = "이름",
            placeholderText = "이름을 입력해주세요",
            input = userName,
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(10.dp))
        //휴대전화
        TextFieldWithTrailingIcon(
            title = "휴대전화",
            placeholderText = "전화번호를 입력해주세요(010-0000-0000)",
            input = userPhoneNumber,
            keyboardType = KeyboardType.Phone
        )
    }
}