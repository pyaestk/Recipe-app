package com.example.foodrecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipe.data.Category
import com.example.foodrecipe.databinding.CategoryMealsItemsBinding

class CategoryAdapter(): RecyclerView.Adapter<CategoryAdapter.CategoryMealListViewHolder>() {

    var onItemClick: ((Category) -> Unit) ?= null

    private var categoryMealList = ArrayList<Category>()
    fun setCategories(categoryMealList: List<Category>) {
        this.categoryMealList = categoryMealList as ArrayList<Category>
        notifyDataSetChanged()
    }

    class CategoryMealListViewHolder(var binding: CategoryMealsItemsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealListViewHolder {
        return CategoryMealListViewHolder(CategoryMealsItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return categoryMealList.size
    }

    override fun onBindViewHolder(holder: CategoryMealListViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryMealList[position].strCategoryThumb)
            .into(holder.binding.categoryImageView)
        holder.binding.categoryNameTextView.text = categoryMealList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoryMealList[position])
        }
    }
}