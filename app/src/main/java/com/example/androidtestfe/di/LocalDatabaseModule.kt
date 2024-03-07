package com.example.androidtestfe.di

import android.content.Context
import androidx.room.Room
import com.example.androidtestfe.data.localdatabase.ListDAO
import com.example.androidtestfe.data.localdatabase.LocalDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {

    @Provides
    @Singleton
    fun localDatabase(@ApplicationContext context: Context ) : LocalDataBase {
        return Room.databaseBuilder(context, LocalDataBase::class.java, "list")
            .build()
    }


    @Provides
    fun provideDao(myDatabase: LocalDataBase) : ListDAO {
        return myDatabase.daoDatabase()
    }
}