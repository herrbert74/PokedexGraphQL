package com.zsoltbertalan.pokedexgraphql.presentation.navigation

import androidx.annotation.StringRes
import com.zsoltbertalan.pokedexgraphql.R

enum class Destination(
	@StringRes val titleId: Int,
	val route: String
) {
	POKEMONS(R.string.pokemons, "pokemons"),
	DETAILS(R.string.details, "details/{pokemonId}"),
}
