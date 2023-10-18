package inu.thebite.tory.screens.education2.compose.lto

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import inu.thebite.tory.screens.education2.compose.sto.STOItemColumn

@Composable
fun LTOAndSTOContainer(
    context : Context
){
    Row(modifier = Modifier.fillMaxSize()) {
        LTOItemColumn(
            context = context
        )
        STOItemColumn()
    }
}