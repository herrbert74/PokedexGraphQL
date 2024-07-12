package com.zsoltbertalan.pokedexgraphql.domain.api

import androidx.paging.PagingData
import com.zsoltbertalan.pokedexgraphql.common.util.Outcome
import com.zsoltbertalan.pokedexgraphql.domain.model.Pokemon
import com.zsoltbertalan.pokedexgraphql.domain.model.PokemonDetails
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

	fun getAllPokemon(): Flow<Outcome<List<Pokemon>>>

	fun getPokemon(pokemonId: Int): Flow<Outcome<Pokemon>>

	fun getPokemonPageFlow(): Flow<PagingData<Pokemon>>

	suspend fun getPokemon(name:String): Outcome<PokemonDetails>

}
