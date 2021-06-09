package mycom.learnsicoapp.movieapp.utils

import mycom.learnsicoapp.movieapp.data.database.User
import mycom.learnsicoapp.movieapp.data.remote.firebase.model.UserFirebase
import mycom.learnsicoapp.movieapp.data.remote.response.user.UserModel


fun UserFirebase.mapToUserEntity() : User {
    return  User(
        id = this.id,
        name = this.name,
        email = this.email,
        image = this.image,
        movieId = this.movieId,
        movieRating = this.movieRating,
        fcmToken = this.fcmToken
    )
}

fun UserFirebase.mapToUserModel() : UserModel {
    return  UserModel(
        id = this.id,
        name = this.name,
        email = this.email,
        image = this.image,
        movieId = this.movieId,
        movieRating = this.movieRating,
        fcmToken = this.fcmToken
    )
}
