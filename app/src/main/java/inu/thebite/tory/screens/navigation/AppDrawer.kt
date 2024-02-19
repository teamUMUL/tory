package inu.thebite.tory.screens.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import inu.thebite.tory.R
import inu.thebite.tory.screens.auth.LoginState
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
    val authViewModel = LoginState.current
    val userName by authViewModel.userName.collectAsState()
    val coroutineScope = rememberCoroutineScope()
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
                color = MaterialTheme.colorScheme.onPrimary
            )
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

@Preview
@Composable
fun DrawerHeaderPreview(){
    AppDrawer(modifier = Modifier, route = AllDestinations.TEACHINGBOARD)
}




