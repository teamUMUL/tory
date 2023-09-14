package inu.thebite.tory.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.database.GameEntity
import inu.thebite.tory.repositories.GameRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel: ViewModel(), KoinComponent {
    private val repo: GameRepo by inject()

    private val _games:MutableStateFlow<List<GameEntity>> = MutableStateFlow(emptyList())
    val games = _games.asStateFlow()

    init{
        getGameResults()
    }

    private fun getGameResults(){
        viewModelScope.launch(Dispatchers.IO){
            repo.getGameResults().collect { data ->
                _games.update { data }
            }
        }
    }

    fun deleteGameResult(game:GameEntity){
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteGameResult(game)
        }
    }

    fun addGameResult(game:GameEntity){
        viewModelScope.launch(Dispatchers.IO){
            repo.addGameResult(game)
        }
    }

    fun updateGameResult(game:GameEntity){
        viewModelScope.launch(Dispatchers.IO){
            repo.updateGameResult(game)
        }
    }

}