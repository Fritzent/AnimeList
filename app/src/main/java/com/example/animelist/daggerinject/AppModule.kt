package com.example.animelist.daggerinject

import com.example.animelist.data.remote.AnimeApi
import com.example.animelist.repository.AnimeRepository
import com.example.animelist.util.ApiBaseUrl.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
/*SingletonComponent*/
/*Make the module still live as long as the app live*/
@InstallIn(SingletonComponent::class)
object AppModule {

    /*This is to Provide the Anime Repository Request*/
    @Singleton
    @Provides
    fun provideAnimeRepository(
        api: AnimeApi
    ) = AnimeRepository(api)

    /*This is to handle the Api Service Request*/
    @Singleton
    @Provides
    fun provideAnimeApi(): AnimeApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(AnimeApi::class.java)
    }
}