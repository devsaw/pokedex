package br.digitalhouse.pokedex.SignIn

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.digitalhouse.pokedex.DashBoard.DashBoardHostActivity
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding: FragmentLoginBinding by lazy { FragmentLoginBinding.inflate(layoutInflater) }

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
    }

    private fun setOnClickListener() {
        binding.btLogin.setOnClickListener{
            startActivity(Intent(requireContext(), DashBoardHostActivity::class.java))
        }

        binding.btnIntentCell.setOnClickListener{
            startActivity(Intent(requireContext(), SmsActivity::class.java))
        }
    }

}