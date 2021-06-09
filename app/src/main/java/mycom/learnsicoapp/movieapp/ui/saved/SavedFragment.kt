package mycom.learnsicoapp.movieapp.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import mycom.learnsicoapp.movieapp.data.remote.firebase.FireStoreClass
import mycom.learnsicoapp.movieapp.databinding.FragmentSavedListBinding
import mycom.learnsicoapp.movieapp.ui.BaseFragment


@AndroidEntryPoint
class SavedFragment : BaseFragment() {

    private lateinit var binding: FragmentSavedListBinding
    private val viewModel: SavedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSavedListBinding.inflate(inflater)
        binding.listMovieSaved.layoutManager = GridLayoutManager(context, 2)

        lifecycleScope.launch{
            val currentUserID = FireStoreClass().currentUserID()
            val listOfRatings = viewModel.getRatingsOfUser(currentUserID)
            viewModel.getByMovieID(listOfRatings)
        }

        binding.listMovieSaved.adapter = viewModel.adapter
        return binding.root
    }
}