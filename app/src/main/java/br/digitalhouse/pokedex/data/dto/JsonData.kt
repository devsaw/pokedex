package br.digitalhouse.pokedex.data.dto

import android.os.Parcelable
import br.digitalhouse.pokedex.data.utils.ConfigFirebase
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PokemonObject(
    @SerializedName("pokemon")
    val listPokemon: List<ListPokemon>,
)

data class ListPokemon(
    val avg_spawns: Double,
    val candy: String,
    val candy_count: Int,
    val egg: String,

    @SerializedName("height")
    val alturaPokemon: String,

    @SerializedName("id")
    val idPokemon: Int,

    @SerializedName("img")
    val imgPokemon: String,

    val multipliers: List<Double>,

    @SerializedName("name")
    val nomePokemon: String,

    @SerializedName("next_evolution")
    val nextEvolution: List<NextEvolution>?,

    val num: String,

    @SerializedName("prev_evolution")
    val prevEvolution: List<PrevEvolution>?,

    val spawn_chance: Double,
    val spawn_time: String,

    @SerializedName("type")
    val typePokemon: List<String>,

    @SerializedName("weaknesses")
    val pontosFracosPokemon: List<String>,

    @SerializedName("weight")
    val pesoPokemon: String
)

data class PrevEvolution(
    @SerializedName("name")
    val namePrevEvolution: String,

    @SerializedName("num")
    val numPrevEvolution: String
)

data class NextEvolution(
    @SerializedName("name")
    val nameNextEvolution: String,

    @SerializedName("num")
    val numNextEvolution: String
    )


@Parcelize
data class PokemonsDataClass(
    var imageP: String? = null,
    var elementP: String? = null,
    var nameP: String? = null,
    var numP: String? = null,
    var idP: String? = null
): Parcelable {
    init {
        this.idP = ConfigFirebase().getDatabase().push().key ?: ""
    }
}