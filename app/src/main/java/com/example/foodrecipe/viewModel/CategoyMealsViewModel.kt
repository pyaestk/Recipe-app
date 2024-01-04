package com.example.foodrecipe.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodrecipe.data.Meal
import com.example.foodrecipe.data.MealsByCategory
import com.example.foodrecipe.data.MealsByCategoryList
import com.example.foodrecipe.retrofit.retrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CategoryMealsViewModel: ViewModel() {

    private var mealsLiveData = MutableLiveData<List<MealsByCategory>>()
    
    fun getMealsByCategory(categoryName: String) {
        retrofitInstance.api.getMealsByCategory(categoryName).enqueue(object :
            Callback<MealsByCategoryList> {
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                response.body()?.let { mealList ->
                    mealsLiveData.postValue(mealList.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("MealCategoryActivity", t.message.toString())
            }

        })
    }

    fun observeCategoryMealsLiveData(): MutableLiveData<List<MealsByCategory>> {
        return mealsLiveData
    }


}