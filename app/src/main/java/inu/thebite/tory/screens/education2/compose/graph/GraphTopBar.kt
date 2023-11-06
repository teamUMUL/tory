package inu.thebite.tory.screens.education2.compose.graph

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import inu.thebite.tory.R

@Composable
fun GraphTopBar(
    setIsLTOGraphOn : (Boolean) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(60.dp)
                .padding(10.dp)
                .clickable {
                    setIsLTOGraphOn(false)
                },
            painter = painterResource(id = R.drawable.icon_back),
            contentDescription = null
        )
    }
}