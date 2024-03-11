package com.example.androidtestfe.data.network

import com.example.androidtestfe.data.network.model.ListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("/photos")
    suspend fun getData(
        @Query("page") page : Int,
        @Query("limit") limit: Int
    ) : Response<ListModel>
}