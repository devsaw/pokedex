package br.digitalhouse.pokedex.SignIn.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ConfigFirebase {
    private var autenticacao: FirebaseAuth? = null
    private var referenciaFirebase: DatabaseReference? = null

    //retorna a instancia do FirebaseDataBase
    fun getFirebase(): DatabaseReference? {
        if (referenciaFirebase == null) {
            referenciaFirebase = FirebaseDatabase.getInstance().reference
        }
        return referenciaFirebase
    }


    //retorna a instancia do FirebaseAuth

    //retorna a instancia do FirebaseAuth
    fun getAuth(): FirebaseAuth? {
        if (autenticacao == null) {
            autenticacao = FirebaseAuth.getInstance()
        }
        return autenticacao
    }
}