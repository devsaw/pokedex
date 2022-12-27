package br.digitalhouse.pokedex.menu.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.digitalhouse.pokedex.databinding.ActivityPerfilBinding
import com.google.firebase.auth.FirebaseAuth

class PerfilActivity : AppCompatActivity() {
    private val binding: ActivityPerfilBinding by lazy { ActivityPerfilBinding.inflate(layoutInflater) }
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener{
            finish()
        }

        binding.btnAlterar.setOnClickListener{
            finish()
        }
    }

    private fun changeName(){
        binding.nomeClient
    }
}