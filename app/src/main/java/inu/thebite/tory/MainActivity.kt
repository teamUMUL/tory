package inu.thebite.tory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import inu.thebite.tory.schedule.TodoViewModel
import inu.thebite.tory.screens.auth.AuthViewModel
import inu.thebite.tory.screens.auth.LoginState
import inu.thebite.tory.screens.auth.TokenExpirationEvent
import inu.thebite.tory.screens.auth.TokenManager
import inu.thebite.tory.screens.auth.login.LoginScreen
import inu.thebite.tory.screens.education.viewmodel.DEVViewModel
import inu.thebite.tory.screens.education.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education.viewmodel.STOViewModel
import inu.thebite.tory.screens.game.viewmodel.DragAndDropViewModel
import inu.thebite.tory.screens.game.viewmodel.GameViewModel
import inu.thebite.tory.screens.notice.NoticeViewModel
import inu.thebite.tory.screens.teachingboard.viewmodel.CenterSelectViewModel
import inu.thebite.tory.screens.teachingboard.viewmodel.ChildClassSelectViewModel
import inu.thebite.tory.screens.teachingboard.viewmodel.ChildSelectViewModel
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
    private lateinit var tokenManager: TokenManager
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tokenManager = TokenManager(this)

        setContent {
            ToryTheme {
                val context = LocalContext.current
                val authViewModel: AuthViewModel = koinViewModel()
                val token = tokenManager.getToken()
                if (token != null){
                    authViewModel.verifyToken(context = context)
                }

                CompositionLocalProvider(LoginState provides authViewModel){
                    ApplicationSwitcher(tokenManager = tokenManager)
                }
            }
        }
    }
}


@Composable
fun ApplicationSwitcher(tokenManager: TokenManager) {
    val tokenExpired = TokenExpirationEvent.expired.observeAsState()
    val vm = LoginState.current
    val loginState by vm.loginState.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (loginState && tokenExpired.value == false) {
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
            val stoViewModel : STOViewModel = koinViewModel()
            val imageViewModel : ImageViewModel = koinViewModel()
            val dragAndDropViewModel: DragAndDropViewModel = koinViewModel()
            val gameViewModel : GameViewModel = koinViewModel()
            val noticeViewModel : NoticeViewModel = koinViewModel()
            val todoViewModel : TodoViewModel = koinViewModel()
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
                stoViewModel = stoViewModel,
                imageViewModel = imageViewModel,
                dragAndDropViewModel = dragAndDropViewModel,
                gameViewModel = gameViewModel,
                noticeViewModel = noticeViewModel,
                todoViewModel = todoViewModel
//                        dragAndDropViewModel = dragAndDropViewModel,
//                        gameViewModel = gameViewModel
            )
        } else {
            LoginScreen()
        }
    }

}
