package br.digitalhouse.pokedex.ui.dashboard.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.databinding.FragmentHomeBinding
import br.digitalhouse.pokedex.data.utils.Base64Custom
import br.digitalhouse.pokedex.data.utils.ConfigFirebase
import br.digitalhouse.pokedex.data.utils.Preferences
import br.digitalhouse.pokedex.ui.dashboard.adapter.HomeAdapter
import br.digitalhouse.pokedex.ui.dashboard.viewmodel.PokemonViewModel
import br.digitalhouse.pokedex.ui.signin.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val pokemonViewModel: PokemonViewModel by viewModels()
    private val binding: FragmentHomeBinding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private var auth: FirebaseAuth? = null
    lateinit var preferences: Preferences
    private lateinit var pokeAdapter: HomeAdapter
    private var valueEventListener: ValueEventListener? = null
    private val firebaseRef = ConfigFirebase().getFirebase()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return (binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        preferences = Preferences(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        adapter()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnNotify.setOnClickListener{
            startActivity(Intent(requireContext(), NotifyActivity::class.java))
        }
    }

    private fun adapter(){
        pokeAdapter = HomeAdapter(requireContext(), onItemClicked = { name, num, image, height, weight, type, weaknesses, prevevo, nextevo ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("num", num)
            intent.putExtra("image", image)
            intent.putExtra("height", height)
            intent.putExtra("weight", weight)
            intent.putExtra("type", type)
            intent.putExtra("weaknesses", weaknesses)
            intent.putExtra("prevevo", prevevo)
            intent.putExtra("nextevo", nextevo)
            startActivity(intent)
        })
        requireView().findViewById<RecyclerView>(R.id.rvList).adapter = pokeAdapter

        lifecycleScope.launch{
            val feed = pokemonViewModel.fetchPokemon()
            pokeAdapter.update(feed)
        }
    }

    private fun setData(){
        val phoneAuth = auth!!.currentUser!!.phoneNumber
        val emailAuth = auth!!.currentUser!!.email
//        val idUsuario = Base64Custom.codificarBase64(emailAuth)
//        val nameUser = firebaseRef!!.child("usuarios").child(idUsuario)
//        val userClass = User()

//        valueEventListener = nameUser.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val user = snapshot.getValue(User::class.java)
//                binding.textLogin.visibility = View.VISIBLE
//                binding.textLogin.text = user!!.nome
//            }
//            override fun onCancelled(error: DatabaseError) {}
//        })

        if (emailAuth != null && phoneAuth.isNullOrEmpty() && FirebaseAuth.getInstance().currentUser!!.displayName.isNullOrEmpty()){
            binding.textLogin.visibility = View.VISIBLE
            binding.textLogin.text = preferences.getInforUserName()
        } else if (emailAuth.isNullOrEmpty() && phoneAuth!!.isNotEmpty() && FirebaseAuth.getInstance().currentUser!!.displayName.isNullOrEmpty()){
            binding.textPhone.visibility = View.VISIBLE
            binding.textPhone.text = "Mestre Pokémon"
        } else if (emailAuth != null && phoneAuth.isNullOrEmpty() && FirebaseAuth.getInstance().currentUser!!.displayName!!.isNotEmpty()){
            binding.textGoogle.visibility = View.VISIBLE
            binding.textGoogle.text = FirebaseAuth.getInstance().currentUser!!.displayName.toString()
        } else{
            binding.textLogin.visibility = View.VISIBLE
            binding.textLogin.text = "Mestre Pokémon"
        }
    }

    override fun onPause() {
        super.onPause()
        requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

}