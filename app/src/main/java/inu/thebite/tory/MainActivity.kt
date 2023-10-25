package inu.thebite.tory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import inu.thebite.tory.screens.HomeScreen
import inu.thebite.tory.screens.education2.viewmodel.DEVViewModel
import inu.thebite.tory.screens.education2.viewmodel.EducationViewModel
import inu.thebite.tory.screens.education2.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education2.viewmodel.STOViewModel
import inu.thebite.tory.screens.game.viewmodel.DragAndDropViewModel
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel
//import inu.thebite.tory.screens.education.GameViewModel
//import inu.thebite.tory.screens.education.LTOViewModel
//import inu.thebite.tory.screens.education.STOViewModel
//import inu.thebite.tory.screens.game.DragAndDropViewModel
import inu.thebite.tory.screens.setting.viewmodel.CenterViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildClassViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildInfoViewModel
import inu.thebite.tory.ui.theme.ToryTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToryTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    val ltoViewModel : LTOViewModel = koinViewModel()
                    val centerSelectViewModel : CenterSelectViewModel = koinViewModel()
                    val childClassSelectViewModel : ChildClassSelectViewModel = koinViewModel()
                    val childSelectViewModel : ChildSelectViewModel = koinViewModel()
//                    val stoViewModel : STOViewModel = koinViewModel()
                    val centerViewModel : CenterViewModel = koinViewModel()
                    val childClassViewModel : ChildClassViewModel = koinViewModel()
                    val childInfoViewModel : ChildInfoViewModel = koinViewModel()
                    val devViewModel : DEVViewModel = koinViewModel()
                    val ltoViewModel : LTOViewModel = koinViewModel()
                    val educationViewModel : EducationViewModel = koinViewModel()
                    val stoViewModel : STOViewModel = koinViewModel()
                    val imageViewModel : ImageViewModel = koinViewModel()
                    val dragAndDropViewModel: DragAndDropViewModel = koinViewModel()

//                    val dragAndDropViewModel : DragAndDropViewModel = koinViewModel()
//                    val gameViewModel : GameViewModel = koinViewModel()
                    MainCompose(
//                        ltoViewModel = ltoViewModel,
                        centerSelectViewModel = centerSelectViewModel,
                        childClassSelectViewModel = childClassSelectViewModel,
                        childSelectViewModel = childSelectViewModel,
//                        stoViewModel = stoViewModel,
                        centerViewModel = centerViewModel,
                        childClassViewModel = childClassViewModel,
                        childInfoViewModel = childInfoViewModel,
                        devViewModel = devViewModel,
                        ltoViewModel = ltoViewModel,
                        educationViewModel = educationViewModel,
                        stoViewModel = stoViewModel,
                        imageViewModel = imageViewModel,
                        dragAndDropViewModel = dragAndDropViewModel
//                        dragAndDropViewModel = dragAndDropViewModel,
//                        gameViewModel = gameViewModel
                    )
                }
            }
        }
    }
}
