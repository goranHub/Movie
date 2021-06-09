package mycom.learnsicoapp.movieapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mycom.learnsicoapp.movieapp.data.remote.firebase.FireStoreClass
import javax.inject.Singleton

/**
 * @author ll4
 * @date 1/22/2021
 */

@Module
@InstallIn(SingletonComponent::class)
object FireStoreClassModule {

    @Singleton
    @Provides
    fun provideFrireStore() : FireStoreClass {
        return FireStoreClass()
    }
}

