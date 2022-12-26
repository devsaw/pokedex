package br.digitalhouse.pokedex.Intro.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import br.digitalhouse.pokedex.R

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val logo: ImageView = findViewById(R.id.logoSplash)
        logo.alpha=0f
        logo.animate().setDuration(3000).alpha(1f).withEndAction{
            val showLogo = Intent(this, SlideHostActivity::class.java)
            startActivity(showLogo)
        }
    }
}