package br.digitalhouse.pokedex.SignIn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.SignIn.adapter.SignInAdapter
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

}