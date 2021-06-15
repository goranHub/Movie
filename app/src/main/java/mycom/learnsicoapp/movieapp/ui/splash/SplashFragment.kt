package mycom.learnsicoapp.movieapp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import mycom.learnsicoapp.movieapp.R
import mycom.learnsicoapp.movieapp.databinding.FragmentSplashBinding
import mycom.learnsicoapp.movieapp.di.Navigator
import mycom.learnsicoapp.movieapp.ui.BaseFragment
import javax.inject.Inject


/**
 * @author ll4
 * @date 1/15/2021
 */
@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    lateinit var binding: FragmentSplashBinding

    @Inject
    lateinit var navigator: Navigator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSplashBinding.inflate(inflater)
        showProgressDialog(resources.getString(R.string.please_wait))

        Handler(Looper.getMainLooper()).postDelayed({
            dialog.dismiss()
            navigator.navigate(R.id.introFragment)
        }, 500)

        return binding.root
    }
}