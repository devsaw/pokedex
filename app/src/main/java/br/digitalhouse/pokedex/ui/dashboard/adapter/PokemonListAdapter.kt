package br.digitalhouse.pokedex.ui.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.data.dto.ListPokemon
import br.digitalhouse.pokedex.data.utils.RecyclerItemClickListener
import com.bumptech.glide.Glide

class PokemonListAdapter(private val context: Context,,
                         private val results: MutableList<ListPokemon> = mutableListOf(),
                         private var onItemClicked: RecyclerItemClickListener) :
    RecyclerView.Adapter<PokemonListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(
        LayoutInflater.from(parent.context).inflate(R.layout.cardpokemon_layout, parent, false)
    )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClicked.onItemClick(produto)
        }

        Glide
            .with(context)
            .load(WSConstants.URL_CARRINHO + dataItens!![position].receita_cliente)
            .error(R.drawable.pdf)
            .into(holder.receitaClient)

    }


    override fun getItemCount(): Int = results.size


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val foto: ImageView = itemView.findViewById(R.id.imgPoke)
        private val nome: TextView = itemView.findViewById(R.id.textPoke)
    }
}