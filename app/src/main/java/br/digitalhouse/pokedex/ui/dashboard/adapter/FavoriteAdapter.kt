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
import br.digitalhouse.pokedex.data.dto.PokemonsDataClass
import com.bumptech.glide.Glide

class FavoriteAdapter(
    private val context: Context,
    private val results: MutableList<PokemonsDataClass> = mutableListOf(),
    private val onItemClicked: (name: String, num: String, image: String, height: String, weight: String, type: String, weaknesses: String, prevevo: String, nextevo: String) -> Unit
) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {

    private var pEvo = ""
    private var nEvo = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavoriteHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.cardpokemon_layout, parent, false)
    )

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.itemView.rootView.setOnClickListener {
            if (results[position].prevP != null) {
                if (results[position].prevP == results[position].nameP) {
                    pEvo = ""
                } else {
                    pEvo = results[position].prevP!!
                }
            } else {
                pEvo = ""
            }

            if (results[position].nextP != null) {
                if (results[position].nextP == results[position].nameP) {
                    nEvo = ""
                } else {
                    nEvo = results[position].nextP!!
                }
            } else {
                nEvo = ""
            }

            onItemClicked.invoke(
                results[position].nameP!!,
                results[position].numP!!,
                results[position].imageP!!,
                results[position].heightP!!,
                results[position].weightP!!,
                results[position].elementP!!,
                results[position].weaknessP!!,
                pEvo,
                nEvo
            )
        }

        Glide
            .with(context)
            .load(results[position].imageP)
            .error(R.drawable.pokepng)
            .into(holder.foto)

        holder.nome.text = results[position].nameP
        holder.id.text = results[position].numP

        if (results[position].elementP == "Fire") {
            holder.card.background.setTint(Color.parseColor("#FF5C34"))
            holder.stats.setImageResource(R.drawable.fire)
        } else if (results[position].elementP == "Water") {
            holder.card.background.setTint(Color.parseColor("#30B6FE"))
            holder.stats.setImageResource(R.drawable.water)
        } else if (results[position].elementP == "Grass") {
            holder.card.background.setTint(Color.parseColor("#64DD17"))
            holder.stats.setImageResource(R.drawable.grass)
        } else if (results[position].elementP == "Electric") {
            holder.card.background.setTint(Color.parseColor("#FFDE08"))
            holder.stats.setImageResource(R.drawable.electric)
        } else if (results[position].elementP == "Flying") {
            holder.card.background.setTint(Color.parseColor("#F7FF08"))
            holder.stats.setImageResource(R.drawable.flying)
        } else if (results[position].elementP == "Ice") {
            holder.card.background.setTint(Color.parseColor("#59D8C5"))
            holder.stats.setImageResource(R.drawable.ice)
        } else if (results[position].elementP == "Psychic") {
            holder.card.background.setTint(Color.parseColor("#ED5484"))
            holder.stats.setImageResource(R.drawable.psyc)
        } else if (results[position].elementP == "Rock") {
            holder.card.background.setTint(Color.parseColor("#ECD3AE"))
            holder.stats.setImageResource(R.drawable.rock)
        } else if (results[position].elementP == "Normal") {
            holder.card.background.setTint(Color.parseColor("#555555"))
            holder.stats.setImageResource(R.drawable.normal)
        } else if (results[position].elementP == "Poison" || results[position].elementP == "Bug") {
            holder.card.background.setTint(Color.parseColor("#BBFF00"))
            holder.stats.setImageResource(R.drawable.poison)
        } else if (results[position].elementP == "Fighting") {
            holder.card.background.setTint(Color.parseColor("#913B3B"))
            holder.stats.setImageResource(R.drawable.fighting)
        } else if (results[position].elementP == "Ground") {
            holder.card.background.setTint(Color.parseColor("#FFA200"))
            holder.stats.setImageResource(R.drawable.ground)
        } else if (results[position].elementP == "Ghost") {
            holder.card.background.setTint(Color.parseColor("#E39FFD"))
            holder.stats.setImageResource(R.drawable.ghost)
        } else if (results[position].elementP == "Dragon") {
            holder.card.background.setTint(Color.parseColor("#5294B4"))
            holder.stats.setImageResource(R.drawable.dragon)
        } else {
            holder.card.background.setTint(Color.parseColor("#FFFFFFFF"))
            holder.stats.setImageResource(R.drawable.ic_pokeballsvg)
        }

    }

    override fun getItemCount(): Int = results.size

    inner class FavoriteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foto: ImageView = itemView.findViewById(R.id.imgPoke)
        val nome: TextView = itemView.findViewById(R.id.textPoke)
        val id: TextView = itemView.findViewById(R.id.idnum)
        val card: CardView = itemView.findViewById(R.id.cardBackground)
        val stats: ImageView = itemView.findViewById(R.id.stats)
    }
}