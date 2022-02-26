package com.example.moviedatabase.Tabs.films

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedatabase.R
import com.example.moviedatabase.api.models.FilmsApiModel
import com.example.moviedatabase.databinding.FilmItemBinding

class FilmsAdapter (private val filmsList : ArrayList<FilmsApiModel>,
                    private val deleteFilm:(Int)->Unit,
                    private val editFilm:(FilmsApiModel)->Unit): RecyclerView.Adapter<FilmsAdapter.FilmsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: FilmItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.film_item, parent, false)
        return FilmsHolder(binding)
    }

    override fun getItemCount(): Int {
        return filmsList.size
    }

    override fun onBindViewHolder(holder: FilmsHolder, position: Int) {
        holder.bind(filmsList[position], deleteFilm, editFilm)
    }

    class FilmsHolder(val binding: FilmItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            films: FilmsApiModel, deleteFilm: (Int) -> Unit, editFilm: (FilmsApiModel) -> Unit
        ) {

            val idFilm = films.id

            binding.idFilm.text = idFilm.toString()

            binding.nameFilm.text = films.name
            binding.categoryFilm.text = films.category
            binding.durationFilm.text = films.duration.toString()


            binding.editFilm.setOnClickListener(View.OnClickListener {
                editFilm(films)
            })

            binding.deleteFilm.setOnClickListener(View.OnClickListener {
                deleteFilm(idFilm!!)
            })
        }

    }

}