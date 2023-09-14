package inu.thebite.tory.screens.navigation

import androidx.navigation.NavController
import inu.thebite.tory.screens.navigation.AllDestinations.DATA
import inu.thebite.tory.screens.navigation.AllDestinations.GAME
import inu.thebite.tory.screens.navigation.AllDestinations.HOME


object AllDestinations{
    const val HOME = "Home"
    const val GAME = "Game"
    const val DATA = "Data"
}

class AppNavigationActions(private val navController: NavController) {

    fun navigateToHome(){
        navController.navigate(HOME){
            popUpTo(HOME)
        }
    }

    fun navigateToGame(){
        navController.navigate(GAME){
            launchSingleTop = true
            restoreState = true
        }

    }
    fun navigateToData(){
        navController.navigate(DATA){
            launchSingleTop = true
            restoreState = true
        }

    }

}