package com.arainko.nawts.fragments

import android.app.TaskStackBuilder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.arainko.nawts.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    NavController.OnDestinationChangedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val navController by lazy { findNavController(navHostFragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController.addOnDestinationChangedListener(this)
        bottomNavigationView.apply {
            setupWithNavController(navController)
            menu.findItem(R.id.navHome).isChecked = true
            setOnNavigationItemSelectedListener(this@MainActivity)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.navCustomization -> {
            navController.navigate(R.id.action_global_customizationFragment)
            true
        }
        R.id.navHome -> {
            navController.navigate(R.id.action_global_mainFragment)
            true
        }
        else -> false
    }


    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) = when (destination.id) {
        R.id.editingFragment -> bottomNavigationView.visibility = View.GONE
        else -> bottomNavigationView.visibility = View.VISIBLE
    }


}
