package mycom.learnsicoapp.movieapp.ui.login.intro

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import mycom.learnsicoapp.movieapp.domain.Repository

/**
 * @author ll4
 * @date 1/26/2021
 */
class IntroVM  @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel()