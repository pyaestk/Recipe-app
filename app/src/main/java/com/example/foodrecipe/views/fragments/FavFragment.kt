package com.example.foodrecipe.views.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodrecipe.R
import com.example.foodrecipe.adapter.FavMealsAdapter
import com.example.foodrecipe.databinding.FragmentFavBinding
import com.example.foodrecipe.viewModel.HomeViewModel
import com.example.foodrecipe.views.activities.MainActivity

class FavFragment : Fragment() {
    private lateinit var binding: FragmentFavBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favMealsAdapter: FavMealsAdapter
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

        favMealsAdapter = FavMealsAdapter()
        binding.FavRecycler.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favMealsAdapter
        }
        observeFav()
    }

    fun observeFav(){
        viewModel.observeFavoriteMealsLiveData().observe(requireActivity()) { meals ->
             favMealsAdapter.differ.submitList(meals)
        }
    }
}