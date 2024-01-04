package com.example.foodrecipe.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodrecipe.adapter.CategoryAdapter
import com.example.foodrecipe.adapter.PopularMealAdapter
import com.example.foodrecipe.data.PopularMeals
import com.example.foodrecipe.data.Meal
import com.example.foodrecipe.databinding.FragmentHomeBinding
import com.example.foodrecipe.viewModel.HomeViewModel
import com.example.foodrecipe.views.activities.CategoryMealsActivity
import com.example.foodrecipe.views.activities.MealDetailsActivity

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMVVM: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularMealsAdapter: PopularMealAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    companion object{
        const val MealID = "meal.id"
        const val MealName = "meal.name"
        const val MealImage = "meal.image"
        const val MealVid = "meal.vid"

        const val Category_name = "category.name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMVVM = ViewModelProvider(this).get(HomeViewModel::class.java)
        popularMealsAdapter = PopularMealAdapter()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.homeFragmentConstraintsLayout.visibility = View.INVISIBLE
        binding.progressBarHome.visibility = View.VISIBLE

        //for Popular meal Adapter
        binding.recyclerMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularMealsAdapter
        }

        //for Category meal Adapter
        categoryAdapter = CategoryAdapter()
        binding.recyclerCategories.apply {
            layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }

        //for random meal heading
        homeMVVM.getRandomMeal()

        observerRandomMeal()
        onRandomMealClick()

        //for popular meal list
        homeMVVM.getPopularItems()
        observerPopularItems()
        onPopularMealClick()

        //for category home vie
        homeMVVM.getCategories()
        observeCategoryItems()
        onCategoryClick()
    }

    fun observerRandomMeal() {
        homeMVVM.observeRandomMealLiveData().observe(viewLifecycleOwner) { meal ->
            
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.randomMealImageView)
            binding.randomRecipeTextView.text = meal.strMeal
            this.randomMeal = meal

            binding.homeFragmentConstraintsLayout.visibility = View.VISIBLE
            binding.progressBarHome.visibility = View.INVISIBLE
        }
    }

    fun onRandomMealClick() {
        binding.randomMealCardView.setOnClickListener {
            val intent = Intent(activity, MealDetailsActivity::class.java)
            intent.putExtra(MealID, randomMeal.idMeal)
            intent.putExtra(MealName, randomMeal.strMeal)
            intent.putExtra(MealImage, randomMeal.strMealThumb)
            intent.putExtra(MealVid, randomMeal.strYoutube)
            startActivity(intent)
        }
    }

    //for popular meals list
    fun observerPopularItems() {
         homeMVVM.observePopularMealsLiveData().observe(viewLifecycleOwner) { meal ->
              popularMealsAdapter.setMeals(meal as ArrayList<PopularMeals>)
         }
    }

    fun onPopularMealClick() {
        popularMealsAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealDetailsActivity::class.java)
            intent.putExtra(MealID, meal.idMeal)
            intent.putExtra(MealName, meal.strMeal)
            intent.putExtra(MealImage, meal.strMealThumb)
            startActivity(intent)
        }
    }

    //for category
    fun observeCategoryItems() {
        homeMVVM.observeCategoryLiveData().observe(viewLifecycleOwner) { categories ->
            categoryAdapter.setCategories(categories)
        }
    }

    fun onCategoryClick() {
        categoryAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(Category_name, category.strCategory)
            startActivity(intent)
        }
    }
}