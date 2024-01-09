package com.example.foodrecipe.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodrecipe.data.Meal
import com.example.foodrecipe.data.MealList
import com.example.foodrecipe.db.MealDatabase
import com.example.foodrecipe.retrofit.retrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchViewModel(
    val mealDatabase: MealDatabase
): ViewModel()  {

    private var searchMealByNameLiveData = MutableLiveData<List<Meal>>()

    fun searchMeal(searchQuery: String) = retrofitInstance.api.searchMealByName(searchQuery).enqueue(
        object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val mealList = response.body()?.meals
                mealList?.let {
                    searchMealByNameLiveData.postValue(mealList!!)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("searchMealByName", t.message.toString())
            }

        }
    )

    fun observeSearchMealByNameLiveData(): LiveData<List<Meal>> {
        return searchMealByNameLiveData
    }
}