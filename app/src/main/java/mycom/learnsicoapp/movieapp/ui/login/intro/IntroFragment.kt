package mycom.learnsicoapp.movieapp.ui.login.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import mycom.learnsicoapp.movieapp.R
import mycom.learnsicoapp.movieapp.databinding.FragmentIntroBinding
import mycom.learnsicoapp.movieapp.di.Navigator
import mycom.learnsicoapp.movieapp.ui.BaseFragment
import javax.inject.Inject

@AndroidEntryPoint
class IntroFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator

    lateinit var binding: FragmentIntroBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentIntroBinding.inflate(inflater)

        binding.btnSignIn.setOnClickListener {
            navigator.navigate(R.id.navDirectionSignInFragment)
        }

        binding.btnSignUp.setOnClickListener {
                navigator.navigate(R.id.navDirectionSignUpFragment)
        }

        return binding.root
    }
}