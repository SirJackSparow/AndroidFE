package com.example.androidtestfe.data.localdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidtestfe.data.localdatabase.model.ListModel

@Dao
interface ListDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listModel: ListModel)

    @Query("select * from ListModel")
    suspend fun getAllData(): List<ListModel>

}
