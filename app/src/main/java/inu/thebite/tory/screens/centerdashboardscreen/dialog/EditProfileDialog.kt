@file:OptIn(ExperimentalMaterial3Api::class)

package inu.thebite.tory.screens.centerdashboardscreen.dialog

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import inu.thebite.tory.model.member.EditProfileRequest
import inu.thebite.tory.model.member.MemberResponse
import inu.thebite.tory.screens.auth.LoginState
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Lato
import inu.thebite.tory.ui.theme.fontFamily_Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileDialog(
    setEditProfileDialog: (Boolean) -> Unit,
    userInfo: MemberResponse
) {

    val authViewModel = LoginState.current

    val selectedQualifications by authViewModel.selectedQualifications.collectAsState()

    var nameInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(userInfo.name))
    }
//    var idInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
//        mutableStateOf(TextFieldValue())
//    }
//    var passwordInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
//        mutableStateOf(TextFieldValue())
//    }
//    var emailInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
//        mutableStateOf(TextFieldValue())
//    }
//    var phoneInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
//        mutableStateOf(TextFieldValue())
//    }
//    var identityInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
//        mutableStateOf(TextFieldValue())
//    }
//    var centerCodeInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
//        mutableStateOf(TextFieldValue())
//    }
    var forteInputValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(userInfo.forte))
    }

    val verticalScrollState = rememberScrollState()

    var isExpanded by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        authViewModel.setSelectedQualification(userInfo.qualification)
    }

    val majorList = listOf(
        "BCBA",
        "QBA",
        "KABA",
        "ESDM 공인치료사",
        "응용행동분석전문가(ABAS)",
        "사회복지사",
        "놀이심리상담사",
        "사회성인치료사",
        "인지학습치료사"
    )

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
                inputName = "전문분야",
                inputValue = forteInputValue,
                setInputValue = { forteInputValue = it }
            )
            Text(
                text = "대표자격",
                style = TextStyle(
                    fontFamily = fontFamily_Lato,
                    color = Color(0xFF1D1C1D),
                    fontSize = 18.sp
                ),
            )
            LazyHorizontalGrid(
                rows = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                userScrollEnabled = false
            ) {
                items(majorList) { majorItem ->
                    Button(
                        onClick = {
                            selectedQualifications?.let { selectedQualifications ->
                                if (selectedQualifications.contains(majorItem)) {
                                    authViewModel.removeSelectedQualification(majorItem)
                                } else {
                                    authViewModel.addSelectedQualification(majorItem)
                                }
                            }
                        },
                        modifier = Modifier
                            .height(30.dp)
                            .padding(bottom = 2.dp, end = 5.dp, top = 2.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color(0xFF0047B3).copy(alpha = 0.5f)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                            selectedQualifications?.let { selectedQualifications ->
                                if (selectedQualifications.contains(majorItem)) {
                                    Color(0xFFE0E9F5)
                                } else {
                                    Color.Transparent
                                }
                            } ?: run {
                                Color.Transparent
                            },
                            contentColor = Color.Black
                        ),
                        contentPadding = PaddingValues(vertical = 1.dp, horizontal = 10.dp)
                    ) {
                        Text(text = majorItem)
                    }
                }
            }
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
                    onClick = {
                        selectedQualifications?.let { selectedQualifications ->
                            authViewModel.editProfile(
                                editProfileRequest = EditProfileRequest(
                                    name = nameInputValue.text,
                                    forte = forteInputValue.text,
                                    qualification = selectedQualifications
                                )
                            )
                        }

                        setEditProfileDialog(false)
                    },
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

//@Preview(showBackground = true, widthDp = 1800, heightDp = 1200)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, widthDp = 1800, heightDp = 1200)
//@Composable
//fun ArticleCardPreview() {
//    ToryTheme {
//        Surface(
//            modifier = Modifier
//                .width(1800.dp)
//        ) {
//            EditProfileDialog(
//                setEditProfileDialog = {},
//            )
//        }
//
//    }
//}