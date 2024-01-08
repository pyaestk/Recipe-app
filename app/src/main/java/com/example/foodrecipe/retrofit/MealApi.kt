package com.example.foodrecipe.retrofit

import com.example.foodrecipe.data.CategoryList
import com.example.foodrecipe.data.PopularMealsList
import com.example.foodrecipe.data.MealList
import com.example.foodrecipe.data.MealsByCategory
import com.example.foodrecipe.data.MealsByCategoryList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(
        @Query("i") id: String
    ):Call<MealList>

    //for popular meals item
    @GET("filter.php?")
    fun getPopularMealsItems(
        @Query("c") categoryName: String
    ):Call<PopularMealsList>

    @GET("categories.php")
    fun getCategory(): Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(
        @Query("c") categoryName: String
    ): Call<MealsByCategoryList>

    @GET("search.php")
    fun searchMealByName(
        @Query("s") searchQuery: String
    ): Call<MealList>
}