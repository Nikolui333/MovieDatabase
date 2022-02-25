package com.example.moviedatabase.Tabs.categories

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
import com.example.moviedatabase.api.models.CategoriesApiModel
import com.example.moviedatabase.databinding.FragmentCatalogCategoriesBinding
import com.example.mysqlretrofitrecycler.api.ApiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatalogCategories : Fragment() {

    private var binding: FragmentCatalogCategoriesBinding? = null
    private var categoriesAdapter: CategoriesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_catalog_categories, container, false)

        loadCategories()


        binding?.deleteAllCategories?.setOnClickListener(View.OnClickListener {

            clearAllCategories()

        })

        return binding?.root
    }

    private fun loadCategories () {

        val callCategories = ApiClient.instance?.api?.getCategory()
        callCategories?.enqueue(object: Callback<ArrayList<CategoriesApiModel>> {
            override fun onResponse(
                call: Call<ArrayList<CategoriesApiModel>>,
                response: Response<ArrayList<CategoriesApiModel>>
            ) {

                val loadCategories = response.body()

                binding?.recyclerCategories?.layoutManager = LinearLayoutManager(context)

                categoriesAdapter = loadCategories?.let {
                    CategoriesAdapter(
                        it, { idCategory:Int->deleteCategory(idCategory)},
                        {categoriesApiModel:CategoriesApiModel->editCategory(categoriesApiModel)})
                }
                binding?.recyclerCategories?.adapter = categoriesAdapter

                Toast.makeText(context, "ЗАГРУЗКА", Toast.LENGTH_SHORT).show()


            }

            override fun onFailure(call: Call<ArrayList<CategoriesApiModel>>, t: Throwable) {
                Toast.makeText(context, "ОШИБКА! ВКЛЮЧИТЕ ИНТЕРНЕТ!", Toast.LENGTH_SHORT).show()

            }
        })

    }

    private fun deleteCategory(id:Int) {

        val callDeleteCategory: Call<ResponseBody?>? = ApiClient.instance?.api?.deleteCategory(id)

        callDeleteCategory?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(
                    context,
                    "КАТЕГОРИЯ УДАЛЕНА",
                    Toast.LENGTH_SHORT
                ).show()

                loadCategories()
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

    private fun clearAllCategories() {

        val callClearAllCat: Call<ResponseBody?>? = ApiClient.instance?.api?.clearCategories()

        callClearAllCat?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(
                    context,
                    "КАТЕГОРИИ УДАЛЕНЫ",
                    Toast.LENGTH_SHORT
                ).show()

                loadCategories()
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

    private fun editCategory(categoriesApiModel:CategoriesApiModel) {
        val panelCategory = PanelEditCategory()
        val parameters = Bundle()
        parameters.putString("idCategory", categoriesApiModel.id.toString())
        parameters.putString("nameCategory", categoriesApiModel.name)
        panelCategory.arguments = parameters

        panelCategory.show((context as FragmentActivity).supportFragmentManager, "editCategory")
    }
}