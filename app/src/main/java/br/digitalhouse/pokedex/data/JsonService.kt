package br.digitalhouse.pokedex.data.dto

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/"

private val interceptor = HttpLoggingInterceptor {
    Log.d("RETROFIT_CLIENT_CRYPT", it)
}
    .apply { level = HttpLoggingInterceptor.Level.BODY }

private val client = OkHttpClient.Builder()
    .addInterceptor(interceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

val pokemonApi: JsonInterface = retrofit.create(JsonInterface::class.java)