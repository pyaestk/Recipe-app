package com.example.foodrecipe.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("mealInformation")
data class Meal(
    val dateModified: Any?,
    @PrimaryKey
    val idMeal: String,
    val strArea: String?,
    val strCategory: String?,
    val strCreativeCommonsConfirmed: Any?,
    val strDrinkAlternate: Any?,
    val strImageSource: Any?,
    val strInstructions: String?,
    val strMeal: String?,
    val strMealThumb: String?,
    val strSource: String?,
    val strTags: String?,
    val strYoutube: String?
)