package br.digitalhouse.pokedex.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.databinding.FragmentHomeBinding
import br.digitalhouse.pokedex.signin.model.User
import br.digitalhouse.pokedex.signin.utils.Base64Custom
import br.digitalhouse.pokedex.signin.utils.ConfigFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private var auth: FirebaseAuth? = null
    private var valueEventListener: ValueEventListener? = null
    private val firebaseRef = ConfigFirebase().getFirebase()

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
        setData()
        setOnClickListener()
    }

    private fun setOnClickListener() {

    }

    private fun setData(){
        val userAuth = auth!!.currentUser
        val idUsuario = Base64Custom.codificarBase64(userAuth!!.email)
        val nameUser = firebaseRef!!.child("usuarios").child(idUsuario)
        valueEventListener = nameUser.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if (user != null){
                    binding.textViewNameG.text = auth!!.currentUser!!.displayName
                }else{
                    binding.textViewName.text = user?.nome
                }

            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

}