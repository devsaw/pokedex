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
import br.digitalhouse.pokedex.data.dto.PokemonObject
import com.bumptech.glide.Glide

class PokemonListAdapter(private val context: Context,
                         private val results: MutableList<ListPokemon> = mutableListOf(),
                         private val onItemClicked: (title: String, overviews: String, filmes: String) -> Unit) :
    RecyclerView.Adapter<PokemonListAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.cardpokemon_layout, parent, false)
    )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) =
        holder.bind(results[position])


    override fun getItemCount(): Int = results.size

    fun update(pokemonObject: PokemonObject) {
        this.results.clear()
        this.results.addAll(pokemonObject.listPokemon)
        this.notifyDataSetChanged()
    }


    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val foto: ImageView = itemView.findViewById(R.id.imgPoke)
        private val nome: TextView = itemView.findViewById(R.id.textPoke)
        private val id: TextView = itemView.findViewById(R.id.idnum)

        fun bind(listPokemon: ListPokemon) {
            val imageURL = getImageSearchUrl(listPokemon.imgPokemon)
            itemView.rootView.setOnClickListener{
                onItemClicked.invoke(listPokemon.imgPokemon, listPokemon.nomePokemon, listPokemon.pesoPokemon )
            }
            Glide
                .with(context)
                .load(imageURL)
                .error(R.drawable.devtp)
                .into(foto)

            nome.text = listPokemon.nomePokemon
            id.text = listPokemon.num
        }

        private fun getImageSearchUrl(path: String): String {
            val BASE_IMAGE_URL = "http://www.serebii.net/pokemongo/pokemon/"
            return "$BASE_IMAGE_URL$path"
        }
    }
}