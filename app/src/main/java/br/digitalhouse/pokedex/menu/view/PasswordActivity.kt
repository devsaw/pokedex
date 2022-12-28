package br.digitalhouse.pokedex.menu.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.digitalhouse.pokedex.dashboard.view.DashBoardHostActivity
import br.digitalhouse.pokedex.databinding.ActivityPasswordBinding
import br.digitalhouse.pokedex.signin.model.User
import br.digitalhouse.pokedex.signin.utils.ConfigFirebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import java.lang.Exception

class PasswordActivity : AppCompatActivity() {
    private val binding: ActivityPasswordBinding by lazy { ActivityPasswordBinding.inflate(layoutInflater) }
    private var auth: FirebaseAuth? = null
    private var credential: AuthCredential? = null
    private var user: User? = null

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

        binding.btnChangePass.setOnClickListener{
            if (binding.editTextPassChange.text.isNullOrEmpty() || binding.editTwoTextPassChange.text.isNullOrEmpty() || binding.editTextConfirmChange.text.isNullOrEmpty()){
                Toast.makeText(this, "Preencha os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.editTwoTextPassChange != binding.editTextConfirmChange){
                Toast.makeText(this, "Senhas diferentes!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            user = User()
            user!!.senha = binding.editTextPassChange.text.toString()
           // validPass(user!!.senha!!)
        }
    }

    private fun validPass(senha: String) {
        binding.progressBar.visibility = View.VISIBLE
        val firebaseAutt = FirebaseAuth.getInstance().currentUser
        firebaseAutt!!.updatePassword()
        auth = ConfigFirebase().getAuth()
        auth!!.updateCurrentUser(senha).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Senha alterada!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                binding.progressBar.visibility = View.GONE
                var excecao = ""
                try {
                    throw task.exception!!
                }  catch (e: FirebaseAuthInvalidCredentialsException) {
                    excecao = "Antiga senha incorreta!"
                } catch (e: Exception) {
                    excecao = "Erro ao alterar senha!"
                    e.printStackTrace()
                }
                Toast.makeText(this, excecao, Toast.LENGTH_SHORT).show()
            }
        }
    }
}