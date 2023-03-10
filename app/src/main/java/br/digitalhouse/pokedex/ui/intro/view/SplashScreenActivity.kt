package br.digitalhouse.pokedex.ui.intro.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import br.digitalhouse.pokedex.ui.dashboard.view.DashBoardHostActivity
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.data.utils.ConfigFirebase
import br.digitalhouse.pokedex.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private val binding: ActivitySplashScreenBinding by lazy { ActivitySplashScreenBinding.inflate(layoutInflater) }
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        splashFun()
    }

    private fun splashFun() {
        val logo: ImageView = findViewById(R.id.logoSplash)
        logo.alpha=0f
        logo.animate().setDuration(3000).alpha(1f).withEndAction{
                auth = ConfigFirebase().getAuth()

                if (auth!!.currentUser != null) {
                    startActivity(Intent(this, DashBoardHostActivity::class.java))
                } else {
                    startActivity(Intent(this, SlideHostActivity::class.java))
                }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.auth_main_enter, R.anim.auth_main_exit)
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}