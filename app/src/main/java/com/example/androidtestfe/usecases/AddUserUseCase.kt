package com.example.androidtestfe.usecases

import com.example.androidtestfe.data.localdatabase.model.ListModel
import com.example.androidtestfe.data.repositories.UserRepo
import javax.inject.Inject

class AddUserUseCase @Inject constructor(private val repo: UserRepo) {

    fun addUser(model: ListModel) {
        repo.addUser(model = model)
    }
}