package com.zsoltbertalan.pokedexgraphql.domain.api

import com.zsoltbertalan.pokedexgraphql.common.util.Outcome
import com.zsoltbertalan.pokedexgraphql.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

	suspend fun getAllPokemon(): Flow<Outcome<List<Pokemon>>>

	suspend fun getPokemon(pokemonId: Int): Flow<Outcome<Pokemon>>

}
