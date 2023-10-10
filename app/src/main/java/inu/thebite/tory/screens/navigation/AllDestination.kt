package inu.thebite.tory.screens.navigation

import androidx.navigation.NavController
import inu.thebite.tory.screens.navigation.AllDestinations.EDUCATION
import inu.thebite.tory.screens.navigation.AllDestinations.HOME
import inu.thebite.tory.screens.navigation.AllDestinations.SETTING


object AllDestinations{
    const val HOME = "Home"
    const val SETTING = "Setting"
    const val EDUCATION = "Education"
    const val READY = "READY"
}

class AppNavigationActions(private val navController: NavController) {

    fun navigateToHome(){
        navController.navigate(HOME){
            popUpTo(HOME)
        }
    }

    fun navigateToGame(){
        navController.navigate(SETTING){
            launchSingleTop = true
            restoreState = true
        }

    }
    fun navigateToData(){
        navController.navigate(EDUCATION){
            launchSingleTop = true
            restoreState = true
        }

    }

}