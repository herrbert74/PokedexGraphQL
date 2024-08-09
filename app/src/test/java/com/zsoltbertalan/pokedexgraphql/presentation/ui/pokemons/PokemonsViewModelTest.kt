package com.zsoltbertalan.pokedexgraphql.presentation.ui.pokemons

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.zsoltbertalan.pokedexgraphql.common.testhelper.PokemonMother
import com.zsoltbertalan.pokedexgraphql.domain.api.PokemonRepository
import com.zsoltbertalan.pokedexgraphql.domain.model.Pokemon
import io.kotest.matchers.equality.shouldBeEqualUsingFields
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class PokemonsViewModelTest {

	private val pokemonRepository = mockk<PokemonRepository>(relaxed = true)

	private lateinit var pokemonsViewModel: PokemonsViewModel

	private val dispatcher = StandardTestDispatcher()

	private lateinit var pokemonList: List<Pokemon>
	private lateinit var pagingData: PagingData<Pokemon>

	@Before
	fun setUp() {

		Dispatchers.setMain(dispatcher)

		pokemonList = PokemonMother.createPokemonList()
		pagingData = PagingData.from(pokemonList)

		coEvery { pokemonRepository.getPokemonPageFlow() } answers { flowOf(pagingData) }

		pokemonsViewModel = PokemonsViewModel(pokemonRepository)

	}

	@After
	fun tearDown() {

		Dispatchers.resetMain()

	}

	@Test
	fun `when started then getPokemons is called and returns correct list`() = runTest {
		coVerify(exactly = 1) { pokemonRepository.getPokemonPageFlow() }

		backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
			pokemonsViewModel.pokemons.asSnapshot()[0] shouldBeEqualUsingFields pokemonList[0]
		}
	}

}


