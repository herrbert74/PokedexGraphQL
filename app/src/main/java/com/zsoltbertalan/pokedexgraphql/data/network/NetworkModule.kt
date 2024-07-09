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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL: String = "https://graphql-pokeapi.graphcdn.app"

@Module
@Suppress("unused")
@InstallIn(SingletonComponent::class)
class NetworkModule {

	@Provides
	@Singleton
	internal fun providePokemonRetrofit(): Retrofit {
		val logging = HttpLoggingInterceptor()
		logging.level = HttpLoggingInterceptor.Level.BODY

		val httpClient = OkHttpClient.Builder()

		httpClient.addInterceptor(logging)
		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.client(httpClient.build())
			.build()
	}

	@Provides
	@Singleton
	internal fun providePokemonService(retroFit: Retrofit): PokemonService {
		return retroFit.create(PokemonService::class.java)
	}

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
		pokemonService: PokemonService,
		apolloClient: ApolloClient,
		@IoDispatcher ioContext: CoroutineDispatcher,
		pokemonDataSource: PokemonDataSource,
	): PokemonRepository {
		return PokemonAccessor(pokemonService, apolloClient, pokemonDataSource, ioContext)
	}

}
