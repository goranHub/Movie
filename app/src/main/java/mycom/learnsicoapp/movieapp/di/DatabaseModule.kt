package mycom.learnsicoapp.movieapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mycom.learnsicoapp.movieapp.data.database.Database
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        Database::class.java,
        "user_db"
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(db: Database) = db.movieDao()
}








