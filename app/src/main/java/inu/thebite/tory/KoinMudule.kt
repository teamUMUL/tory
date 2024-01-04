package inu.thebite.tory


import inu.thebite.tory.schedule.TodoViewModel
import inu.thebite.tory.screens.auth.AuthViewModel
import inu.thebite.tory.screens.auth.TokenExpirationInterceptor
import inu.thebite.tory.screens.auth.TokenManager
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
import inu.thebite.tory.screens.setting.viewmodel.CenterViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildClassViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildInfoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { TokenManager(androidContext()) }
    single { TokenExpirationInterceptor(androidContext()) }

    viewModel<CenterSelectViewModel> {
        CenterSelectViewModel()
    }
    viewModel<ChildClassSelectViewModel> {
        ChildClassSelectViewModel()
    }
    viewModel<ChildSelectViewModel> {
        ChildSelectViewModel()
    }
    viewModel<CenterViewModel> {
        CenterViewModel()
    }
    viewModel<ChildClassViewModel> {
        ChildClassViewModel()
    }
    viewModel<ChildInfoViewModel> {
        ChildInfoViewModel()
    }
    viewModel<DEVViewModel>{
        DEVViewModel()
    }
    viewModel<LTOViewModel>{
        LTOViewModel()
    }

    viewModel<STOViewModel>{
        STOViewModel()
    }
    viewModel<ImageViewModel>{
        ImageViewModel()
    }
    viewModel<DragAndDropViewModel>{
        DragAndDropViewModel()
    }
    viewModel<GameViewModel>{
        GameViewModel()
    }
    viewModel<NoticeViewModel> {
        NoticeViewModel()
    }
    viewModel<TodoViewModel> {
        TodoViewModel()
    }
    viewModel<AuthViewModel> {
        AuthViewModel(get())
    }
}