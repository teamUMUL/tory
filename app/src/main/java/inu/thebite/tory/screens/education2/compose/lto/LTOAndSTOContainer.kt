package inu.thebite.tory.screens.education2.compose.lto

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import inu.thebite.tory.screens.education2.compose.sto.STOItemColumn
import inu.thebite.tory.screens.education2.viewmodel.DEVViewModel
import inu.thebite.tory.screens.education2.viewmodel.LTOViewModel

@Composable
fun LTOAndSTOContainer(
    context : Context,
    devViewModel: DEVViewModel,
    ltoViewModel: LTOViewModel
){
    Row(modifier = Modifier.fillMaxSize()) {
        LTOItemColumn(
            context = context,
            devViewModel = devViewModel,
            ltoViewModel = ltoViewModel
        )
        STOItemColumn(
            ltoViewModel = ltoViewModel
        )
    }
}