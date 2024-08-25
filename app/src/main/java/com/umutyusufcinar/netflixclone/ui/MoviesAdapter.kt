package com.umutyusufcinar.netflixclone.ui


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umutyusufcinar.netflixclone.R
import com.umutyusufcinar.netflixclone.network.model.dto.MovieDTO

class MovieAdapter (val context: Context, val movies: List<MovieDTO>)
    : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_movie, parent, false)
        return MovieHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        movies.elementAt(position).apply {  }
    }

    class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var cardView = itemView.cv_movie
        private var poster = itemView.iv_movie_poster
    }
}