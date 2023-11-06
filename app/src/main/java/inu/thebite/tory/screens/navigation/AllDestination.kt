package inu.thebite.tory.screens.navigation

import androidx.navigation.NavController
import inu.thebite.tory.screens.navigation.AllDestinations.CENTERDASHBOARD
import inu.thebite.tory.screens.navigation.AllDestinations.EDUCATION
import inu.thebite.tory.screens.navigation.AllDestinations.TEACHINGBOARD
import inu.thebite.tory.screens.navigation.AllDestinations.READY
import inu.thebite.tory.screens.navigation.AllDestinations.SETTING


object AllDestinations{
    const val CENTERDASHBOARD = "CenterDashboard"
    const val TEACHINGBOARD = "TeachingBoard"
    const val SETTING = "Setting"
    const val EDUCATION = "Education"
    const val READY = "READY"
}

class AppNavigationActions(private val navController: NavController) {

    fun navigateToCenterDashboard(){
        navController.navigate(CENTERDASHBOARD){
            popUpTo(CENTERDASHBOARD)
        }
    }
    fun navigateToTeachingBoard(){
        navController.navigate(TEACHINGBOARD){
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