package inu.thebite.tory

//import inu.thebite.tory.screens.education.GameViewModel
//import inu.thebite.tory.screens.education.LTOViewModel
//import inu.thebite.tory.screens.education.STOViewModel
//import inu.thebite.tory.screens.game.DragAndDropViewModel
import inu.thebite.tory.screens.setting.viewmodel.CenterViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildClassViewModel
import inu.thebite.tory.screens.setting.viewmodel.ChildInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
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
//    viewModel<GameViewModel> {
//        GameViewModel()
//    }
//    viewModel<LTOViewModel> {
//        LTOViewModel()
//    }
//    viewModel<STOViewModel> {
//        STOViewModel()
//    }
//    viewModel<DragAndDropViewModel> {
//        DragAndDropViewModel()
//    }


}