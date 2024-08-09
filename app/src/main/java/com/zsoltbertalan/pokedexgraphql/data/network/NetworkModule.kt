package com.zsoltbertalan.pokedexgraphql.data.network

import com.apollographql.apollo.ApolloClient
import com.zsoltbertalan.pokedexgraphql.data.db.PokemonDataSource
import com.zsoltbertalan.pokedexgraphql.common.async.IoDispatcher
import com.zsoltbertalan.pokedexgraphql.data.repository.PokemonAccessor
import com.zsoltbertalan.pokedexgraphql.domain.api.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

private const val BASE_URL: String = "https://graphql-pokeapi.graphcdn.app"

@Module
@Suppress("unused")
@InstallIn(SingletonComponent::class)
class NetworkModule {

	@Provides
	@Singleton
	internal fun provideApolloClient(): ApolloClient {
		return ApolloClient.Builder()
			.serverUrl(BASE_URL)
			.build()
	}

	@Provides
	@Singleton
	fun providePokemonRepository(
		apolloClient: ApolloClient,
		@IoDispatcher ioContext: CoroutineDispatcher,
		pokemonDataSource: PokemonDataSource,
	): PokemonRepository {
		return PokemonAccessor(apolloClient, pokemonDataSource, ioContext)
	}

}
