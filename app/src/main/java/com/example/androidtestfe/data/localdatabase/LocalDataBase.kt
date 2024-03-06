package com.example.androidtestfe.data.localdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidtestfe.data.localdatabase.model.ListModel

@Database(entities = [ListModel::class], version = 1, exportSchema = false)
abstract class LocalDataBase : RoomDatabase() {
    abstract fun daoDatabase() : ListDAO
}