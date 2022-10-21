package com.example.udacityfirstproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.udacityfirstproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()  {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var config: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)

        val navHostFragment=supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController=navHostFragment.findNavController()
        setSupportActionBar(binding.toolbar)
        config= AppBarConfiguration.Builder(R.id.loginFragment,R.id.welcomeFragment,R.id.instructionFragment,R.id.showListFragment).build()
        setupActionBarWithNavController(navController,config)
    }



    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(config) || super.onSupportNavigateUp()
    }

}