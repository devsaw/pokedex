package br.digitalhouse.pokedex.ui.dashboard.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.databinding.FragmentHomeBinding
import br.digitalhouse.pokedex.data.utils.ConfigFirebase
import br.digitalhouse.pokedex.data.utils.Preferences
import br.digitalhouse.pokedex.ui.dashboard.adapter.HomeAdapter
import br.digitalhouse.pokedex.ui.dashboard.viewmodel.PokemonViewModel
import br.digitalhouse.pokedex.ui.signin.model.User
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val pokemonViewModel: PokemonViewModel by viewModels()
    private val binding: FragmentHomeBinding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private var auth: FirebaseAuth? = null
    lateinit var preferences: Preferences
    lateinit var mAdView: AdView
    var textCartItemCount: TextView? = null
    var mCartItemCount = 0
    private lateinit var pokeAdapter: HomeAdapter

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
        adapter()
        setOnClickListener()
        validName()
        bannerAdMob()
        setupBadge()
        setupBadge1()
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

    private fun validName() {
        // executa consulta no banco de dados passando pelos nós do firebase e compara com outro argumento
        val searchParam: Query = FirebaseDatabase.getInstance().getReference("usuarios")
            .child(ConfigFirebase().getIdUser() ?: "")
            .orderByChild("email")
            .equalTo(ConfigFirebase().isAutenticated())

        searchParam.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if (snapshot.exists()) {
                    binding.textLogin.text = user!!.nome
                } else{
                    binding.textLogin.text = "Mestre Pokémon"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("CANCEL", "CONSULTA NO BANCO DO FIREBASE onCancelled")
            }
        })
    }

    private fun bannerAdMob(){
        MobileAds.initialize(requireContext()) {}
        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    override fun onPause() {
        super.onPause()
        requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun setupBadge1() {
        if (preferences.getLogin() == true) {
            if (mCartItemCount == 0) {

                binding.cartBadge.visibility = View.GONE

            } else {
                binding.cartBadge.text = Integer.min(mCartItemCount, 9).toString()
                if (mCartItemCount >= 10) binding.cartBadge.text = "9+"

                binding.cartBadge.visibility = View.VISIBLE

            }
        }
    }

    private fun setupBadge() {
        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount!!.visibility != View.GONE) {
                    textCartItemCount!!.visibility = View.GONE
                }
            } else {
                textCartItemCount!!.text = Math.min(mCartItemCount, 99).toString()
                if (textCartItemCount!!.visibility != View.VISIBLE) {
                    textCartItemCount!!.visibility = View.VISIBLE
                }
            }
        }
    }

    private val myReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {


            //chamar so se estiver na main como visao principal
//            if (intent.extras != null) {

//                val body = intent.getStringExtra("descricao")
//                val graph = navController.navInflater.inflate(R.navigation.mobile_navigation)
//                graph.setStartDestination(R.id.sucessLevelFragment)
//                navController.graph = graph
                //findNavController(R.id.mobile_navigation_xml).navigate(R.id.sucessLevelFragment)
                // Toast.makeText(this@HomeActivity, "Foi", Toast.LENGTH_SHORT).show()

//            }
        }
    }

}