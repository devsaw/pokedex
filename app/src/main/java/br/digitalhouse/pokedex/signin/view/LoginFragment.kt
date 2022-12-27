package br.digitalhouse.pokedex.signin.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.digitalhouse.pokedex.dashboard.view.DashBoardHostActivity
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.signin.model.User
import br.digitalhouse.pokedex.signin.utils.ConfigFirebase
import br.digitalhouse.pokedex.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import java.lang.Exception

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding: FragmentLoginBinding by lazy { FragmentLoginBinding.inflate(layoutInflater) }
    private var autentication: FirebaseAuth? = null
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.GONE
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btLogin.setOnClickListener{
            val textEmail = binding.loginEditText.text.toString()
            val textPass = binding.passwordEditText.text.toString()

            if (textEmail.isNullOrEmpty() || textPass.isNullOrEmpty()){
                Toast.makeText(requireContext(), "Preencha os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            user = User()
            user!!.email = textEmail
            user!!.senha = textPass
            validLogin(user!!.email!!, user!!.senha!!)
        }

        binding.btnIntentCell.setOnClickListener{
            startActivity(Intent(requireContext(), SmsActivity::class.java))
        }

        binding.textViewPass.setOnClickListener{
            startActivity(Intent(requireContext(), ForgotPassActivity::class.java))
        }

        binding.btGoogle.setOnClickListener{

        }
    }

    private fun validLogin(email: String, password: String) {
        binding.progressBar.visibility = View.VISIBLE
        autentication = ConfigFirebase().getAuth()
        autentication!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Logado!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), DashBoardHostActivity::class.java))
            } else {
                binding.progressBar.visibility = View.GONE
                var excecao = ""
                try {
                    throw task.exception!!
                } catch (e: FirebaseAuthInvalidUserException) {
                    excecao = "Usuário inválido!."
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    excecao = "Email ou senha não correspondem a um usuário cadastrado!"
                } catch (e: Exception) {
                    excecao = "Erro ao fazer Login!"
                    e.printStackTrace()
                }
                Toast.makeText(requireContext(), excecao, Toast.LENGTH_SHORT).show()
            }
        }
    }
}