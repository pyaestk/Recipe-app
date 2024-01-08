package com.example.foodrecipe.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodrecipe.db.MealDatabase
import com.example.foodrecipe.viewModel.MealDetailViewModel

class MealViewModelFactory(private val mealDatabase: MealDatabase): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealDetailViewModel(mealDatabase) as T
    }
}