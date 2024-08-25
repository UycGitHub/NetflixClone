package com.umutyusufcinar.netflixclone.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umutyusufcinar.netflixclone.R
import com.umutyusufcinar.netflixclone.network.model.dto.MovieDTO

class ListsOfMoviesAdapter (val context: Context, val lists: List<List<MovieDTO>>)
    : RecyclerView.Adapter<ListsOfMoviesAdapter.MovieListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_rv_movies, parent, false)
        return MovieListHolder(view)
    }

    override fun getItemCount(): Int = lists.size

    override fun onBindViewHolder(holder: MovieListHolder, position: Int) {
        holder.title.text = when(position){
            0 -> "Trending Now"
            1 -> "Upcoming Movies"
            2 -> "Popular Movies"
            else -> "Movies"
        }
        holder.movies.LayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.movies.adapter = MoviesAdapter(context, lists[position])
    }

    class MovieListHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var title = itemView.tv_movies_title
        private var movies = itemView.rv_movies
    }
}