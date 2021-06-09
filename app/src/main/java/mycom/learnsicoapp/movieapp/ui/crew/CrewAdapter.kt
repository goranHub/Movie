package mycom.learnsicoapp.movieapp.ui.crew

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import mycom.learnsicoapp.movieapp.BR
import mycom.learnsicoapp.movieapp.R
import mycom.learnsicoapp.movieapp.databinding.ItemCrewBinding

/**
 * @author ll4
 * @date 12/11/2020
 */
class CrewAdapter: RecyclerView.Adapter<CrewAdapter.CrewViewHolder>(){

    var list = mutableListOf<CrewObservable>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewViewHolder {
       val binding : ItemCrewBinding = DataBindingUtil.inflate(
           LayoutInflater.from(parent.context),
           R.layout.item_crew,
           parent,
           false
       )
        return CrewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CrewViewHolder, position: Int) {
        val data = list[position]
        holder.bind(data)
    }

    class CrewViewHolder(val binding: ItemCrewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(obj: Any?) {
            binding.setVariable(BR.data, obj)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount() = list.size

    fun addCast(crewItems: List<CrewObservable>?) {
        list.addAll(crewItems!!)
        notifyDataSetChanged()
    }
}