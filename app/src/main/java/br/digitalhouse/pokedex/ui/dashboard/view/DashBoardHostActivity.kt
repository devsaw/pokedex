package br.digitalhouse.pokedex.ui.dashboard.view

import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.data.utils.NetworkReceiver
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

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.auth_main_enter, R.anim.auth_main_exit)
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(NetworkReceiver(), intentFilter)
    }
}