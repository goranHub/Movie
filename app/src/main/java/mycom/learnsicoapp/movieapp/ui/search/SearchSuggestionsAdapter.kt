package mycom.learnsicoapp.movieapp.ui.search

import android.content.Context
import android.widget.ArrayAdapter
import mycom.learnsicoapp.movieapp.data.remote.response.multi.MultiResult

class SearchSuggestionsAdapter(context: Context, resource: Int) : ArrayAdapter<String>(context, resource) {

    private val hintArrayList = arrayListOf<String>()

    fun setData(list: List<String>?) {
        hintArrayList.clear()
        if (list != null) {
            hintArrayList.addAll(list)
        }
    }

    override fun getCount(): Int {
        return hintArrayList.size
    }

    override fun getItem(position: Int): String? {
        return hintArrayList[position]
    }
}