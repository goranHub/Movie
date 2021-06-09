package mycom.learnsicoapp.movieapp.di

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {

    lateinit var navController: NavController

    fun navigate(@IdRes navigationId: Int) {
        navController.navigate(navigationId)
    }

    fun navigateWithBundle(@IdRes navigationId: Int, bundle: Bundle) {
        navController.navigate(navigationId, bundle)
    }

    fun navigateBack() {
        navController.popBackStack()
    }
}
