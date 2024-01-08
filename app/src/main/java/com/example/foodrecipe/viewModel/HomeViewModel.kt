package com.example.foodrecipe.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipe.data.Category
import com.example.foodrecipe.data.CategoryList
import com.example.foodrecipe.data.PopularMealsList
import com.example.foodrecipe.data.PopularMeals
import com.example.foodrecipe.data.Meal
import com.example.foodrecipe.data.MealList
import com.example.foodrecipe.db.MealDatabase
import com.example.foodrecipe.retrofit.retrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealDatabase: MealDatabase
): ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()

    private var popularMealsLiveData = MutableLiveData<List<PopularMeals>>()

    private var categoryMealsLiveData = MutableLiveData<List<Category>>()

    private var favoriteMealLiveData = mealDatabase.mealDao().getAllMeals()

    private var searchMealByNameLiveData = MutableLiveData<List<Meal>>()
    

    //random meals heading
    fun getRandomMeal(){
        retrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                    Log.d("Test", "meal id ${randomMeal.idMeal} name ${randomMeal.strMeal}")

                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HFR", t.message.toString())
            }

        })
    }

    fun observeRandomMealLiveData(): LiveData<Meal>{
        return randomMealLiveData
    }

    //popular meals lists
    fun getPopularItems() {
        retrofitInstance.api.getPopularMealsItems("Seafood").enqueue(object : Callback<PopularMealsList> {
            override fun onResponse(call: Call<PopularMealsList>, response: Response<PopularMealsList>) {
                if(response.body() != null) {
                    popularMealsLiveData.value = response.body()!!.meals
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<PopularMealsList>, t: Throwable) {
                Log.d("HFP", t.message.toString())
            }

        })
    }

    fun observePopularMealsLiveData(): LiveData<List<PopularMeals>> {
        return popularMealsLiveData
    }

    //for category home view
    fun getCategories(){
        retrofitInstance.api.getCategory().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
//                if (response.body() != null) {
//                    categoryMealsLiveData.value = response.body()!!.categories
//                } else {
//                    return
//                }
                response.body()?.let { categoryList ->
                    categoryMealsLiveData.postValue(categoryList.categories)
                }

            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HVM", t.message.toString())
            }

        })
    }

    fun observeCategoryLiveData(): LiveData<List<Category>> {
        return categoryMealsLiveData
    }

    //for room database livedata
    fun observeFavoriteMealsLiveData(): LiveData<List<Meal>> {
        return favoriteMealLiveData
    }
    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().insertUpdateMeal(meal)
        }
    }
    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().deleteMeal(meal)
        }
    }

    //search meal by name
    fun searchMeal(searchQuery: String) = retrofitInstance.api.searchMealByName(searchQuery).enqueue(
        object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val mealList = response.body()?.meals
                mealList?.let {
                    searchMealByNameLiveData.postValue(mealList)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("searchMealByName", t.message.toString())
            }

        }
    )

    fun observeSearchMealByNameLiveData(): LiveData<List<Meal>>{
        return searchMealByNameLiveData
    }
}