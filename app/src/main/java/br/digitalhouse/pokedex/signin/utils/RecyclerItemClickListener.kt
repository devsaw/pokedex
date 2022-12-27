package br.digitalhouse.pokedex.signin.utils

import android.view.View
import br.digitalhouse.pokedex.signin.model.User


interface RecyclerItemClickListener {

    fun onClickListenerItem(item: Any?){
        //optional body
    }

    fun onClickListenerItemWithType(v: View?, item: Any?, type: Int){
        //optional body
    }

    fun onClickListenerUser(user: User){
        //optional body
    }

    fun onClickListenerUAddress(uAddress: User){
        //optional body
    }

}