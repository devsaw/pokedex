package br.digitalhouse.pokedex.ui.dashboard.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.databinding.ActivityDashBoardHostBinding

class DashBoardHostActivity : AppCompatActivity() {
    private val binding: ActivityDashBoardHostBinding by lazy { ActivityDashBoardHostBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initBottomNavDashBoard()
    }

    private fun initBottomNavDashBoard(){
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container)
        setupWithNavController(binding.bottomNavigation, navController)
    }
}