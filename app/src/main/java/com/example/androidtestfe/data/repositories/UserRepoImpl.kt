package com.example.androidtestfe.data.repositories

import com.example.androidtestfe.data.localdatabase.ListDAO
import com.example.androidtestfe.data.localdatabase.model.ListModel
import javax.inject.Inject
class UserRepoImpl @Inject constructor(private val dao: ListDAO) : UserRepo {
    override fun addUser(model: ListModel) {
        dao.insert(model)
    }
    override fun getUser() : List<ListModel> {
       return dao.getAllData()
    }
    override fun login(email:String, password: String): ListModel {
        return dao.loginUser(email = email, password =  password)
    }
}