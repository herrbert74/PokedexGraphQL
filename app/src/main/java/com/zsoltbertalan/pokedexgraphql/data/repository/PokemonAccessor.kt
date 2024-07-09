package com.zsoltbertalan.pokedexgraphql.data.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.github.michaelbull.result.Ok
import com.zsoltbertalan.pokedexgraphql.PokemonsQuery
import com.zsoltbertalan.pokedexgraphql.data.db.PokemonDataSource
import com.zsoltbertalan.pokedexgraphql.common.async.IoDispatcher
import com.zsoltbertalan.pokedexgraphql.common.util.Outcome
import com.zsoltbertalan.pokedexgraphql.common.util.getresult.fetchCacheThenNetwork
import com.zsoltbertalan.pokedexgraphql.domain.api.PokemonRepository
import com.zsoltbertalan.pokedexgraphql.data.network.PokemonService
import com.zsoltbertalan.pokedexgraphql.data.network.dto.PokemonDto
import com.zsoltbertalan.pokedexgraphql.data.network.dto.toPokemonList
import com.zsoltbertalan.pokedexgraphql.domain.model.Pokemon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Singleton

@Singleton
class PokemonAccessor(
	private val pokemonService: PokemonService,
	private val apolloClient: ApolloClient,
	private val pokemonDataSource: PokemonDataSource,
	@IoDispatcher private val ioContext: CoroutineDispatcher,
) : PokemonRepository {

	override suspend fun getAllPokemon(): Flow<Outcome<List<Pokemon>>> {
		val d = apolloClient.query(PokemonsQuery(limit = Optional.present(20), Optional.present(1))).execute().data
		Timber.d("zsoltbertalan* getAllPokemon: $d")
		return fetchCacheThenNetwork(
			fetchFromLocal = { pokemonDataSource.getPokemons() },
			makeNetworkRequest = { pokemonService.getPokemons() },
			saveResponseData = { pokemons -> pokemonDataSource.insertPokemons(pokemons.toPokemonList()) },
			mapper = List<PokemonDto>::toPokemonList
		).flowOn(ioContext)
	}

	override suspend fun getPokemon(pokemonId: Int): Flow<Outcome<Pokemon>> {

		return flow<Outcome<Pokemon>> {
			val pokemon = pokemonDataSource.getPokemon(pokemonId)
			emit(Ok(pokemon))
		}.flowOn(ioContext)

	}

}
