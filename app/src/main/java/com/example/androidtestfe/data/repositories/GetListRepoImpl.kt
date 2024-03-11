package com.example.androidtestfe.data.repositories

import com.example.androidtestfe.data.network.Api
import com.example.androidtestfe.data.network.model.ListModel
import javax.inject.Inject

class GetListRepoImpl @Inject constructor(private val services: Api) : GetListRepo {
    override suspend fun getList(page: Int, limit: Int): ListModel {

        val response = services.getData(page, limit)
        val result = response.body()
        if (response.isSuccessful && result != null) {
            return result
        }
        return ListModel()
    }
}