package inu.thebite.tory.screens.education.compose.sidebar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import inu.thebite.tory.R
import inu.thebite.tory.screens.education.viewmodel.DEVViewModel
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education.viewmodel.STOViewModel

@Composable
fun Sidebar(
    devViewModel: DEVViewModel,
    ltoViewModel: LTOViewModel,
    stoViewModel: STOViewModel
){
    val purpleGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF0047B3), Color(0xFF7F5AF0))
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = Modifier
                .padding(top = 30.dp),
            onClick = {}
        ) {
            Icon(painter = painterResource(id = R.drawable.icon_dev_add), contentDescription = null, tint = Color(0xFF8E8E8E))
        }
        IconButton(
            onClick = {}
        ) {
            Icon(painter = painterResource(id = R.drawable.icon_dev_update), contentDescription = null, tint = Color(0xFF8E8E8E))
        }
        IconButton(
            onClick = {}
        ) {
            Icon(painter = painterResource(id = R.drawable.icon_lto_add), contentDescription = null, tint = Color(0xFF8E8E8E))
        }
        IconButton(
            onClick = {}
        ) {
            Icon(painter = painterResource(id = R.drawable.icon_lto_update), contentDescription = null, tint = Color(0xFF8E8E8E))
        }
        IconButton(
            onClick = {}
        ) {
            Icon(painter = painterResource(id = R.drawable.icon_sto_add), contentDescription = null, tint = Color(0xFF8E8E8E))
        }
        IconButton(
            onClick = {}
        ) {
            Icon(painter = painterResource(id = R.drawable.icon_sto_update), contentDescription = null, tint = Color(0xFF8E8E8E))
        }
        IconButton(
            onClick = {}
        ) {
            Icon(painter = painterResource(id = R.drawable.icon_todo), contentDescription = null, tint = Color.Unspecified)
        }
    }

}