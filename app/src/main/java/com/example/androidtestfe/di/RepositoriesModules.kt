package com.example.androidtestfe.di

import com.example.androidtestfe.data.repositories.UserRepo
import com.example.androidtestfe.data.repositories.UserRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModules {

    @Binds
    @Singleton
    abstract fun userRepository(userRepoImpl: UserRepoImpl) : UserRepo
}