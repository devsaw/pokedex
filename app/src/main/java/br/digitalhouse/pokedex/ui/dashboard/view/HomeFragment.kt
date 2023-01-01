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
import com.google.firebase.auth.FirebaseAuth
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

        }
    }

    private fun adapter(){
        pokeAdapter = HomeAdapter(requireContext(), onItemClicked = { name, num, image, height, weight ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("num", num)
            intent.putExtra("image", image)
            intent.putExtra("height", height)
            intent.putExtra("weight", weight)
//            intent.putExtra("type", type)
//            intent.putExtra("weaknesses", weaknesses)
//            intent.putExtra("prevEvo", prevEvo)
//            intent.putExtra("nextEvoEvo", nextEvo)
            startActivity(intent)
        })
        requireView().findViewById<RecyclerView>(R.id.rvList).adapter = pokeAdapter

        lifecycleScope.launch{
            val feed = pokemonViewModel.fetchPokemon()
            pokeAdapter.update(feed)
        }
    }

    private fun setData(){
        val phone = auth!!.currentUser!!.phoneNumber
        val userAuth = auth!!.currentUser
        val idUsuario = Base64Custom.codificarBase64(userAuth!!.email)
        val nameUser = firebaseRef!!.child("usuarios").child(idUsuario)

//        valueEventListener = nameUser.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val user = snapshot.getValue(User::class.java)
//                binding.textLogin.visibility = View.VISIBLE
//                binding.textLogin.text = user!!.nome
//            }
//            override fun onCancelled(error: DatabaseError) {}
//        })

            if (userAuth.email != null && phone.isNullOrEmpty()){
            binding.textGoogle.visibility = View.VISIBLE
            binding.textGoogle.text = FirebaseAuth.getInstance().currentUser!!.displayName.toString()
        } else if (phone!!.isNotEmpty()){
            binding.textPhone.visibility = View.VISIBLE
            binding.textPhone.text = "Mestre Pokémon"
        } else {
            binding.textLogin.visibility = View.VISIBLE
            binding.textLogin.text = preferences.getInforUserName()
        }
    }
}