package com.example.moviedatabase.Tabs.films

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.moviedatabase.R
import com.example.moviedatabase.databinding.FragmentPanelEditFilmBinding
import com.example.mysqlretrofitrecycler.api.ApiClient
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class PanelEditFilm  : BottomSheetDialogFragment(),View.OnKeyListener, View.OnClickListener {

    private var binding: FragmentPanelEditFilmBinding? = null
    private var idFilm:Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_panel_edit_film, container, false)

        idFilm = arguments?.getString("idFilm")?.toInt()
        binding?.editNameFilm?.setText(arguments?.getString("nameFilm").toString())
        binding?.editCategoryFilm?.setText(arguments?.getString("categoryFilm").toString())
        binding?.editDurationFilm?.setText(arguments?.getString("priceFilm").toString())

        binding?.editNameFilm?.setOnKeyListener(this)
        binding?.editCategoryFilm?.setOnKeyListener(this)
        binding?.editDurationFilm?.setOnKeyListener(this)

        binding?.buttonEditFilm?.setOnClickListener(this)


        return binding?.root
    }

    override fun onKey(view: View, i: Int, keyEvent: KeyEvent): Boolean {
        when (view.id) {


            R.id.editNameFilm -> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {

                    binding?.resEditNameFilm?.text = binding?.editNameFilm?.text
                    binding?.editNameFilm?.setText("")

                    return true
                }

            }

            R.id.editCategoryFilm -> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {

                    binding?.resEditCategoryFilm?.text = binding?.editCategoryFilm?.text
                    binding?.editCategoryFilm?.setText("")

                    return true
                }

            }

            R.id.editDurationFilm -> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {

                    binding?.resEditDurationFilm?.text = binding?.editDurationFilm?.text
                    binding?.editDurationFilm?.setText("")

                    return true
                }

            }
        }

        return false
    }

    override fun onClick(view: View) {

        updateFilm(binding?.resEditNameFilm?.text?.toString()!!, binding?.resEditCategoryFilm?.text?.toString()!!,
            binding?.resEditDurationFilm?.text?.toString()!!)
    }




    private fun updateFilm(name: String, category: String, duration: String) {
        val callUpdateFilm = ApiClient.instance?.api?.updateFilm(idFilm.toString().toInt(), name, category, duration)

        callUpdateFilm?.enqueue(object : retrofit2.Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(
                    context,
                    "ТОВАР ОБНОВЛЕН",
                    Toast.LENGTH_SHORT
                ).show()

                loadScreen()


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

    private fun loadScreen() {

        dismiss()

        (context as FragmentActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.content, CatalogFilms()).commit()

    }
}