package com.example.androidtestfe.usecases

import com.example.androidtestfe.data.network.model.ListModelItem
import com.example.androidtestfe.data.repositories.GetListRepo
import javax.inject.Inject

class GetListDataUseCase @Inject constructor(private val repo: GetListRepo) {

   suspend fun getListData(page: Int, limit: Int) : List<ListModelItem> {
        return repo.getList(page, limit)
    }
}