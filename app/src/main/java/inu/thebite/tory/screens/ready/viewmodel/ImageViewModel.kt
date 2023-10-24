package inu.thebite.tory.screens.ready.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import inu.thebite.tory.model.image.ImageResponse
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.repositories.LTO.LTORepoImpl
import inu.thebite.tory.repositories.image.ImageRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ImageViewModel : ViewModel() {
    private val repo: ImageRepoImpl = ImageRepoImpl()


    private val _allImages: MutableStateFlow<List<ImageResponse>?> = MutableStateFlow(null)
    val allImages = _allImages.asStateFlow()

    init {
        getAllImages()
    }

    fun getAllImages(){
        viewModelScope.launch {
            try {
                val allImages = repo.getAllImages()
                _allImages.value = allImages
            } catch (e: Exception) {
                Log.e("failed to get all Images", e.message.toString())
            }
        }
    }
}