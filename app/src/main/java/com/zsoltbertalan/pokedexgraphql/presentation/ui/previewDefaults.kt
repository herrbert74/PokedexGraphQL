package com.zsoltbertalan.pokedexgraphql.presentation.ui

import com.zsoltbertalan.pokedexgraphql.data.network.PokemonService
import com.zsoltbertalan.pokedexgraphql.data.network.dto.PokemonDto

val defaultPokemonService = object : PokemonService {

	override suspend fun getPokemons(): List<PokemonDto> {
		TODO("Not yet implemented")
	}

	override suspend fun getPokemon(pokemonId: String): PokemonDto {
		TODO("Not yet implemented")
	}

}
