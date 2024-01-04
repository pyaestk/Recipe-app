package com.example.foodrecipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipe.data.PopularMeals
import com.example.foodrecipe.databinding.PopularMealsItemBinding

class PopularMealAdapter(): RecyclerView.Adapter<PopularMealAdapter.PopularMealViewHolder>() {

    lateinit var onItemClick: ((PopularMeals) -> Unit)

    private var mealsList = ArrayList<PopularMeals>()
    fun setMeals(mealsList: ArrayList<PopularMeals>) {
        this.mealsList = mealsList
        notifyDataSetChanged()
    }

    class PopularMealViewHolder(var binding: PopularMealsItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
          return PopularMealViewHolder(PopularMealsItemBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun getItemCount(): Int {
         return mealsList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
         Glide.with(holder.itemView)
             .load(mealsList[position].strMealThumb)
             .into(holder.binding.popularMealItem)

        holder.binding.popularMealTextView.text = mealsList[position].strMeal

        holder.itemView.setOnClickListener {
             onItemClick.invoke(mealsList[position])
        }
    }
}