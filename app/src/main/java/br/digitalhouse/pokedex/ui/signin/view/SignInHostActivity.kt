package br.digitalhouse.pokedex.ui.signin.view

import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.data.utils.NetworkReceiver
import br.digitalhouse.pokedex.ui.signin.adapter.SignInAdapter
import br.digitalhouse.pokedex.databinding.ActivitySignInHostBinding
import com.google.android.material.tabs.TabLayoutMediator

class SignInHostActivity : AppCompatActivity() {
    private val binding: ActivitySignInHostBinding by lazy { ActivitySignInHostBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initBottomNav()
    }

    private fun initBottomNav() {
        val tabLayout = binding.myTabLayout
        val viewPager = binding.viewPagerTab
        val adapterTab = SignInAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapterTab

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Logar"
                }
                1 -> {
                    tab.text = "Registrar"
                }
            }
        }.attach()
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