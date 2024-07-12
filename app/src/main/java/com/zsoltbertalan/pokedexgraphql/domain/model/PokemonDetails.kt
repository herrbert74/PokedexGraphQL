package com.zsoltbertalan.pokedexgraphql.domain.model

import com.zsoltbertalan.pokedexgraphql.PokemonQuery

data class PokemonDetails(
	val id: Int = 0,
	val name: String = "",
	val abilities: List<String> = emptyList(),
	val types: List<String> = emptyList(),
	val moves: List<String> = emptyList(),
	val evolutions: List<Stat> = emptyList(),
	val imageUrl: String = "",
)

data class Stat(val name: String, val baseStat: Int, val effort: Int)