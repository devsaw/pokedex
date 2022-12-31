package br.digitalhouse.pokedex.data.dto

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JsonRepository {
    private val api = pokemonApi

    suspend fun fetchPokemon(): PokemonObject = withContext(Dispatchers.IO){
        api.fetchPokemon()
    }
}