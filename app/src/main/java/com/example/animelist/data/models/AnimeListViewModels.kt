package com.example.animelist.data.models

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.example.animelist.util.ApiBaseUrl.PAGE_SIZE
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.animelist.repository.AnimeRepository
import com.example.animelist.util.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModels @Inject constructor(
    private val repository: AnimeRepository
): ViewModel() {

    private var currentPage = 0

    var animeList = mutableStateOf<List<AnimeListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    init {
        loadAnimePaginated()
    }

    fun loadAnimePaginated(){
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getAnimeList(PAGE_SIZE)
            when(result){
                is ResponseHandler.Success -> {
                    endReached.value = currentPage * PAGE_SIZE >= result.data!!.meta.count

                    val animeEntries = result.data.data.mapIndexed { _, data ->
                        val name = data.attributes.slug
                        val image = data.attributes.posterImage.original
                        val number = data.id

                        AnimeListEntry(
                            name,
                            image,
                            number.toInt(),
                            )
                    }

                    currentPage++
                    loadError.value = ""
                    isLoading.value = false
                    animeList.value += animeEntries
                }
                is ResponseHandler.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }

        }
    }

    /*This fun for calculate the dominant color in the image*/
    /*Helping to Generate the background color*/
    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}