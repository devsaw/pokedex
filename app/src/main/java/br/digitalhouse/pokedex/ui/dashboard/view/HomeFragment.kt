package br.digitalhouse.pokedex.ui.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.databinding.FragmentHomeBinding
import br.digitalhouse.pokedex.data.utils.Base64Custom
import br.digitalhouse.pokedex.data.utils.ConfigFirebase
import br.digitalhouse.pokedex.data.utils.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private var auth: FirebaseAuth? = null
    lateinit var preferences: Preferences
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
        setOnClickListener()
    }

    private fun setOnClickListener() {

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