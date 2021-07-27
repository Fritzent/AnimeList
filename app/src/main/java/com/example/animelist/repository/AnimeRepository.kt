package com.example.animelist.repository

import com.example.animelist.data.remote.AnimeApi
import com.example.animelist.data.remote.responses.Anime
import com.example.animelist.data.remote.responses.AnimeList
import com.example.animelist.util.ResponseHandler
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class AnimeRepository @Inject constructor(
    private val api: AnimeApi
) {
    suspend fun getAnimeList(limit: Int): ResponseHandler<AnimeList> {
        val response = try {
            api.getAnimeList(limit)
        }catch (e: Exception){
            return ResponseHandler.Error("An unknown error occured")
        }
        return ResponseHandler.Success(response)
    }
    suspend fun getAnimeDetails(animeId: String): ResponseHandler<Anime>{
        val response = try {
            api.getAnimeDetails(animeId)
        }catch (e: Exception){
            return ResponseHandler.Error("An unknown error occured")
        }
        return ResponseHandler.Success(response)
    }
}