@file:OptIn(ExperimentalMaterial3Api::class)

package inu.thebite.tory.screens.centerdashboardscreen.dialog

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import inu.thebite.tory.ui.theme.ToryTheme
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileDialog(
    setEditProfileDialog: (Boolean) -> Unit
) {
    var nameInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var idInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var passwordInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var emailInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var phoneInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var identityInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var centerCodeInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var qualificationInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    val verticalScrollState = rememberScrollState()

    Dialog(
        onDismissRequest = { setEditProfileDialog(false) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .width(900.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .padding(
                    horizontal = 35.dp,
                    vertical = 30.dp
                )
                .verticalScroll(verticalScrollState),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(
                text = "프로필 편집",
                style = TextStyle(
                    fontFamily = fontFamily_Inter,
                    fontWeight = FontWeight(400),
                    fontSize = 33.sp
                )
            )
            EditProfileDialogTextField(
                inputName = "이름",
                inputValue = nameInputValue,
                setInputValue = { nameInputValue = it }
            )
            EditProfileDialogTextField(
                inputName = "ID",
                inputValue = idInputValue,
                setInputValue = { idInputValue = it }
            )
            EditProfileDialogTextField(
                inputName = "비밀번호",
                inputValue = passwordInputValue,
                setInputValue = { passwordInputValue = it }
            )
            EditProfileDialogTextField(
                inputName = "E-MAIL",
                inputValue = emailInputValue,
                setInputValue = { emailInputValue = it }
            )
            EditProfileDialogTextField(
                inputName = "휴대전화",
                inputValue = phoneInputValue,
                setInputValue = { phoneInputValue = it }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "구분을 선택해주세요.",
                    style = TextStyle(
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF606060),
                        fontSize = 20.sp
                    ),
                    modifier = Modifier
                        .padding(start = 10.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    RadioButton(
                        selected = (identityInputValue.text == "센터장/소장"),
                        onClick = { identityInputValue = TextFieldValue("센터장/소장") })
                    Text(
                        text = "센터장/소장",
                        style = TextStyle(
                            fontFamily = fontFamily_Inter,
                            fontWeight = FontWeight(400),
                            fontSize = 20.sp,
                            color = Color(0xFF606060)
                        )
                    )
                    RadioButton(
                        selected = (identityInputValue.text == "센터 소속 교사"),
                        onClick = { identityInputValue = TextFieldValue("센터 소속 교사") })
                    Text(
                        text = "센터 소속 교사",
                        style = TextStyle(
                            fontFamily = fontFamily_Inter,
                            fontWeight = FontWeight(400),
                            fontSize = 20.sp,
                            color = Color(0xFF606060)
                        )
                    )
                }
            }

            EditProfileDialogTextField(
                inputName = "센터코드",
                inputValue = centerCodeInputValue,
                setInputValue = { centerCodeInputValue = it }
            )
            EditProfileDialogTextField(
                inputName = "자격",
                inputValue = qualificationInputValue,
                setInputValue = { qualificationInputValue = it }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { setEditProfileDialog(false) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBFBFBF)
                    ),
                    modifier = Modifier
                        .weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "취소",
                        style = TextStyle(
                            fontFamily = fontFamily_Poppins,
                            fontWeight = FontWeight.Medium,
                            fontSize = 26.sp,
                            color = Color.White
                        )
                    )
                }
                Spacer(modifier = Modifier.width(100.dp))
                Button(
                    onClick = { setEditProfileDialog(false) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0047B3)
                    ),
                    modifier = Modifier
                        .weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "수정",
                        style = TextStyle(
                            fontFamily = fontFamily_Poppins,
                            fontWeight = FontWeight.Medium,
                            fontSize = 26.sp,
                            color = Color.White
                        )
                    )
                }
            }
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileDialogTextField(
    inputName: String,
    inputValue: TextFieldValue,
    setInputValue: (TextFieldValue) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color = Color(0xFFDFE3E7), shape = RoundedCornerShape(10.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = inputName,
            style = TextStyle(
                fontFamily = fontFamily_Inter,
                fontWeight = FontWeight(400),
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                color = Color(0xFF606060)
            ),
            modifier = Modifier
                .width(90.dp)
                .padding(start = 10.dp)
        )
        TextField(
            value = inputValue,
            onValueChange = { setInputValue(it) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true, keyboardType = keyboardType, imeAction = ImeAction.Done
            ),
            textStyle = TextStyle(
                color = Color(0xFF606060),
                fontSize = 20.sp,
                fontFamily = fontFamily_Inter
            ),
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            )
        )
    }
}

@Preview(showBackground = true, widthDp = 1800, heightDp = 1200)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, widthDp = 1800, heightDp = 1200)
@Composable
fun ArticleCardPreview() {
    ToryTheme {
        Surface(
            modifier = Modifier
                .width(1800.dp)
        ) {
            EditProfileDialog(
                setEditProfileDialog = {}
            )
        }

    }
}