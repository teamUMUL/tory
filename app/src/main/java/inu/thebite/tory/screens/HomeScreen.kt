package inu.thebite.tory.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
        homeViewModel: HomeViewModel = viewModel(),
        ltoViewModel: LTOViewModel = viewModel(),
        stoViewModel: STOViewModel = viewModel()
){

    val gameResults by homeViewModel.games.collectAsState()

    val (dialogOpen, setDialogOpen) = remember {
        mutableStateOf(false)
    }


}