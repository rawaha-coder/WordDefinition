package com.rawahacoder.worddefinition.service

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface DictionaryService {

    @GET("entries/en/{word}")
    suspend fun searchWordDefinition(@Path("word") word: String): Response<List<ResultResponse>>

    companion object {

        val instance: DictionaryService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.dictionaryapi.dev/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(DictionaryService::class.java)
        }
    }
}