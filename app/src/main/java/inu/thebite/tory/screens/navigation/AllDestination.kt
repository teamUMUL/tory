package inu.thebite.tory.screens.navigation

import androidx.navigation.NavController
import inu.thebite.tory.screens.navigation.AllDestinations.CENTERHOME
import inu.thebite.tory.screens.navigation.AllDestinations.EDUCATION
import inu.thebite.tory.screens.navigation.AllDestinations.HOME
import inu.thebite.tory.screens.navigation.AllDestinations.READY
import inu.thebite.tory.screens.navigation.AllDestinations.SETTING


object AllDestinations{
    const val CENTERHOME = "CenterHome"
    const val HOME = "Home"
    const val SETTING = "Setting"
    const val EDUCATION = "Education"
    const val READY = "READY"
}

class AppNavigationActions(private val navController: NavController) {

    fun navigateToCenterHome(){
        navController.navigate(CENTERHOME){
            popUpTo(CENTERHOME)
        }
    }
    fun navigateToHome(){
        navController.navigate(HOME){
            launchSingleTop = true
            restoreState = true
        }

    }

    fun navigateToSetting(){
        navController.navigate(SETTING){
            launchSingleTop = true
            restoreState = true
        }

    }
    fun navigateToEducation(){
        navController.navigate(EDUCATION){
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToReady(){
        navController.navigate(READY){
            launchSingleTop = true
            restoreState = true
        }
    }

}