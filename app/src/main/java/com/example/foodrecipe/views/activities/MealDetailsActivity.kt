package com.example.foodrecipe.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodrecipe.databinding.ActivityMealDetailsBinding
import com.example.foodrecipe.viewModel.MealDetailViewModel
import com.example.foodrecipe.views.fragments.HomeFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class MealDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealDetailsBinding
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealImg: String

    private lateinit var mealMvvm: MealDetailViewModel

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm = ViewModelProvider(this).get(MealDetailViewModel::class.java)

        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MealID)!!
        mealName = intent.getStringExtra(HomeFragment.MealName)!!
        mealImg = intent.getStringExtra(HomeFragment.MealImage)!!

        Glide.with(applicationContext)
            .load(mealImg)
            .into(binding.mealDetailImageView)

        binding.collapsingToolbar.title = mealName

        loadingDetails()

        mealMvvm.getMealDetails(mealId)

        observerMealDetailsLiveData()
        
    }

    fun observerMealDetailsLiveData() {
        mealMvvm.observeMealDetailsLiveData().observe(this) { meal ->
            binding.mealCategoryDetailsTextView.text = " Category: ${meal.strCategory}"
            binding.mealAreaDetailsTextView.text = " Area: ${meal.strArea}"
            binding.mealRecipeDescriptionTextView.text = meal.strInstructions

            
            onResponseDetails()
        }
    }

    fun loadingDetails() {
        binding.progressBar.visibility = View.VISIBLE
        binding.likedButton.visibility = View.INVISIBLE
        binding.mealAreaDetailsTextView.visibility = View.INVISIBLE
        binding.mealCategoryDetailsTextView.visibility = View.INVISIBLE
        binding.mealRecipeDescriptionTextView.visibility = View.INVISIBLE
        binding.textViewInstruction.visibility = View.INVISIBLE
    }

    fun onResponseDetails() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.likedButton.visibility = View.VISIBLE
        binding.mealAreaDetailsTextView.visibility = View.VISIBLE
        binding.mealCategoryDetailsTextView.visibility = View.VISIBLE
        binding.mealRecipeDescriptionTextView.visibility = View.VISIBLE
        binding.textViewInstruction.visibility = View.VISIBLE
    }
}