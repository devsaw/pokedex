package br.digitalhouse.pokedex.menu.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.databinding.ActivityPasswordBinding
import br.digitalhouse.pokedex.signin.view.SignInHostActivity
import com.google.firebase.auth.*

class PasswordActivity : AppCompatActivity() {
    private val binding: ActivityPasswordBinding by lazy { ActivityPasswordBinding.inflate(layoutInflater) }
    private var auth: FirebaseAuth? = null
    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.progressBar.visibility = View.GONE
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnChangePass.setOnClickListener {
            if (binding.editTextPassChange.text.toString().isNullOrEmpty() || binding.editTwoTextPassChange.text.toString().isNullOrEmpty() || binding.editTextConfirmChange.text.toString().isNullOrEmpty()) {
                Toast.makeText(this, "Preencha os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.editTwoTextPassChange.text.toString() != binding.editTextConfirmChange.text.toString()) {
                Toast.makeText(this, "Senhas diferentes!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.editTwoTextPassChange.length() <= 5){
                Toast.makeText(this, "Senha fraca!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            validPass()
        }
    }

    private fun validPass() {
        binding.progressBar.visibility = View.VISIBLE
        val user = auth!!.currentUser
        val credential = EmailAuthProvider.getCredential(user!!.email!!, binding.editTextPassChange.text.toString())
        if (user != null && user.email != null){
            user.reauthenticate(credential).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val build = AlertDialog.Builder(this, R.style.ThemeCustomDialog)
                    val view = layoutInflater.inflate(R.layout.alert_dialog, null)
                    build.setView(view)
                    val btnNo = view.findViewById<TextView>(R.id.no)
                    val btnYes = view.findViewById<TextView>(R.id.yes)
                    val textAlertDialog = view.findViewById<TextView>(R.id.textAlertDialog)
                    textAlertDialog.setText("Tem certeza que deseja \n alterar sua senha?")
                    btnNo.setOnClickListener{ alertDialog.dismiss()
                        binding.progressBar.visibility = View.GONE}
                    btnYes.setOnClickListener{ alertDialog.dismiss()
                        user.updatePassword(binding.editTwoTextPassChange.text.toString()).addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(this, "Senha alterada!", Toast.LENGTH_LONG).show()
                                auth!!.signOut()
                                startActivity(Intent(this, SignInHostActivity::class.java))
                                finish()
                            }else{
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(this, "Não é possível alterar no momento!", Toast.LENGTH_LONG).show()
                            }
                        }}
                    alertDialog = build.create()
                    alertDialog.show()
                    binding.progressBar.visibility = View.GONE
                }else{
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Senha atual incorreta!", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            binding.progressBar.visibility = View.GONE
            Toast.makeText(this, "Erro!", Toast.LENGTH_SHORT).show()
        }
    }
}