package br.digitalhouse.pokedex.ui.dashboard.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.digitalhouse.pokedex.databinding.ActivityNotifyBinding

class NotifyActivity : AppCompatActivity() {
    private val binding: ActivityNotifyBinding by lazy { ActivityNotifyBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener{
            finish()
        }
    }
}