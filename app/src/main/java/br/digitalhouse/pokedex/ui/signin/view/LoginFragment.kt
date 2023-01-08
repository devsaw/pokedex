package br.digitalhouse.pokedex.ui.signin.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import br.digitalhouse.pokedex.ui.dashboard.view.DashBoardHostActivity
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.ui.signin.model.User
import br.digitalhouse.pokedex.data.utils.ConfigFirebase
import br.digitalhouse.pokedex.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.GoogleAuthProvider
import java.lang.Exception

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding: FragmentLoginBinding by lazy { FragmentLoginBinding.inflate(layoutInflater) }
    private var autentication: FirebaseAuth? = null
    private var auth: FirebaseAuth? = null
    private lateinit var googleSignInClient: GoogleSignInClient
    private var user: User? = null

    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResult(task)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding.progressBar.visibility = View.GONE
        setOnClickListener()
        googleSignIn()
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
            val signIntent = googleSignInClient.signInIntent
            launcher.launch(signIntent)
        }
    }

    private fun validLogin(email: String, password: String) {
        binding.progressBar.visibility = View.VISIBLE
        autentication = ConfigFirebase().getAuth()
        autentication!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                binding.progressBar.visibility = View.GONE
                if (auth!!.currentUser!!.isEmailVerified){
                    Toast.makeText(requireContext(), "Logado!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireContext(), DashBoardHostActivity::class.java))
                } else{
                    Toast.makeText(requireContext(), "Verifique seu e-mail!", Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.progressBar.visibility = View.GONE
                var excecao = ""
                try {
                    throw task.exception!!
                } catch (e: FirebaseAuthInvalidUserException) {
                    excecao = "Usuário inválido!"
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

    private fun googleSignIn(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        googleSignInClient.signOut()
    }

    private fun handleResult(task : Task<GoogleSignInAccount>){
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }
        }else{
            Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth!!.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful){
                startActivity(Intent(requireContext(), DashBoardHostActivity::class.java))
            }else{
                Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}