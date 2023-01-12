package br.digitalhouse.pokedex.ui.dashboard.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import br.digitalhouse.pokedex.databinding.ActivityNetWorkReceiverBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NetWorkReceiverActivity : AppCompatActivity() {
    private val binding: ActivityNetWorkReceiverBinding by lazy { ActivityNetWorkReceiverBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}