package com.zsoltbertalan.pokedexgraphql.data.network.dto

import com.zsoltbertalan.pokedexgraphql.PokemonsQuery
import com.zsoltbertalan.pokedexgraphql.data.db.REGION_REGEX
import com.zsoltbertalan.pokedexgraphql.data.repository.mapNullInputList
import com.zsoltbertalan.pokedexgraphql.domain.model.Pokemon

fun PokemonsQuery.Data.toPokemonList(): List<Pokemon> =
	mapNullInputList(this.pokemons?.results?.filterNotNull()) { queryResult ->
		queryResult.toPokemon()
	}

fun PokemonsQuery.Result.toPokemon() = Pokemon(
	this.id ?: 0,
	 emptyList(),
	 emptyList(),
	 0,
	this.image ?: "",
	 "",
	 "",
	this.name ?: "",
	 "",
)