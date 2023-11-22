package inu.thebite.tory.screens.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import inu.thebite.tory.R

@OptIn(ExperimentalMaterial3Api::class)
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
            shape = MaterialTheme.shapes.small
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
            shape = MaterialTheme.shapes.small
        )
        NavigationDrawerItem(
            label = {
                Text(
                    text = stringResource(id = R.string.education),
                )
            },
            selected = route == AllDestinations.EDUCATION,
            onClick = {
                navigateToEducation()
                closeDrawer()
            },
            icon = { Icon(painter = painterResource(id = R.drawable.icon_book), contentDescription = null)},
            shape = MaterialTheme.shapes.small
        )
        NavigationDrawerItem(
            label = {
                Text(
                    text = stringResource(id = R.string.notice),
                )
            },
            selected = route == AllDestinations.NOTICE,
            onClick = {
                navigateToNotice()
                closeDrawer()
            },
            icon = { Icon(painter = painterResource(id = R.drawable.icon_calendar), contentDescription = null)},
            shape = MaterialTheme.shapes.small
        )
        NavigationDrawerItem(
            label = {
                Text(
                    text = stringResource(id = R.string.ready),
                )
            },
            selected = route == AllDestinations.READY,
            onClick = {
                navigateToReady()
                closeDrawer()
            },
            icon = { Icon(painter = painterResource(id = R.drawable.icon_photo), contentDescription = null)},
            shape = MaterialTheme.shapes.small
        )
        NavigationDrawerItem(
            label = {
                Text(
                    text = stringResource(id = R.string.setting),
                )
            },
            selected = route == AllDestinations.SETTING,
            onClick = {
                navigateToSetting()
                closeDrawer()
            },
            icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = null)},
            shape = MaterialTheme.shapes.small
        )

    }
}


@Composable
fun DrawerHeader(modifier: Modifier) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp)
            .fillMaxWidth()
    ){
        Icon(
            imageVector = Icons.Default.Person, 
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.header_image_size))
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacer_padding)))
        Text(
            text = stringResource(id = R.string.app_name),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
fun DrawerHeaderPreview(){
    AppDrawer(modifier = Modifier, route = AllDestinations.TEACHINGBOARD)
}




