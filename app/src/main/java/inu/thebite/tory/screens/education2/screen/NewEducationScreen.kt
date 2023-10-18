package inu.thebite.tory.screens.education2.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import inu.thebite.tory.screens.education2.compose.dev.DEVItemRow
import inu.thebite.tory.screens.education2.compose.lto.LTOAndSTOContainer

@Composable
fun NewEducationScreen(

){
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        DEVItemRow()
        Divider(thickness = 4.dp, color = MaterialTheme.colorScheme.tertiary)
        LTOAndSTOContainer(
            context = context
        )
    }

}




