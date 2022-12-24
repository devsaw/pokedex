package br.digitalhouse.pokedex.SignIn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.databinding.ActivityForgotPassBinding
import br.digitalhouse.pokedex.databinding.ActivitySmsBinding

class ForgotPassActivity : AppCompatActivity(R.layout.activity_forgot_pass) {
    private val binding: ActivityForgotPassBinding by lazy { ActivityForgotPassBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener{
            finish()
        }

        binding.btForgot.setOnClickListener{
            finish()
        }
    }
}