package mycom.learnsicoapp.movieapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import mycom.learnsicoapp.movieapp.data.database.entities.MovieEntity
import mycom.learnsicoapp.movieapp.data.database.entities.Rating
import mycom.learnsicoapp.movieapp.data.database.entities.User
import mycom.learnsicoapp.movieapp.data.database.relations.MovieRatingCrossRef
import mycom.learnsicoapp.movieapp.data.database.relations.UserMovieCrossRef


@Database(entities = [User::class, UserMovieCrossRef::class, MovieRatingCrossRef::class, MovieEntity::class, Rating::class], version = 1 , exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun movieDao() : DatabaseDao
}