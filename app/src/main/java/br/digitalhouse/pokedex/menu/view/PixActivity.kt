package br.digitalhouse.pokedex.menu.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.digitalhouse.pokedex.databinding.ActivityPixBinding
import com.google.firebase.auth.FirebaseAuth

class PixActivity : AppCompatActivity() {
    private val binding: ActivityPixBinding by lazy { ActivityPixBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener{
            finish()
        }

        binding.btnCopy.setOnClickListener{
            copyKey()
            Toast.makeText(this, "Chave copiada!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun copyKey() {

    }
}