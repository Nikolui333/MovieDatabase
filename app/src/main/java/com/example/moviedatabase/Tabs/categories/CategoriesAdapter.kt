package com.example.moviedatabase.Tabs.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedatabase.R
import com.example.moviedatabase.api.models.CategoriesApiModel
import com.example.moviedatabase.databinding.CategoryItemBinding

class CategoriesAdapter(private val categoriesList: ArrayList<CategoriesApiModel>,
                        private val deleteCategory:(Int)->Unit,
                        private val editCategory:(CategoriesApiModel)->Unit): RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CategoryItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.category_item, parent, false)
        return CategoriesHolder(binding)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: CategoriesHolder, position: Int) {
        holder.bind(categoriesList[position], deleteCategory, editCategory)
    }

    class CategoriesHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            categories: CategoriesApiModel, deleteCategory: (Int) -> Unit,
            editCategory: (CategoriesApiModel) -> Unit
        ) {

            val idCategory = categories.id

            binding.idCategory.text = idCategory.toString()

            binding.nameCategory.text = categories.name

            binding.editCategory.setOnClickListener(View.OnClickListener {
                editCategory(categories)

            })

            binding.deleteCategory.setOnClickListener(View.OnClickListener {
              deleteCategory(idCategory!!)
            })
        }

    }

}