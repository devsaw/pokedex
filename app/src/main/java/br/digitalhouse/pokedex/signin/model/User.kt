package br.digitalhouse.pokedex.signin.model

import br.digitalhouse.pokedex.signin.utils.ConfigFirebase
import com.google.firebase.database.Exclude

class User {
    @get:Exclude
    var idUsuario: String? = null
    var nome: String? = null
    var email: String? = null
    var senha: String? = null


    fun saveUser() {
        val firebase = ConfigFirebase().getFirebase()
        if (firebase != null) {
            firebase.child("usuarios")
                .child(idUsuario!!)
                .setValue(this)
        }
    }
}