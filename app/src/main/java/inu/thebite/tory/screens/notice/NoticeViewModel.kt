package inu.thebite.tory.screens.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.model.sto.StoResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoticeViewModel : ViewModel() {

    private val _selectedNoticeDates: MutableStateFlow<List<NoticeDate>?> = MutableStateFlow(null)
    val selectedNoticeDates = _selectedNoticeDates.asStateFlow()

    fun setSelectedNoticeDates(
        allDates: List<NoticeDate>,
        selectedYear: String,
        selectedMonth: String
    ) {
        viewModelScope.launch {
            val result = allDates.filter {
                it.year == selectedYear &&
                it.month == selectedMonth
            }.sortedByDescending { it.date.toInt() }

            _selectedNoticeDates.update {
                result
            }
        }

    }
}