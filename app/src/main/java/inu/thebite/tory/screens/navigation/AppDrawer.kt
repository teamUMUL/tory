package inu.thebite.tory.screens.navigation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import es.dmoral.toasty.Toasty
import inu.thebite.tory.R
import inu.thebite.tory.model.lto.LtoRequest
import inu.thebite.tory.model.member.UpdatePasswordRequest
import inu.thebite.tory.screens.auth.LoginState
import inu.thebite.tory.screens.centerdashboardscreen.dialog.EditProfileDialogTextField
import inu.thebite.tory.ui.theme.fontFamily_Inter
import inu.thebite.tory.ui.theme.fontFamily_Poppins
import kotlinx.coroutines.launch

@Composable
fun AppDrawer(
    route: String,
    modifier: Modifier = Modifier,
    navigateToCenterDashboard: () -> Unit = {},
    navigateToTeachingBoard: () -> Unit = {},
    navigateToSetting: () -> Unit = {},
    navigateToEducation: () -> Unit = {},
    navigateToNotice: () -> Unit = {},
    navigateToReady: () -> Unit = {},
    closeDrawer: () -> Unit = {}
) {

    ModalDrawerSheet(modifier = Modifier) {
        DrawerHeader(modifier)
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacer_padding)))
        Divider(thickness = 1.dp, color = Color.LightGray)
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacer_padding)))
        NavigationDrawerItem(
            label = {
                Text(
                    text = stringResource(id = R.string.centerDashboard)
                )
            },
            selected = route == AllDestinations.CENTERDASHBOARD,
            onClick = {
                navigateToCenterDashboard()
                closeDrawer()
            },
            icon = { Icon(painter = painterResource(id = R.drawable.icon_centerboard), contentDescription = null, modifier = Modifier.size(30.dp))},
            shape = MaterialTheme.shapes.small,
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = Color(0xFF0047B3),
                unselectedContainerColor = Color.Transparent,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.Black,
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Black
            )
        )
        NavigationDrawerItem(
            label = {
                Text(
                    text = stringResource(id = R.string.teachingBoard)
                )
            },
            selected = route == AllDestinations.TEACHINGBOARD,
            onClick = {
                navigateToTeachingBoard()
                closeDrawer()
            },
            icon = { Icon(painter = painterResource(R.drawable.icon_teachingboard), contentDescription = null)},
            shape = MaterialTheme.shapes.small,
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = Color(0xFF0047B3),
                unselectedContainerColor = Color.Transparent,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.Black,
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Black
            )
        )
        NavigationDrawerItem(
            label = {
                Text(
                    text = stringResource(id = R.string.education),
                    modifier = Modifier
                        .padding(start = 5.dp)
                )
            },
            selected = route == AllDestinations.EDUCATION,
            onClick = {
                navigateToEducation()
                closeDrawer()
            },
            icon = { Icon(painter = painterResource(id = R.drawable.icon_book), contentDescription = null)},
            shape = MaterialTheme.shapes.small,
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = Color(0xFF0047B3),
                unselectedContainerColor = Color.Transparent,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.Black,
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Black
            )
        )
        NavigationDrawerItem(
            label = {
                Text(
                    text = stringResource(id = R.string.notice),
                    modifier = Modifier
                        .padding(start = 5.dp)
                )
            },
            selected = route == AllDestinations.NOTICE,
            onClick = {
                navigateToNotice()
                closeDrawer()
            },
            icon = { Icon(painter = painterResource(id = R.drawable.icon_calendar), contentDescription = null)},
            shape = MaterialTheme.shapes.small,
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = Color(0xFF0047B3),
                unselectedContainerColor = Color.Transparent,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.Black,
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Black
            )
        )
        NavigationDrawerItem(
            label = {
                Text(
                    text = stringResource(id = R.string.setting),
                    modifier = Modifier
                        .padding(start = 5.dp)
                )
            },
            selected = route == AllDestinations.SETTING,
            onClick = {
                navigateToSetting()
                closeDrawer()
            },
            icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = null)},
            shape = MaterialTheme.shapes.small,
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = Color(0xFF0047B3),
                unselectedContainerColor = Color.Transparent,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.Black,
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Black
            )
        )

    }
}


@Composable
fun DrawerHeader(modifier: Modifier) {
    val context = LocalContext.current


    val authViewModel = LoginState.current
    val userName by authViewModel.userName.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val (changePasswordDialog, setChangePasswordDialog) = rememberSaveable {
        mutableStateOf(false)
    }
    var currentPassword by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var changePassword by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    if (changePasswordDialog){
        Dialog(
            onDismissRequest = {setChangePasswordDialog(false)},
            properties = DialogProperties(usePlatformDefaultWidth = false),
        ){
            Column(
                modifier = Modifier
                    .width(900.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
                    .padding(
                        horizontal = 35.dp,
                        vertical = 30.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ){
                Text(
                    text = "비밀번호 변경",
                    style = TextStyle(
                        fontSize = 33.sp,
                        fontFamily = fontFamily_Inter,
                        fontWeight = FontWeight(400),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    ),
                )
                ChangePasswordDialogTextField(
                    inputName = "현재 비밀번호",
                    inputValue = currentPassword,
                    setInputValue = { currentPassword = it }
                )
                ChangePasswordDialogTextField(
                    inputName = "변경 비밀번호",
                    inputValue = changePassword,
                    setInputValue = { changePassword = it }
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(80.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .height(80.dp),
                        shape = RoundedCornerShape(8.dp),
                        onClick = {
                            setChangePasswordDialog(false)
                            currentPassword = TextFieldValue("")
                            changePassword = TextFieldValue("")
//                        gameMode = ""
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                    ){
                        Text(
                            text = "취소",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontFamily = fontFamily_Poppins,
                                fontWeight = FontWeight(500),
                                color = Color(0xFFFFFFFF),
                            )
                        )
                    }
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .height(80.dp),
                        shape = RoundedCornerShape(8.dp),
                        onClick = {
                            if(currentPassword.text.isNotEmpty() && changePassword.text.isNotEmpty()){
                                //LTO 추가 코드
                                authViewModel.updatePassword(
                                    context = context,
                                    updatePasswordRequest = UpdatePasswordRequest(
                                        beforePassword = currentPassword.text,
                                        afterPassword = changePassword.text
                                    )
                                )
                            } else{
                                Toasty.warning(context, "현재 비밀번호와 변경 비밀번호를 입력해주세요", Toast.LENGTH_SHORT, true).show()
                            }
                            setChangePasswordDialog(false)
                            currentPassword = TextFieldValue("")
                            changePassword = TextFieldValue("")
//                        gameMode = ""
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0047B3))
                    ){
                        Text(
                            text = "변경",
                            style = TextStyle(
                                fontSize = 26.sp,
                                fontFamily = fontFamily_Poppins,
                                fontWeight = FontWeight(500),
                                color = Color(0xFFFFFFFF),
                            )
                        )
                    }
                }

            }
        }
    }

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .background(Color(0xFFF3F3F3))
            .padding(16.dp)
            .fillMaxWidth()
    ){
        Icon(
            painterResource(id = R.drawable.icon_logo),
            contentDescription = null,
            modifier = Modifier
                .width(200.dp)
                .clip(CircleShape),
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacer_padding)))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = userName ?: "",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row {
                Button(
                    onClick = {
                        setChangePasswordDialog(true)
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "비밀번호 변경")
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            authViewModel.logout()
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "로그아웃")

                }
            }

        }

    }
}

@Preview
@Composable
fun DrawerHeaderPreview(){
    AppDrawer(modifier = Modifier, route = AllDestinations.TEACHINGBOARD)
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordDialogTextField(
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
                .width(180.dp)
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

