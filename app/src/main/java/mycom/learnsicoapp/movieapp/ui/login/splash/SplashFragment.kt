package mycom.learnsicoapp.movieapp.ui.login.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mycom.learnsicoapp.movieapp.R
import mycom.learnsicoapp.movieapp.databinding.FragmentEntryBinding
import mycom.learnsicoapp.movieapp.di.Navigator
import mycom.learnsicoapp.movieapp.ui.BaseFragment
import javax.inject.Inject


/**
 * @author ll4
 * @date 1/15/2021
 */
@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    lateinit var binding: FragmentEntryBinding

    @Inject
    lateinit var navigator: Navigator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEntryBinding.inflate(inflater)

        Handler(Looper.getMainLooper()).postDelayed({
            navigator.navigate(R.id.introFragment)
        }, 1000)

        return binding.root
    }
}