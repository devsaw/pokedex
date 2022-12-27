package br.digitalhouse.pokedex.menu.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.digitalhouse.pokedex.databinding.ActivityPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class PasswordActivity : AppCompatActivity() {
    private val binding: ActivityPasswordBinding by lazy { ActivityPasswordBinding.inflate(layoutInflater) }
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}