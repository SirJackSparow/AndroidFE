package com.example.androidtestfe.usecases

import com.example.androidtestfe.data.localdatabase.model.ListModel
import com.example.androidtestfe.data.repositories.UserRepo
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repo: UserRepo) {

    fun login(email: String, password: String) : ListModel{
        return repo.login(email, password)
    }
}