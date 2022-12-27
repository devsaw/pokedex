package br.digitalhouse.pokedex.DashBoard.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.SignIn.model.User
import br.digitalhouse.pokedex.SignIn.view.SignInHostActivity
import br.digitalhouse.pokedex.databinding.FragmentMenuBinding
import com.google.firebase.auth.FirebaseAuth

class MenuFragment : Fragment() {
    private val binding: FragmentMenuBinding by lazy { FragmentMenuBinding.inflate(layoutInflater) }
    private lateinit var alertDialog: AlertDialog
    private var auth: FirebaseAuth? = null
    private var usuario: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
        setImageClient()
    }

    private fun setOnClickListener() {
        binding.meuPerfil.setOnClickListener{

        }

        binding.alterarSenha.setOnClickListener{

        }

        binding.contribuaPix.setOnClickListener{

        }

        binding.desativarConta.setOnClickListener{
            val build = AlertDialog.Builder(requireContext(), R.style.ThemeCustomDialog)
            val view = layoutInflater.inflate(R.layout.alert_dialog, null)
            build.setView(view)
            val btnNo = view.findViewById<TextView>(R.id.no)
            val btnYes = view.findViewById<TextView>(R.id.yes)
            val textAlertDialog = view.findViewById<TextView>(R.id.textAlertDialog)
            textAlertDialog.setText("Tem certeza que deseja \n desativar a sua conta?")
            btnNo.setOnClickListener{ alertDialog.dismiss() }
            btnYes.setOnClickListener{ alertDialog.dismiss()
                Toast.makeText(requireContext(), "Sua conta foi desativada! Esperamos te ver novamente em breve!", Toast.LENGTH_LONG).show()
                startActivity(Intent(requireContext(), SignInHostActivity::class.java))}
            alertDialog = build.create()
            alertDialog.show()

        }

        binding.sair.setOnClickListener{
            val build = AlertDialog.Builder(requireContext(), R.style.ThemeCustomDialog)
            val view = layoutInflater.inflate(R.layout.alert_dialog, null)
            build.setView(view)
            val btnNo = view.findViewById<TextView>(R.id.no)
            val btnYes = view.findViewById<TextView>(R.id.yes)
            val textAlertDialog = view.findViewById<TextView>(R.id.textAlertDialog)
            textAlertDialog.setText("Deseja sair da PokeDex?")
            btnNo.setOnClickListener{ alertDialog.dismiss() }
            btnYes.setOnClickListener{ alertDialog.dismiss()
                Toast.makeText(requireContext(), "Você foi deslogado!", Toast.LENGTH_LONG).show()
                startActivity(Intent(requireContext(), SignInHostActivity::class.java))}
            alertDialog = build.create()
            alertDialog.show()
        }
    }

    private fun setImageClient(){
        binding.imageViewClient
    }
}