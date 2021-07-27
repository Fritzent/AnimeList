package com.example.animelist.data.remote

import com.example.animelist.data.remote.responses.Anime
import com.example.animelist.data.remote.responses.AnimeList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApi {
//    https://kitsu.io/api/edge/anime?page[limit]=5&page[offset]=0
    @GET("anime")
    suspend fun getAnimeList(
        @Query("page[limit]") limit:Int
    ): AnimeList
    @GET("anime/{anime_id}")
    suspend fun getAnimeDetails(
        @Path("anime_id") name:String
    ): Anime
}