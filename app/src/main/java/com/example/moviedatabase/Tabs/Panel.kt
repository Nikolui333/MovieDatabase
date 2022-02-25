package com.example.moviedatabase.Tabs

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.moviedatabase.R
import com.example.moviedatabase.databinding.FragmentPanelBinding
import com.example.mysqlretrofitrecycler.api.ApiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Panel : Fragment(), View.OnKeyListener, View.OnClickListener {

    private var binding: FragmentPanelBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_panel, container, false)


        binding?.enterNameProduct?.setOnKeyListener(this)
        binding?.enterCategoryProduct?.setOnKeyListener(this)
        binding?.enterPriceProduct?.setOnKeyListener(this)

        binding?.buttonAddActionMovies?.setOnClickListener(this)
        binding?.buttonAddCategoryShoes?.setOnClickListener(this)
        binding?.buttonAddCategoryAccessories?.setOnClickListener(this)
        binding?.buttonAddProduct?.setOnClickListener(this)



        return binding?.root
    }

    override fun onKey(view: View, i: Int, keyEvent: KeyEvent): Boolean {
        when (view.id) {

            R.id.enterNameProduct -> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    binding?.resEnterNameProduct?.text = binding?.enterNameProduct?.text
                    binding?.enterNameProduct?.setText("")
                    return true
                }

            }

            R.id.enterCategoryProduct -> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    binding?.resEnterCategoryProduct?.text = binding?.enterCategoryProduct?.text
                    binding?.enterCategoryProduct?.setText("")
                    return true
                }

            }

            R.id.enterPriceProduct -> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    binding?.resEnterPriceProduct?.text = binding?.enterPriceProduct?.text
                    binding?.enterPriceProduct?.setText("")
                    return true
                }

            }
        }

        return false
    }

    override fun onClick(view: View) {

        when (view.id) {

            R.id.buttonAddActionMovies -> {

                insertCategory(binding?.buttonAddActionMovies?.text?.toString())

            }

            R.id.buttonAddCategoryShoes -> {

                insertCategory(binding?.buttonAddCategoryShoes?.text?.toString())

            }

            R.id.buttonAddCategoryAccessories -> {

                insertCategory(binding?.buttonAddCategoryAccessories?.text?.toString())

            }

            R.id.buttonAddProduct -> {

                insertProduct(
                    binding?.resEnterNameProduct?.text?.toString(),
                    binding?.resEnterCategoryProduct?.text?.toString(),
                    binding?.resEnterPriceProduct?.text?.toString()
                )

                clearResEnterProduct()

            }


        }

    }

    private fun clearResEnterProduct() {
        binding?.resEnterNameProduct?.setText("")
        binding?.resEnterCategoryProduct?.setText("")
        binding?.resEnterPriceProduct?.setText("")

    }


    private fun insertCategory(name: String?) {
        val callInsertCategory: Call<ResponseBody?>? = ApiClient.instance?.api?.insertCategory(name)
        callInsertCategory?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(context, "ДОБАВЛЕНА КАТЕГОРИЯ", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(context, "ОШИБКА", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun insertProduct(name: String?, category: String?, price: String?) {
        val callInsertProduct: Call<ResponseBody?>? =
            ApiClient.instance?.api?.insertFilm(name, category, price)
        callInsertProduct?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(context, "ТОВАР ДОБАВЛЕН", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(context, "ОШИБКА", Toast.LENGTH_SHORT).show()
            }
        })
    }
}