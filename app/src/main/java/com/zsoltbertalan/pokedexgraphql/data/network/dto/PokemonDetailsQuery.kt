package com.zsoltbertalan.pokedexgraphql.data.network.dto

import com.zsoltbertalan.pokedexgraphql.PokemonQuery
import com.zsoltbertalan.pokedexgraphql.domain.model.PokemonDetails
import com.zsoltbertalan.pokedexgraphql.domain.model.Stat

fun PokemonQuery.Data.toPokemon(): PokemonDetails? = this.pokemon?.toPokemon()

private fun PokemonQuery.Pokemon.toPokemon(): PokemonDetails = PokemonDetails(
	this.id ?: 0,
	this.name ?: "",
	this.abilities.orEmpty().map { it?.ability?.name ?: "" },
	this.types.orEmpty().map { it?.type?.name ?: "" },
	this.moves.orEmpty().map { it?.move?.name ?: "" },
	this.stats.orEmpty().map { Stat(it?.stat?.name ?: "", it?.base_stat ?: 0, it?.effort ?: 0) },
)