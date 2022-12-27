package br.digitalhouse.pokedex.DashBoard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.digitalhouse.pokedex.SignIn.model.User
import br.digitalhouse.pokedex.databinding.FragmentMenuBinding
import com.google.firebase.auth.FirebaseAuth

class MenuFragment : Fragment() {
    private val binding: FragmentMenuBinding by lazy { FragmentMenuBinding.inflate(layoutInflater) }
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

        }

        binding.sair.setOnClickListener{

        }
    }

    private fun setImageClient(){
        binding.imageViewClient
    }
}