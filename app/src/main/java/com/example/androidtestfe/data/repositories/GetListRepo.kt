package com.example.androidtestfe.data.repositories

import com.example.androidtestfe.data.network.model.ListModel

interface GetListRepo {
   suspend fun getList(page: Int, limit: Int) : ListModel
}