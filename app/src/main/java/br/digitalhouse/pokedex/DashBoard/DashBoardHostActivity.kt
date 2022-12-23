package br.digitalhouse.pokedex.DashBoard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.digitalhouse.pokedex.databinding.ActivityDashBoardHostBinding

class DashBoardHostActivity : AppCompatActivity() {
    private val binding: ActivityDashBoardHostBinding by lazy { ActivityDashBoardHostBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}