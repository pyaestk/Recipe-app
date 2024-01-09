package com.example.foodrecipe.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodrecipe.R
import com.example.foodrecipe.adapter.CategoryAdapter
import com.example.foodrecipe.adapter.CategoryMealsAdapter
import com.example.foodrecipe.databinding.FragmentCategoryBinding
import com.example.foodrecipe.viewModel.HomeViewModel
import com.example.foodrecipe.views.activities.CategoryMealsActivity
import com.example.foodrecipe.views.activities.MainActivity

class CategoryFragment : Fragment() {


    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryMvvm: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryMvvm = (activity as MainActivity).viewModel
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryAdapter = CategoryAdapter()
        binding.categoryRecycler.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }

        observeCategoryMeals()
        onCategoryClick()
    }

    fun observeCategoryMeals() {
        categoryMvvm.observeCategoryLiveData().observe(viewLifecycleOwner) { categories ->
            categoryAdapter.setCategories(categories)
        }
    }

    fun onCategoryClick() {
        categoryAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(HomeFragment.Category_name, category.strCategory)
            startActivity(intent)
        }
    }
}