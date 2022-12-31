package br.digitalhouse.pokedex.ui.dashboard.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.data.dto.ListPokemon
import br.digitalhouse.pokedex.data.dto.PokemonObject
import com.bumptech.glide.Glide

class HomeAdapter(private val context: Context,
                  private val results: MutableList<ListPokemon> = mutableListOf(),
                  private val onItemClicked: (title: String, overviews: String, filmes: String) -> Unit) :
    RecyclerView.Adapter<HomeAdapter.HomeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.cardpokemon_layout, parent, false)
    )

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        holder.itemView.rootView.setOnClickListener{
            onItemClicked.invoke(results[position].imgPokemon, results[position].nomePokemon, results[position].pesoPokemon )
        }

        Glide
            .with(context)
            .load(results[position].imgPokemon)
            .error(R.drawable.pokepng)
            .into(holder.foto)

        holder.nome.text = results[position].nomePokemon
        holder.id.text = results[position].num

        if (results[position].typePokemon[0] == "Fire"){
            holder.card.background.setTint(Color.parseColor("#FF3232"))
            holder.stats.setImageResource(R.drawable.ash)
        }else if(results[position].typePokemon[0] == "Water"){
            holder.card.background.setTint(Color.parseColor("#30B6FE"))
            holder.stats.setImageResource(R.drawable.ash)
        }else if(results[position].typePokemon[0] == "Grass"){
            holder.card.background.setTint(Color.parseColor("#64DD17"))
            holder.stats.setImageResource(R.drawable.pokepng)
        }else if(results[position].typePokemon[0] == "Electric"){
            holder.card.background.setTint(Color.parseColor("#FFDE08"))
            holder.stats.setImageResource(R.drawable.ash)
        }else if(results[position].typePokemon[0] == "Flying"){
            holder.card.background.setTint(Color.parseColor("#F7FF08"))
            holder.stats.setImageResource(R.drawable.ash)
        }else if(results[position].typePokemon[0] == "Ice"){
            holder.card.background.setTint(Color.parseColor("#A3E6FF"))
            holder.stats.setImageResource(R.drawable.ash)
        }else if(results[position].typePokemon[0] == "Psychic"){
            holder.card.background.setTint(Color.parseColor("#FFCCFE"))
            holder.stats.setImageResource(R.drawable.ash)
        }else if(results[position].typePokemon[0] == "Rock"){
            holder.card.background.setTint(Color.parseColor("#555555"))
            holder.stats.setImageResource(R.drawable.ash)
        }else if(results[position].typePokemon[0] == "Normal"){
            holder.card.background.setTint(Color.parseColor("#ECD3AE"))
            holder.stats.setImageResource(R.drawable.ash)
        }else if(results[position].typePokemon[0] == "Poison" || results[position].typePokemon[0] == "Bug"){
            holder.card.background.setTint(Color.parseColor("#BBFF00"))
            holder.stats.setImageResource(R.drawable.ash)
        }else if(results[position].typePokemon[0] == "Fighting"){
            holder.card.background.setTint(Color.parseColor("#9F6969"))
            holder.stats.setImageResource(R.drawable.fighting)
        }else if(results[position].typePokemon[0] == "Ground"){
            holder.card.background.setTint(Color.parseColor("#009884"))
            holder.stats.setImageResource(R.drawable.ash)
        }else if(results[position].typePokemon[0] == "Ghost"){
            holder.card.background.setTint(Color.parseColor("#E39FFD"))
            holder.stats.setImageResource(R.drawable.ash)
        }else if(results[position].typePokemon[0] == "Dragon"){
            holder.card.background.setTint(Color.parseColor("#FFA200"))
            holder.stats.setImageResource(R.drawable.ash)
        }else{
            holder.card.background.setTint(Color.parseColor("#FFFFFFFF"))
            holder.stats.setImageResource(R.drawable.ic_pokeballsvg)
        }

    }

    override fun getItemCount(): Int = results.size

    fun update(pokemonObject: PokemonObject) {
        this.results.clear()
        this.results.addAll(pokemonObject.listPokemon)
        this.notifyDataSetChanged()
    }


    inner class HomeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foto: ImageView = itemView.findViewById(R.id.imgPoke)
        val nome: TextView = itemView.findViewById(R.id.textPoke)
        val id: TextView = itemView.findViewById(R.id.idnum)
        val card: CardView = itemView.findViewById(R.id.cardBackground)
        val stats: ImageView = itemView.findViewById(R.id.stats)
    }
}