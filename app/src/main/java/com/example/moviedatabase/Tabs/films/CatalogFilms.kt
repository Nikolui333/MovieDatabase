package com.example.moviedatabase.Tabs.films

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
import com.example.moviedatabase.api.models.FilmsApiModel
import com.example.moviedatabase.databinding.FragmentCatalogFilmsBinding
import com.example.mysqlretrofitrecycler.api.ApiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatalogFilms : Fragment(),View.OnClickListener {

    private var binding: FragmentCatalogFilmsBinding? = null
    private var filmsAdapter: FilmsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_catalog_films, container, false)

        loadFilms()

        binding?.deleteAllFilms?.setOnClickListener(this)

        return binding?.root
    }

    private fun loadFilms () {

        val callFilms = ApiClient.instance?.api?.getFilm()
        callFilms?.enqueue(object: Callback<ArrayList<FilmsApiModel>> {
            override fun onResponse(
                call: Call<ArrayList<FilmsApiModel>>,
                response: Response<ArrayList<FilmsApiModel>>
            ) {

                val loadFilms = response.body()

                binding?.recyclerFilms?.layoutManager = LinearLayoutManager(context)
                filmsAdapter = loadFilms?.let {
                    FilmsAdapter(
                        it, { idFilm:Int->deleteFilm(idFilm)},
                        {filmsApiModel:FilmsApiModel->editFilm(filmsApiModel)})
                }
                binding?.recyclerFilms?.adapter = filmsAdapter

                Toast.makeText(context, "ЗАГРУЗКА", Toast.LENGTH_SHORT).show()

            }

            override fun onFailure(call: Call<ArrayList<FilmsApiModel>>, t: Throwable) {
                Toast.makeText(context, "ОШИБКА! ВКЛЮЧИТЕ ИНТЕРНЕТ!", Toast.LENGTH_SHORT).show()

            }
        })

    }

    override fun onClick(v: View?) {

        clearAllFilms()

    }

    private fun deleteFilm(id:Int) {

        val callDeleteFilm: Call<ResponseBody?>? = ApiClient.instance?.api?.deleteFilm(id)

        callDeleteFilm?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(
                    context,
                    "ТОВАР УДАЛЕН",
                    Toast.LENGTH_SHORT
                ).show()

                loadFilms()
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(
                    context,
                    "ОШИБКА! ВКЛЮЧИТЕ ИНТЕРНЕТ!",
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

    private fun clearAllFilms() {

        val callClearAllFilms: Call<ResponseBody?>? = ApiClient.instance?.api?.clearFilms()

        callClearAllFilms?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(
                    context,
                    "ТОВАРЫ УДАЛЕНЫ",
                    Toast.LENGTH_SHORT
                ).show()

                loadFilms()
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(
                    context,
                    "ОШИБКА! ВКЛЮЧИТЕ ИНТЕРНЕТ!",
                    Toast.LENGTH_SHORT
                ).show()
            }


        })

    }
}