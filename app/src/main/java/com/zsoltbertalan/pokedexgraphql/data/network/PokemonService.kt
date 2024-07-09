package com.zsoltbertalan.pokedexgraphql.data.network

import com.zsoltbertalan.pokedexgraphql.data.network.dto.PokemonDto
import retrofit2.http.GET

interface PokemonService {
	@GET("api/pokemons")
	suspend fun getPokemons(): List<PokemonDto>

	@GET("api/pokemon")
	suspend fun getPokemon(pokemonId: String): PokemonDto
}