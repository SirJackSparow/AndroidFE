package com.example.androidtestfe.data.localdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidtestfe.data.localdatabase.model.ListModel

@Dao
interface ListDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(listModel: ListModel)

    @Query("SELECT * FROM ListModel")
    fun getAllData(): List<ListModel>

}
