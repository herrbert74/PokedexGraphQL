package com.zsoltbertalan.pokedexgraphql.data.db

import com.zsoltbertalan.pokedexgraphql.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonDataSource {

	suspend fun purgeDatabase()

	suspend fun insertPokemons(pokemons: List<Pokemon>)

	fun getPokemons(): Flow<List<Pokemon>?>

	fun getPokemon(id: Int): Pokemon

}
