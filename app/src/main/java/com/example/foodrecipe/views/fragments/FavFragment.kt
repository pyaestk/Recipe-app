package com.example.foodrecipe.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipe.R
import com.example.foodrecipe.adapter.MealsAdapter
import com.example.foodrecipe.databinding.FragmentFavBinding
import com.example.foodrecipe.viewModel.HomeViewModel
import com.example.foodrecipe.views.activities.MainActivity
import com.example.foodrecipe.views.activities.MealDetailsActivity
import com.google.android.material.snackbar.Snackbar

class FavFragment : Fragment() {
    private lateinit var binding: FragmentFavBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var mealsAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //adapter
        mealsAdapter = MealsAdapter()
        binding.FavRecycler.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = mealsAdapter
        }
        observeFav()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedmeal = mealsAdapter.differ.currentList[position]
                viewModel.deleteMeal(mealsAdapter.differ.currentList[position])

                Snackbar.make(requireView(), "Meal has been removed from favorites", Snackbar.LENGTH_LONG)
                    .setAction(
                        "Undo", View.OnClickListener {
                            viewModel.insertMeal(deletedmeal)
                        }
                    ).show()
            }

        }

        val touchHelper = ItemTouchHelper(itemTouchHelper)
        touchHelper.attachToRecyclerView(binding.FavRecycler)

        onFavMealClick()
    }

    fun observeFav(){
        viewModel.observeFavoriteMealsLiveData().observe(requireActivity()) { meals ->
             mealsAdapter.differ.submitList(meals)
        }
    }

    fun onFavMealClick(){
        mealsAdapter.onItemClick = { meal ->
            val intent = Intent(requireContext(), MealDetailsActivity::class.java)
            intent.putExtra(HomeFragment.MealID, meal.idMeal)
            intent.putExtra(HomeFragment.MealName, meal.strMeal)
            intent.putExtra(HomeFragment.MealImage, meal.strMealThumb)
            startActivity(intent)
        }
    }


}