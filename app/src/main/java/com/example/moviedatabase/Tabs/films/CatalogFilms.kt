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
    private var productsAdapter: FilmsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_catalog_films, container, false)

        loadProducts()

        binding?.deleteAllProducts?.setOnClickListener(this)

        return binding?.root
    }

    private fun loadProducts () {

        val callProducts = ApiClient.instance?.api?.getFilm()
        callProducts?.enqueue(object: Callback<ArrayList<FilmsApiModel>> {
            override fun onResponse(
                call: Call<ArrayList<FilmsApiModel>>,
                response: Response<ArrayList<FilmsApiModel>>
            ) {

                val loadProducts = response.body()

                binding?.recyclerFilms?.layoutManager = LinearLayoutManager(context)
                productsAdapter = loadProducts?.let {
                    FilmsAdapter(
                        it, { idProduct:Int->deleteProduct(idProduct)},
                        {productsApiModel:FilmsApiModel->editProduct(productsApiModel)})
                }
                binding?.recyclerFilms?.adapter = productsAdapter

                Toast.makeText(context, "ЗАГРУЗКА", Toast.LENGTH_SHORT).show()

            }

            override fun onFailure(call: Call<ArrayList<FilmsApiModel>>, t: Throwable) {
                Toast.makeText(context, "ОШИБКА! ВКЛЮЧИТЕ ИНТЕРНЕТ!", Toast.LENGTH_SHORT).show()

            }
        })

    }

    override fun onClick(v: View?) {

        clearAllProducts()

    }

    private fun deleteProduct(id:Int) {

        val callDeleteProduct: Call<ResponseBody?>? = ApiClient.instance?.api?.deleteFilm(id)

        callDeleteProduct?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(
                    context,
                    "ТОВАР УДАЛЕН",
                    Toast.LENGTH_SHORT
                ).show()

                loadProducts()
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

    private fun editProduct(productsApiModel: FilmsApiModel) {
        val panelEditProduct = PanelEditFilm()
        val parameters = Bundle()
        parameters.putString("idProduct", productsApiModel.id.toString())
        parameters.putString("nameProduct", productsApiModel.name)
        parameters.putString("categoryProduct", productsApiModel.category)
        parameters.putString("priceProduct", productsApiModel.duration)
        panelEditProduct.arguments = parameters

        panelEditProduct.show((context as FragmentActivity).supportFragmentManager, "editFilm")
    }

    private fun clearAllProducts() {

        val callClearAllProducts: Call<ResponseBody?>? = ApiClient.instance?.api?.clearFilms()

        callClearAllProducts?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(
                    context,
                    "ТОВАРЫ УДАЛЕНЫ",
                    Toast.LENGTH_SHORT
                ).show()

                loadProducts()
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