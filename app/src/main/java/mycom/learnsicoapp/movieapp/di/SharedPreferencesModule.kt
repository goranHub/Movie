package mycom.learnsicoapp.movieapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mycom.learnsicoapp.movieapp.utils.PREF_NAME
import javax.inject.Singleton

/**
 * @author lllhr
 * @date 6/11/2021
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context) =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
}