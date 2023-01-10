package br.digitalhouse.pokedex.ui.dashboard.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.databinding.FragmentMenuBinding
import br.digitalhouse.pokedex.ui.menu.view.PasswordActivity
import br.digitalhouse.pokedex.ui.menu.view.PerfilActivity
import br.digitalhouse.pokedex.ui.menu.view.PixActivity
import br.digitalhouse.pokedex.ui.signin.view.SignInHostActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

class MenuFragment : Fragment() {
    private val binding: FragmentMenuBinding by lazy { FragmentMenuBinding.inflate(layoutInflater) }
    private var auth: FirebaseAuth? = null
    private lateinit var alertDialog: AlertDialog
    private lateinit var firebaseStorage: FirebaseStorage

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
        firebaseStorage = FirebaseStorage.getInstance()
        showButton()
        setOnClickListener()
    }

    override fun onResume() {
        super.onResume()
        setImageClient()
    }

    private fun showButton() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user!!.email.isNullOrEmpty()){
            binding.meuPerfil.visibility = View.GONE
            binding.alterarSenha.visibility = View.GONE
            binding.desativarConta.visibility = View.GONE
            binding.appCompatImageView.visibility = View.GONE
            binding.imageViewClient.visibility = View.GONE
            binding.currentUserNull.visibility = View.VISIBLE
        }
    }

    private fun setOnClickListener() {
        binding.meuPerfil.setOnClickListener {
            startActivity(Intent(requireContext(), PerfilActivity::class.java))

        }

        binding.alterarSenha.setOnClickListener {
            startActivity(Intent(requireContext(), PasswordActivity::class.java))
        }

        binding.contribuaPix.setOnClickListener {
            startActivity(Intent(requireContext(), PixActivity::class.java))
        }

        binding.desativarConta.setOnClickListener {
            val build = AlertDialog.Builder(requireContext(), R.style.ThemeCustomDialog)
            val view = layoutInflater.inflate(R.layout.alert_dialog_remove_acc, null)
            build.setView(view)
            val btnNo = view.findViewById<TextView>(R.id.no)
            val btnYes = view.findViewById<TextView>(R.id.yes)
            val textPassDialog = view.findViewById<EditText>(R.id.passDialog)
            btnNo.setOnClickListener { alertDialog.dismiss() }
            btnYes.setOnClickListener {
                if (textPassDialog.text.toString().isNullOrEmpty()){
                    Toast.makeText(requireContext(), "Digite a senha!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val user = auth!!.currentUser
                val credential = EmailAuthProvider.getCredential(user!!.email!!, textPassDialog.text.toString())
                if (user != null && user.email != null) {
                    user.reauthenticate(credential).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            user.delete()
                            Toast.makeText(requireContext(), "Sua conta foi excluída! Esperamos te ver novamente em breve!", Toast.LENGTH_LONG).show()
                            alertDialog.dismiss()
                            finishAffinity(requireActivity())
                        } else {
                            Toast.makeText(requireContext(), "Senha incorreta!", Toast.LENGTH_SHORT).show()
                        }
                    }
                    alertDialog.dismiss()
                }else{
                    Toast.makeText(requireContext(), "Não é possível alterar no momento!", Toast.LENGTH_SHORT).show()
                }
            }
            alertDialog = build.create()
            alertDialog.show()

        }

        binding.sair.setOnClickListener {
            val build = AlertDialog.Builder(requireContext(), R.style.ThemeCustomDialog)
            val view = layoutInflater.inflate(R.layout.alert_dialog, null)
            build.setView(view)
            val btnNo = view.findViewById<TextView>(R.id.no)
            val btnYes = view.findViewById<TextView>(R.id.yes)
            val textAlertDialog = view.findViewById<TextView>(R.id.textAlertDialog)
            textAlertDialog.setText("Deseja sair da PokeDex?")
            btnNo.setOnClickListener { alertDialog.dismiss() }
            btnYes.setOnClickListener {
                alertDialog.dismiss()
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(requireContext(), "Você saiu!", Toast.LENGTH_LONG).show()
                startActivity(Intent(requireContext(), SignInHostActivity::class.java))
                // finishAffinity(requireActivity())
            }
            alertDialog = build.create()
            alertDialog.show()
        }
    }

    private fun setImageClient() {
        try {
            firebaseStorage.reference.child("usuarios/").child(auth!!.currentUser!!.uid)
                .downloadUrl.addOnSuccessListener { uri ->
                    Glide
                        .with(requireContext())
                        .load(uri)
                        .error(R.drawable.ash)
                        .into(binding.imageViewClient)
                }
        }catch (e: Exception){
            Log.e("STORAGE", "USUARIO SEM LOGIN")
        }
    }

    override fun onPause() {
        super.onPause()
        requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}

