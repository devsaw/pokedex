package br.digitalhouse.pokedex.data.utils

import android.view.View
import br.digitalhouse.pokedex.ui.signin.model.User


interface ItemClickListener {

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