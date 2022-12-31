package br.digitalhouse.pokedex.data.dto

import retrofit2.http.GET

interface JsonInterface {
    @GET("master/pokedex.json")
    suspend fun fetchPokemon(): PokemonObject
}