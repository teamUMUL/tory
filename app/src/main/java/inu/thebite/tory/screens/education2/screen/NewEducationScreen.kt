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
import inu.thebite.tory.screens.education2.viewmodel.DEVViewModel
import inu.thebite.tory.screens.education2.viewmodel.EducationViewModel
import inu.thebite.tory.screens.education2.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education2.viewmodel.STOViewModel
import inu.thebite.tory.screens.game.viewmodel.DragAndDropViewModel
import inu.thebite.tory.screens.game.viewmodel.GameViewModel
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel

@Composable
fun NewEducationScreen(
    devViewModel: DEVViewModel,
    ltoViewModel: LTOViewModel,
    stoViewModel : STOViewModel,
    imageViewModel : ImageViewModel,
    gameViewModel: GameViewModel,
    dragAndDropViewModel: DragAndDropViewModel,
    educationViewModel : EducationViewModel
){
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        DEVItemRow(
            devViewModel = devViewModel,
            ltoViewModel = ltoViewModel
        )
        Divider(thickness = 4.dp, color = MaterialTheme.colorScheme.tertiary)
        LTOAndSTOContainer(
            context = context,
            devViewModel = devViewModel,
            ltoViewModel = ltoViewModel,
            educationViewModel = educationViewModel,
            stoViewModel =  stoViewModel,
            imageViewModel = imageViewModel,
            dragAndDropViewModel = dragAndDropViewModel,
            gameViewModel = gameViewModel
        )
    }

}




