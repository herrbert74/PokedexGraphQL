package com.zsoltbertalan.pokedexgraphql.data.repository

import androidx.paging.PagingData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.github.michaelbull.result.map
import com.zsoltbertalan.pokedexgraphql.PokemonQuery
import com.zsoltbertalan.pokedexgraphql.PokemonsQuery
import com.zsoltbertalan.pokedexgraphql.common.async.IoDispatcher
import com.zsoltbertalan.pokedexgraphql.common.util.Outcome
import com.zsoltbertalan.pokedexgraphql.common.util.runCatchingApi
import com.zsoltbertalan.pokedexgraphql.data.db.PokemonDataSource
import com.zsoltbertalan.pokedexgraphql.data.network.dto.toPokemon
import com.zsoltbertalan.pokedexgraphql.data.network.dto.toPokemonList
import com.zsoltbertalan.pokedexgraphql.domain.api.PokemonRepository
import com.zsoltbertalan.pokedexgraphql.domain.model.Pokemon
import com.zsoltbertalan.pokedexgraphql.domain.model.PokemonDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Singleton

@Singleton
class PokemonAccessor(
	private val apolloClient: ApolloClient,
	private val pokemonDataSource: PokemonDataSource,
	@IoDispatcher private val ioContext: CoroutineDispatcher,
) : PokemonRepository {

	override fun getPokemonPageFlow(): Flow<PagingData<Pokemon>> = createPager { pageOffset ->
		runCatchingApi {
			apolloClient.query(
				PokemonsQuery(
					limit = Optional.present(PAGING_PAGE_SIZE), offset = Optional.present(pageOffset)
				)
			)
				.execute()
				.data
		}.map {
			Timber.d("zsoltbertalan* getAllPokemon: $it")
			Pair(it?.toPokemonList().orEmpty(), it?.pokemons?.count ?: 0)
		}
	}.flow

	override suspend fun getPokemon(name: String): Outcome<PokemonDetails> {
		Timber.d("zsoltbertalan* getPokemon: $name")
		return runCatchingApi {
			apolloClient.query(
				PokemonQuery(
					name = name
				)
			).execute().data
		}.map { it!!.toPokemon()!! }
	}

}
