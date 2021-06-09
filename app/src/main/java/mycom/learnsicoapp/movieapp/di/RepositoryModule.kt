package mycom.learnsicoapp.movieapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mycom.learnsicoapp.movieapp.data.database.DatabaseDataSource
import mycom.learnsicoapp.movieapp.data.remote.NetworkDataSource
import mycom.learnsicoapp.movieapp.domain.Repository
import javax.inject.Singleton

/**
 * @author ll4
 * @date 1/20/2021
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        networkDataSource: NetworkDataSource,
        databaseDataSource: DatabaseDataSource
    ): Repository {
        return Repository(
            networkDataSource = networkDataSource,
            databaseDataSource = databaseDataSource
        )
    }
}