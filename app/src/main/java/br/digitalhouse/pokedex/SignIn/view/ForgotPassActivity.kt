package br.digitalhouse.pokedex.SignIn.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.SignIn.model.User
import br.digitalhouse.pokedex.SignIn.utils.ConfigFirebase
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
        binding.btnBack.setOnClickListener{
            finish()
        }

        binding.btForgot.setOnClickListener{
            binding.progressBar.visibility = View.VISIBLE
            auth = ConfigFirebase().getAuth()
            val textEmail = binding.editTextUserForgot.text.toString()
            val user = User()
            user.email = textEmail

            if (textEmail.isNullOrEmpty()){
                Toast.makeText(this, "Digite o email!", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
                return@setOnClickListener
            }

            if (!textEmail.contains("@")){
                Toast.makeText(this, "Email inválido!", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
                return@setOnClickListener
            }

            auth!!.sendPasswordResetEmail(textEmail).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Verifique seu email: $textEmail", Toast.LENGTH_LONG).show()
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
        }
    }
}