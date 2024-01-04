package com.example.foodrecipe.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodrecipe.data.Meal
import com.example.foodrecipe.data.MealList
import com.example.foodrecipe.retrofit.retrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDetailViewModel: ViewModel() {
    private var mealDetailsLiveData = MutableLiveData<Meal>()

    fun getMealDetails(id: String) {
        retrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body()!=null){
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                }
                else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MealDetailActivity", t.message.toString())
            }

        })
    }

    fun observeMealDetailsLiveData(): LiveData<Meal> {
        return mealDetailsLiveData
    }
}