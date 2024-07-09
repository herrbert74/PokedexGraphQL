package com.zsoltbertalan.pokedexgraphql.data.network.dto

import com.zsoltbertalan.pokedexgraphql.data.db.REGION_REGEX
import com.zsoltbertalan.pokedexgraphql.domain.model.Pokemon
import com.zsoltbertalan.pokedexgraphql.data.repository.mapNullInputList

@Suppress("PropertyName")
data class PokemonDto(
	val abilities: List<String>? = null,
	val evolutions: List<String>? = null,
	val hitpoints: Int? = null,
	val id: Int? = null,
	val image_url: String? = null,
	val location: String? = null,
	val pokemon: String? = null,
	val type: String? = null
)

fun List<PokemonDto>.toPokemonList(): List<Pokemon> = mapNullInputList(this) { pokemonDto ->
	pokemonDto.toPokemon()
}

fun PokemonDto.toPokemon() = Pokemon(
	this.id ?: 0,
	this.abilities ?: emptyList(),
	this.evolutions ?: emptyList(),
	this.hitpoints ?: 0,
	this.image_url ?: "",
	this.location ?: "",
	this.location?.let { Regex(REGION_REGEX).find(it)?.value } ?: "",
	this.pokemon ?: "",
	this.type ?: "",
)