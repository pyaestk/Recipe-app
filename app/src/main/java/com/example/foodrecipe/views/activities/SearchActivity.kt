package com.example.foodrecipe.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodrecipe.adapter.MealsAdapter
import com.example.foodrecipe.databinding.ActivitySearchBinding
import com.example.foodrecipe.db.MealDatabase
import com.example.foodrecipe.viewModel.SearchViewModel
import com.example.foodrecipe.viewModel.factory.SearchViewModelFactory
import com.example.foodrecipe.views.fragments.HomeFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private lateinit var viewModel: SearchViewModel
    private lateinit var searchAdapter: MealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = SearchViewModelFactory(mealDatabase)
        viewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]
        
        searchAdapter = MealsAdapter()
        binding.searchMealRecycler.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = searchAdapter
        }

        binding.imgSearch.setOnClickListener{
            searchMealsByName()
        }

        observeMealsLiveData()
        onCategoryMealClick()

        //update the recycler
        var searchJob: Job? = null
        binding.edSearchBox.addTextChangedListener {searchQuery ->
        searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchMeal(searchQuery.toString())
            }
        }
    }

    private fun searchMealsByName() {
        val searchQuery = binding.edSearchBox.text.toString()
        if(searchQuery.isNotEmpty()){
            viewModel.searchMeal(searchQuery)
        }
    }

    private fun observeMealsLiveData() {
        viewModel.observeSearchMealByNameLiveData().observe(this) { mealList ->
            searchAdapter.differ.submitList(mealList)
        }
    }

    private fun onCategoryMealClick() {
        searchAdapter.onItemClick = { meal ->
            val intent = Intent(this, MealDetailsActivity::class.java)
            intent.putExtra(HomeFragment.MealID, meal.idMeal)
            intent.putExtra(HomeFragment.MealName, meal.strMeal)
            intent.putExtra(HomeFragment.MealImage, meal.strMealThumb)
            startActivity(intent)
        }
    }
}