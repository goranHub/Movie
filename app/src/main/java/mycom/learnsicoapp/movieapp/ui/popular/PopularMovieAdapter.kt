package mycom.learnsicoapp.movieapp.ui.popular

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
import mycom.learnsicoapp.movieapp.databinding.ItemMoviePopularBinding
import mycom.learnsicoapp.movieapp.di.Navigator
import javax.inject.Inject


class PopularMovieAdapter @Inject constructor() : RecyclerView.Adapter<PopularMovieAdapter.PopularViewHolder>() {

    var list = mutableListOf<BindMovie>()
    lateinit var binding: ItemMoviePopularBinding
    lateinit var listenerCall: ListenerCall

    interface ListenerCall {
        fun callback(binding: ItemMoviePopularBinding)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : PopularViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movie_popular,
            parent,
            false
        )
        listenerCall.callback(binding)
        return PopularViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val dataModel = list[position]
        holder.bind(dataModel)
    }

    override fun getItemCount() = list.size

    class PopularViewHolder(val binding: ItemMoviePopularBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(obj: Any?) {
            binding.setVariable(BR.data, obj)
            binding.executePendingBindings()
        }
    }

    fun addMovies(listItems: List<BindMovie>) {
        list.addAll(listItems)
        notifyDataSetChanged()
    }
}