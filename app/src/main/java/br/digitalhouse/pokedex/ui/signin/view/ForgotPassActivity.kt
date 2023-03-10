package br.digitalhouse.pokedex.ui.signin.view

import android.app.Activity
import android.content.Context
import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.ui.signin.model.User
import br.digitalhouse.pokedex.data.utils.ConfigFirebase
import br.digitalhouse.pokedex.data.utils.NetworkReceiver
import br.digitalhouse.pokedex.databinding.ActivityForgotPassBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import java.lang.Exception

class ForgotPassActivity : AppCompatActivity(R.layout.activity_forgot_pass) {
    private val binding: ActivityForgotPassBinding by lazy { ActivityForgotPassBinding.inflate(layoutInflater) }
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.progressBar.visibility = View.GONE
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btForgot.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            auth = ConfigFirebase().getAuth()
            val textEmail = binding.editTextUserForgot.text.toString()
            val user = User()
            user.email = textEmail

            if (textEmail.isNullOrEmpty()) {
                Toast.makeText(this, "Digite o email!", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                Toast.makeText(this, "Email inválido!", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
                return@setOnClickListener
            }

            auth!!.sendPasswordResetEmail(textEmail).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Verifique seu email: $textEmail", Toast.LENGTH_LONG)
                        .show()
                    binding.progressBar.visibility = View.GONE
                    finish()
                } else {
                    binding.progressBar.visibility = View.GONE
                    var excecao = ""
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthInvalidUserException) {
                        excecao = "Usuário não cadastrado."
                    } catch (e: Exception) {
                        excecao = "Usuário não cadastrado."
                        e.printStackTrace()
                    }
                    Toast.makeText(this, excecao, Toast.LENGTH_LONG).show()
                }
            }
            it.hideKeyboard()
        }
    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.auth_main_enter, R.anim.auth_main_exit)
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(NetworkReceiver(), intentFilter)
    }

}