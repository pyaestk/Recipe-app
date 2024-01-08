package com.example.foodrecipe.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodrecipe.adapter.CategoryMealsAdapter
import com.example.foodrecipe.databinding.ActivityCategoryMealsBinding
import com.example.foodrecipe.viewModel.CategoryMealsViewModel
import com.example.foodrecipe.views.fragments.HomeFragment

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var categoryMealsMvvm: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter

    lateinit var categoryName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryMealsMvvm = ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        val intent = intent
        categoryName = intent.getStringExtra(HomeFragment.Category_name)!!

        categoryMealsAdapter = CategoryMealsAdapter()
        binding.categoryMealRecycler.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }

        categoryMealsMvvm.getMealsByCategory(intent.getStringExtra(HomeFragment.Category_name)!!)

        observeCategoryMealsLiveData()
        onPopularMealClick()
    }

    fun observeCategoryMealsLiveData() {
        categoryMealsMvvm.observeCategoryMealsLiveData().observe(this) { mealsList ->
            binding.categoryMealTextView.text = "Category: ${categoryName}"
            binding.mealCount.text = mealsList.size.toString()
            categoryMealsAdapter.setMealsList(mealsList)
        }
    }

    fun onPopularMealClick() {
        categoryMealsAdapter.onItemClick = { meal ->
            val intent = Intent(applicationContext, MealDetailsActivity::class.java)
            intent.putExtra(HomeFragment.MealID, meal.idMeal)
            intent.putExtra(HomeFragment.MealName, meal.strMeal)
            intent.putExtra(HomeFragment.MealImage, meal.strMealThumb)
            startActivity(intent)
        }
    }
}