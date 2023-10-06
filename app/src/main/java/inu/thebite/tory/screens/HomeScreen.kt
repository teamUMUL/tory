package inu.thebite.tory.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import inu.thebite.tory.screens.datascreen.LTOViewModel
//import inu.thebite.tory.screens.DataScreen.STOViewModel

@Composable
fun HomeScreen(
        homeViewModel: HomeViewModel = viewModel(),
){
    val ltoViewModel : LTOViewModel = viewModel()
//    val stoViewModel : STOViewModel = viewModel()

    val (dialogOpen, setDialogOpen) = remember {
        mutableStateOf(false)
    }


}