package com.example.androidtestfe.data.network

import com.example.androidtestfe.data.network.model.ListModel
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("photos")
    fun getData() : Response<ListModel>
}