package inu.thebite.tory.screens.DataScreen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.util.Date


data class Graph(
    val className: String,
    val childName: String,
    val selectedDEV: String,
    val selectedLTO: String,
    val selectedSTO: String,
    val date: List<LocalDate>,
    val plusRatio: List<Float>,
    val minusRatio: List<Float>
)

class GraphViewModel : ViewModel() {
    private val _graph = mutableStateListOf<Graph>()

    val graph: List<Graph>
        get() = _graph

    fun addGraph(
        className: String,
        childName: String,
        selectedDEV: String,
        selectedLTO: String,
        selectedSTO: String,
        date: List<LocalDate>,
        plusRatio: List<Float>,
        minusRatio: List<Float>
    ) {
        val graph = Graph(
            className = className,
            childName = childName,
            selectedDEV = selectedDEV,
            selectedLTO = selectedLTO,
            selectedSTO = selectedSTO,
            date = date,
            plusRatio = plusRatio,
            minusRatio = minusRatio
        )

        _graph.add(graph)
    }

    // Create 또는 Update
    fun addOrUpdateGraph(
        className: String,
        childName: String,
        selectedDEV: String,
        selectedLTO: String,
        selectedSTO: String,
        date: LocalDate,
        plusRatio: Float,
        minusRatio: Float
    ) {
        val matchingGraph = _graph.find {
            it.selectedDEV == selectedDEV &&
                    it.selectedLTO == selectedLTO &&
                    it.selectedSTO == selectedSTO &&
                    it.className == className &&
                    it.childName == childName
        }
        Log.e("들어온 값", "들어온 값: selectedDEV=$selectedDEV, selectedLTO=$selectedLTO, selectedSTO=$selectedSTO, className=$className, childName=$childName, date=$date, plusRatio=$plusRatio, minusRatio=$minusRatio")
        if (matchingGraph != null) {
            // 이미 데이터가 존재하면 업데이트
            val updatedDate = matchingGraph.date.toMutableList()
            updatedDate.add(date)

            val updatedPlusRatio = matchingGraph.plusRatio.toMutableList()
            updatedPlusRatio.add(plusRatio)

            val updatedMinusRatio = matchingGraph.minusRatio.toMutableList()
            updatedMinusRatio.add(minusRatio)

            val updatedGraph = matchingGraph.copy(
                date = updatedDate,
                plusRatio = updatedPlusRatio,
                minusRatio = updatedMinusRatio
            )

            updateGraph(matchingGraph, updatedGraph)
        } else {
            // 데이터가 존재하지 않으면 추가
            addGraph(
                className = className,
                childName = childName,
                selectedDEV = selectedDEV,
                selectedLTO = selectedLTO,
                selectedSTO = selectedSTO,
                date = listOf(date),
                plusRatio = listOf(plusRatio),
                minusRatio = listOf(minusRatio)
            )
        }
    }

    // Read (조회)
    fun getGraph(selectedDEV: String, selectedLTO: String, selectedSTO: String, className: String, childName: String): Graph? {
        val matchingGraph = _graph.find {
            it.selectedDEV == selectedDEV &&
            it.selectedLTO == selectedLTO &&
            it.selectedSTO == selectedSTO &&
            it.className == className &&
            it.childName == childName
        }
        return matchingGraph
//        return matchingGraph ?: throw IllegalArgumentException(
//            "그래프 발견 안됨: selectedDEV=$selectedDEV, selectedLTO=$selectedLTO, selectedSTO=$selectedSTO, className=$className, childName=$childName"
//        ).also {
//            // 로그에 예외 내용을 출력합니다.
//            Log.e("GraphViewModel", "그래프 발견 안됨: selectedDEV=$selectedDEV, selectedLTO=$selectedLTO, selectedSTO=$selectedSTO, className=$className, childName=$childName")
//        }
    }
    fun updateSelectedLTO(graphToUpdate: Graph, newSelectedLTO: String) {
        val updatedGraph = graphToUpdate.copy(selectedLTO = newSelectedLTO)
        updateGraph(graphToUpdate, updatedGraph)
    }

    // Update selectedSTO for a specific Graph
    fun updateSelectedSTO(graphToUpdate: Graph, newSelectedSTO: String) {
        val updatedGraph = graphToUpdate.copy(selectedSTO = newSelectedSTO)
        updateGraph(graphToUpdate, updatedGraph)
    }

    // Update (수정)
    fun updateGraph(graphBeforeUpdate: Graph,graphToUpdate: Graph) {
        val index = _graph.indexOfFirst { it == graphBeforeUpdate }
        if (index != -1) {
            _graph[index] = graphToUpdate
        } else {
            Log.e("GraphViewModel", "수정할 그래프를 찾을 수 없음")
        }
    }

    // Delete (삭제)
    fun deleteGraph(graphToDelete: Graph) {
        _graph.remove(graphToDelete)
    }


}