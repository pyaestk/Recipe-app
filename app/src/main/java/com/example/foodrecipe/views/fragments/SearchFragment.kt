package com.example.foodrecipe.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipe.R
import com.example.foodrecipe.adapter.MealsAdapter
import com.example.foodrecipe.databinding.FragmentSearchBinding
import com.example.foodrecipe.retrofit.MealApi
import com.example.foodrecipe.viewModel.HomeViewModel
import com.example.foodrecipe.views.activities.MainActivity
import com.example.foodrecipe.views.activities.MealDetailsActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private lateinit var viewModel: HomeViewModel
    private lateinit var searchAdapter: MealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        binding.edSearchBox.addTextChangedListener { searchQuery ->
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
        viewModel.observeSearchMealByNameLiveData().observe(viewLifecycleOwner) { mealList ->
            searchAdapter.differ.submitList(mealList)
        }
    }

    private fun onCategoryMealClick() {
        searchAdapter.onItemClick = { meal ->
            val intent = Intent(requireContext(), MealDetailsActivity::class.java)
            intent.putExtra(HomeFragment.MealID, meal.idMeal)
            intent.putExtra(HomeFragment.MealName, meal.strMeal)
            intent.putExtra(HomeFragment.MealImage, meal.strMealThumb)
            startActivity(intent)
        }
    }
}