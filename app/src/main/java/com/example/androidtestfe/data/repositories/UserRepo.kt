package com.example.androidtestfe.data.repositories

import com.example.androidtestfe.data.localdatabase.model.ListModel

interface UserRepo {

    fun addUser(model: ListModel)

    fun getUser() : List<ListModel>

    fun login(email:String, password: String) : ListModel
}