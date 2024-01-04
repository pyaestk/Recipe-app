package com.example.foodrecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipe.data.Meal
import com.example.foodrecipe.data.MealList
import com.example.foodrecipe.data.MealsByCategory
import com.example.foodrecipe.data.PopularMeals
import com.example.foodrecipe.databinding.ActivityCategoryMealsBinding
import com.example.foodrecipe.databinding.MealItemBinding
import com.example.foodrecipe.views.activities.CategoryMealsActivity

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>(){

    lateinit var onItemClick: ((MealsByCategory) -> Unit)

    class CategoryMealsViewHolder(var binding: MealItemBinding): RecyclerView.ViewHolder(binding.root)

    private var mealsList = ArrayList<MealsByCategory>()

    fun setMealsList(mealsList: List<MealsByCategory>){
        this.mealsList = mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.binding.mealImageView)
        holder.binding.mealNameTextView.text = mealsList[position].strMeal
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }
    }                                                                              
}