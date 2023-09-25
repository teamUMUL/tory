package inu.thebite.tory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChildViewModel : ViewModel(){
    private val _selectedChildName = MutableLiveData<String>("오전1")
    private val _selectedChildClass = MutableLiveData<String>("오전반(월수금)")

    // Expose LiveData for observing changes
    val selectedChildName: LiveData<String>
        get() = _selectedChildName

    val selectedChildClass: LiveData<String>
        get() = _selectedChildClass

    // Setters to update the LiveData values
    fun setSelectedChildName(name: String) {
        Log.e("setSelectedChildName", name)
        _selectedChildName.value = name
    }

    fun setSelectedChildClass(className: String) {
        Log.e("setSelectedChildClass", className)
        _selectedChildClass.value = className
    }
}