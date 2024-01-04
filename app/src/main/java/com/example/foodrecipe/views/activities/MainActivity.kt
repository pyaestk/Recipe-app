package com.example.foodrecipe.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.foodrecipe.R
import com.example.foodrecipe.views.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val navController = findNavController(R.id.host_fragment)
//        findViewById<BottomNavigationView>(R.id.bottomNav).setupWithNavController(navController)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNav)
        val navController = Navigation.findNavController(this, R.id.host_fragment)
        NavigationUI.setupWithNavController(bottomNavigation, navController)

    }
}