package inu.thebite.tory.screens.auth.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
fun LabeledTextFieldLogin(
    userid : MutableState<TextFieldValue>,
    password : MutableState<TextFieldValue>
){

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    Column {
        //아이디
        Column(
            modifier = Modifier

                .width(400.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "ID",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF7C838A),
                )
            )
            TextField(
                label = {
                    Text(
                        text = "Enter your ID here",
                        style = TextStyle(
                            textAlign = TextAlign.Start,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0x80000000),
                        ),
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                            .height(40.dp)

                    )
                },
                value = userid.value,
                onValueChange = { userid.value = it },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = true, keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = null,
                        modifier = Modifier
                            .clickableWithNoRipple {
                                userid.value = TextFieldValue("")
                            }
                    )
                }
            )
        }

        //비밀번호
        Column(
            modifier = Modifier

                .width(400.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "PASSWORD",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF7C838A),
                )
            )
            TextField(
                label = {
                    Text(
                        text = "Enter your PASSWORD here",
                        style = TextStyle(
                            textAlign = TextAlign.Start,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0x80000000),
                        ),
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                            .height(40.dp)

                    )
                },
                value = password.value,
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                onValueChange = { password.value = it },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = true, keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    val image = if (passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible.value) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                }

            )
        }



    }
}