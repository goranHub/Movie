package mycom.learnsicoapp.movieapp.ui.savedAll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mycom.learnsicoapp.movieapp.databinding.FragmentSavedListBinding
import mycom.learnsicoapp.movieapp.ui.BaseFragment


@AndroidEntryPoint
class SavedFragmentAll : BaseFragment() {

    private lateinit var binding: FragmentSavedListBinding
    private val viewModel: SavedViewModelAll by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedListBinding.inflate(inflater)
        binding.listMovieSaved.layoutManager = GridLayoutManager(context, 2)
        binding.listMovieSaved.adapter = viewModel.adapter
        return binding.root
    }
}