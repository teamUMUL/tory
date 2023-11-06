package inu.thebite.tory

//import inu.thebite.tory.screens.education.GameViewModel
//import inu.thebite.tory.screens.education.LTOViewModel
//import inu.thebite.tory.screens.education.STOViewModel
//import inu.thebite.tory.screens.game.DragAndDropViewModel
import inu.thebite.tory.screens.education2.viewmodel.DEVViewModel
import inu.thebite.tory.screens.education2.viewmodel.EducationViewModel
import inu.thebite.tory.screens.education2.viewmodel.LTOViewModel
import inu.thebite.tory.screens.education2.viewmodel.STOViewModel
import inu.thebite.tory.screens.game.viewmodel.DragAndDropViewModel
import inu.thebite.tory.screens.game.viewmodel.GameViewModel
import inu.thebite.tory.screens.teachingboard.viewmodel.CenterSelectViewModel
import inu.thebite.tory.screens.teachingboard.viewmodel.ChildClassSelectViewModel
import inu.thebite.tory.screens.teachingboard.viewmodel.ChildSelectViewModel
import inu.thebite.tory.screens.ready.viewmodel.ImageViewModel
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
    viewModel<DEVViewModel>{
        DEVViewModel()
    }
    viewModel<LTOViewModel>{
        LTOViewModel()
    }
    viewModel<EducationViewModel>{
        EducationViewModel()
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