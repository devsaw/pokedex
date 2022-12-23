package br.digitalhouse.pokedex.DashBoard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.digitalhouse.pokedex.databinding.ActivityDashBoardBinding

class DashBoardHostActivity : AppCompatActivity() {
    private val binding: ActivityDashBoardBinding by lazy { ActivityDashBoardBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}