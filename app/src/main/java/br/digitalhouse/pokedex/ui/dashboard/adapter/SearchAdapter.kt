package br.digitalhouse.pokedex.ui.dashboard.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.data.dto.ListPokemon
import br.digitalhouse.pokedex.data.dto.PokemonObject
import br.digitalhouse.pokedex.data.dto.PokemonsDataClass
import com.bumptech.glide.Glide
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

class SearchAdapter(
    private val context: Context,
    private val results: ArrayList<ListPokemon>,
    private val onItemClicked: (name: String, num: String, image: String, height: String, weight: String, type: String, weaknesses: String, prevevo: String, nextevo: String) -> Unit
) :
    RecyclerView.Adapter<SearchAdapter.SearchHolder>(), Filterable {

    var listFull = ArrayList<ListPokemon>()

    init {
        listFull = this.results
    }

    private var pEvo = ""
    private var nEvo = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.cardpokemon_layout, parent, false)
    )

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.itemView.rootView.setOnClickListener {
            if (results[position].prevEvolution != null) {
                for (iEvo in results[position].prevEvolution!!) {
                    if (iEvo.namePrevEvolution == results[position].nomePokemon) {
                        pEvo = ""
                    } else {
                        pEvo = iEvo.namePrevEvolution
                    }
                }
            } else {
                pEvo = ""
            }

            if (results[position].nextEvolution != null) {
                if (results[position].nextEvolution!![0].nameNextEvolution == results[position].nomePokemon) {
                    nEvo = ""
                } else {
                    nEvo = results[position].nextEvolution!![0].nameNextEvolution
                }
            } else {
                nEvo = ""
            }

            onItemClicked.invoke(
                results[position].nomePokemon,
                results[position].num,
                results[position].imgPokemon,
                results[position].alturaPokemon,
                results[position].pesoPokemon,
                results[position].typePokemon[0],
                results[position].pontosFracosPokemon[0],
                pEvo,
                nEvo
            )
        }

        Glide
            .with(context)
            .load(results[position].imgPokemon)
            .error(R.drawable.pokepng)
            .into(holder.foto)

        holder.nome.text = results[position].nomePokemon
        holder.id.text = results[position].num

        if (results[position].typePokemon[0] == "Fire") {
            holder.card.background.setTint(Color.parseColor("#FF5C34"))
            holder.stats.setImageResource(R.drawable.fire)
        } else if (results[position].typePokemon[0] == "Water") {
            holder.card.background.setTint(Color.parseColor("#30B6FE"))
            holder.stats.setImageResource(R.drawable.water)
        } else if (results[position].typePokemon[0] == "Grass") {
            holder.card.background.setTint(Color.parseColor("#64DD17"))
            holder.stats.setImageResource(R.drawable.grass)
        } else if (results[position].typePokemon[0] == "Electric") {
            holder.card.background.setTint(Color.parseColor("#FFDE08"))
            holder.stats.setImageResource(R.drawable.electric)
        } else if (results[position].typePokemon[0] == "Flying") {
            holder.card.background.setTint(Color.parseColor("#F7FF08"))
            holder.stats.setImageResource(R.drawable.flying)
        } else if (results[position].typePokemon[0] == "Ice") {
            holder.card.background.setTint(Color.parseColor("#59D8C5"))
            holder.stats.setImageResource(R.drawable.ice)
        } else if (results[position].typePokemon[0] == "Psychic") {
            holder.card.background.setTint(Color.parseColor("#ED5484"))
            holder.stats.setImageResource(R.drawable.psyc)
        } else if (results[position].typePokemon[0] == "Rock") {
            holder.card.background.setTint(Color.parseColor("#ECD3AE"))
            holder.stats.setImageResource(R.drawable.rock)
        } else if (results[position].typePokemon[0] == "Normal") {
            holder.card.background.setTint(Color.parseColor("#555555"))
            holder.stats.setImageResource(R.drawable.normal)
        } else if (results[position].typePokemon[0] == "Poison" || results[position].typePokemon[0] == "Bug") {
            holder.card.background.setTint(Color.parseColor("#BBFF00"))
            holder.stats.setImageResource(R.drawable.poison)
        } else if (results[position].typePokemon[0] == "Fighting") {
            holder.card.background.setTint(Color.parseColor("#913B3B"))
            holder.stats.setImageResource(R.drawable.fighting)
        } else if (results[position].typePokemon[0] == "Ground") {
            holder.card.background.setTint(Color.parseColor("#FFA200"))
            holder.stats.setImageResource(R.drawable.ground)
        } else if (results[position].typePokemon[0] == "Ghost") {
            holder.card.background.setTint(Color.parseColor("#E39FFD"))
            holder.stats.setImageResource(R.drawable.ghost)
        } else if (results[position].typePokemon[0] == "Dragon") {
            holder.card.background.setTint(Color.parseColor("#5294B4"))
            holder.stats.setImageResource(R.drawable.dragon)
        } else {
            holder.card.background.setTint(Color.parseColor("#FFFFFFFF"))
            holder.stats.setImageResource(R.drawable.ic_pokeballsvg)
        }

    }

    override fun getItemCount(): Int = results.size

    override fun getFilter(): Filter {
        return filter
    }

    private val filter = object : Filter(){

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val resultList = ArrayList<ListPokemon>()
            val charSearch = constraint.toString()
            if (charSearch.isEmpty()) {
                listFull = results
            } else {

                val filterPattern =
                    constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }

                results.filter {
                    (it.nomePokemon.toLowerCase().contains(filterPattern))

                }
                    .forEach { resultList.add(it) }
                listFull = resultList

            }
            val filterResults = FilterResults()
            filterResults.values = listFull
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            listFull = results?.values as ArrayList<ListPokemon>
            Log.d("RETORNO", Gson().toJson(results))
            notifyDataSetChanged()
        }

    }

    inner class SearchHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foto: ImageView = itemView.findViewById(R.id.imgPoke)
        val nome: TextView = itemView.findViewById(R.id.textPoke)
        val id: TextView = itemView.findViewById(R.id.idnum)
        val card: CardView = itemView.findViewById(R.id.cardBackground)
        val stats: ImageView = itemView.findViewById(R.id.stats)
    }
}