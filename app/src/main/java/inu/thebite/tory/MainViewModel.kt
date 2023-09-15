package inu.thebite.tory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.database.ChildEntity
import inu.thebite.tory.database.GameEntity
import inu.thebite.tory.repositories.ChildRepo
import inu.thebite.tory.repositories.GameRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    var selectedChildName : String = ""
    var selectedChildClass : String = ""


}