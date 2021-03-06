package com.example.moviedatabase.Tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedatabase.R
import com.example.moviedatabase.Tabs.films.FilmsAdapter
import com.example.moviedatabase.Tabs.films.PanelEditFilm
import com.example.moviedatabase.api.models.FilmsApiModel
import com.example.moviedatabase.databinding.FragmentActionMovieCatalogBinding
import com.example.mysqlretrofitrecycler.api.ApiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActionMovieCatalog : Fragment() {

    private var binding: FragmentActionMovieCatalogBinding? = null
    private var filmsAdapter: FilmsAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_action_movie_catalog, container, false)

        loadClothes()

        return binding?.root
    }


    private fun loadClothes () {

        val callGetClothes = ApiClient.instance?.api?.getFilmFilter(getString(R.string.actionMovieCatalog),
            getString(R.string.durationFilter))
        callGetClothes?.enqueue(object: Callback<ArrayList<FilmsApiModel>> {
            override fun onResponse(
                call: Call<ArrayList<FilmsApiModel>>,
                response: Response<ArrayList<FilmsApiModel>>
            ) {

                val loadFilms = response.body()

                binding?.recyclerActionMovies?.layoutManager = LinearLayoutManager(context)
                filmsAdapter = loadFilms?.let {
                    FilmsAdapter(
                        it, { idFilm:Int->deleteFilm(idFilm)},
                        {filmsApiModel:FilmsApiModel->editFilm(filmsApiModel)})
                }
                binding?.recyclerActionMovies?.adapter = filmsAdapter

                Toast.makeText(context, "????????????????", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ArrayList<FilmsApiModel>>, t: Throwable) {
                Toast.makeText(context, "????????????! ???????????????? ????????????????!", Toast.LENGTH_SHORT).show()

            }
        })

    }


    private fun deleteFilm(id:Int) {

        val callDeleteFilm: Call<ResponseBody?>? = ApiClient.instance?.api?.deleteFilm(id)

        callDeleteFilm?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(
                    context,
                    "?????????? ????????????",
                    Toast.LENGTH_SHORT
                ).show()

                loadClothes()
            }



            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(
                    context,
                    "????????????! ???????????????? ????????????????!",
                    Toast.LENGTH_SHORT
                ).show()
            }


        })

    }

    private fun editFilm(filmsApiModel: FilmsApiModel) {
        val panelEditFilm = PanelEditFilm()
        val parameters = Bundle()
        parameters.putString("idFilm", filmsApiModel.id.toString())
        parameters.putString("nameFilm", filmsApiModel.name)
        parameters.putString("categoryFilm", filmsApiModel.category)
        parameters.putString("durationFilm", filmsApiModel.duration)
        panelEditFilm.arguments = parameters

        panelEditFilm.show((context as FragmentActivity).supportFragmentManager, "editFilm")
    }
}