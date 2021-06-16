package mycom.learnsicoapp.movieapp.ui.savedAll

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import mycom.learnsicoapp.movieapp.R
import mycom.learnsicoapp.movieapp.data.database.relations.MovieAndRatings
import mycom.learnsicoapp.movieapp.data.remote.response.movie.MovieResponse
import mycom.learnsicoapp.movieapp.utils.URL_IMAGE

class SavedAdapterAll : RecyclerView.Adapter<SavedAdapterAll.ViewHolderSaved>() {

    var listSavedRatings = listOf<MovieAndRatings>()
    var movieList = listOf<MovieResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSaved {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_saved, parent, false)
        return ViewHolderSaved(view)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: ViewHolderSaved, position: Int) {

        val size = listSavedRatings.size

        if(position < size && size != 0){
            val ratingList = listSavedRatings[position].ratingList
            ratingList.map {
                holder.contentView.text = it.ratingId
            }
        }

        val dataMovie = movieList[position]
        Glide.with(holder.idView.rootView.context)
            .load(URL_IMAGE + dataMovie.posterPath)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(holder.idView)
    }


    fun addMovieWithRating(allElement: List<MovieResponse>, list: List<MovieAndRatings>) {
        movieList = allElement
        listSavedRatings = list
        notifyDataSetChanged()
    }


    class ViewHolderSaved(view: View) : RecyclerView.ViewHolder(view) {
        var idView: ImageView = view.findViewById(R.id.item_image)
        val contentView: TextView = view.findViewById(R.id.content)
    }
}