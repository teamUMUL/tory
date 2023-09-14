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
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel : ViewModel(), KoinComponent {
    private val repo: ChildRepo by inject()

    private val _child: MutableStateFlow<List<ChildEntity>> = MutableStateFlow(emptyList())
    val child = _child.asStateFlow()

    var selectedChildName : String = ""
    var selectedChildClass : String = ""

    init{
        getChild()
    }

    private fun getChild(){
        viewModelScope.launch(Dispatchers.IO){
            repo.getChild().collect { data ->
                _child.update { data }
            }
        }
    }

    fun deleteChild(child: ChildEntity){
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteChild(child)
        }
    }

    fun addChild(child: ChildEntity){
        viewModelScope.launch(Dispatchers.IO){
            repo.addChild(child)
        }
    }

    fun updateChild(child: ChildEntity){
        viewModelScope.launch(Dispatchers.IO){
            repo.updateChild(child)
        }
    }
}