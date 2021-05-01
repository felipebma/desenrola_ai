package com.example.desenrolaai

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.desenrolaai.databinding.ActivityMainBinding
import com.example.desenrolaai.firebase.Firebase
import com.example.desenrolaai.screens.home.HomeFragment


class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var firebase : Firebase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        firebase = Firebase()

        drawerLayout = binding.mainDrawerLayout
        var navView = binding.mainDrawerNavView

        navView.menu.findItem(R.id.logoutMenu).setOnMenuItemClickListener{ menuItem ->
            finish()
            firebase.singOut()
            startActivity(Intent(this, LoginActivity::class.java))
            true
        }

        val navController = this.findNavController(R.id.mainNavHostFragment)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        NavigationUI.setupWithNavController(binding.mainDrawerNavView, navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.mainNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logoutMenu -> {
                this.startActivity(Intent(this, HomeFragment::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


}