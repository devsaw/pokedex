package br.digitalhouse.pokedex.ui.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import br.digitalhouse.pokedex.data.dto.JsonRepository
import br.digitalhouse.pokedex.data.dto.PokemonObject

class PokemonViewModel: ViewModel() {
    val pokemonRepository = JsonRepository()

    suspend fun fetchPokemon(): PokemonObject = pokemonRepository.fetchPokemon()
}