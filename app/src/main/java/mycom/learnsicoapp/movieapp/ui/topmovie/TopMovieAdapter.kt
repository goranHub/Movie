package mycom.learnsicoapp.movieapp.ui.topmovie

/**
 * @author ll4
 * @date 12/10/2020
 */

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import mycom.learnsicoapp.movieapp.BR
import mycom.learnsicoapp.movieapp.R
import mycom.learnsicoapp.movieapp.databinding.ItemMovieTopBinding
import mycom.learnsicoapp.movieapp.ui.popular.BindMovie
import javax.inject.Inject


class TopMovieAdapter @Inject constructor(): RecyclerView.Adapter<TopMovieAdapter.TopViewHolder>() {

    var list = mutableListOf<BindMovie>()
    lateinit var binding: ItemMovieTopBinding
    lateinit var listenerCall: ListenerCall

    interface ListenerCall {
        fun callback(binding: ItemMovieTopBinding)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : TopViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movie_top,
            parent,
            false
        )
        listenerCall.callback(binding)
        return TopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopViewHolder, position: Int) {
        val dataModel = list[position]
        holder.bind(dataModel)
    }


    override fun getItemCount() = list.size

    fun addMovies(listItems: List<BindMovie>) {
        list.addAll(listItems)
    }

    class TopViewHolder(val binding: ItemMovieTopBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(obj: Any?) {
            binding.setVariable(BR.data, obj)
            binding.executePendingBindings()
        }
    }
}