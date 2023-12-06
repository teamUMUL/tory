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

    private val _allCategories: MutableStateFlow<List<String>?> = MutableStateFlow(null)
    val allCategories = _allCategories.asStateFlow()
    init {
        getAllImages()
        getAllCategories()
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

    fun getAllCategories(){
        viewModelScope.launch {
            try {
                val allImages = repo.getAllImages()
                val allCategoryList = mutableListOf<String>()
                allImages.forEach { image ->
                    allCategoryList.add(image.category.name)
                }
                val allUniqueCategory = allCategoryList.distinct()
                _allCategories.value = allUniqueCategory
            } catch (e : Exception){
                Log.e("failed to get all Categories", e.message.toString())
            }
        }
    }

    fun getImagesByCategory(category : String): List<ImageResponse> {
        val filteredImages = allImages.value!!.filter { image ->
            image.category.name == category
        }
        return filteredImages
    }

    fun findImageByName(imageName : String) : ImageResponse? {
        val returnImage = allImages.value?.find {
            it.name == imageName
        }
        return returnImage
    }

    fun findImagesByNames(imageNames : List<String>?) : List<ImageResponse>{
        val returnImages = mutableListOf<ImageResponse>()
        imageNames?.let {imageNames ->
            for (imageName in imageNames){
                findImageByName(imageName)?.let {
                    returnImages.add(it)
                }
            }
        }

        return returnImages
    }

}