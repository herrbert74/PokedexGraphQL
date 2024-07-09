package com.zsoltbertalan.pokedexgraphql.presentation.ui.pokemons

import com.github.michaelbull.result.Ok
import com.zsoltbertalan.pokedexgraphql.domain.api.PokemonRepository
import com.zsoltbertalan.pokedexgraphql.common.testhelper.PokemonMother
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class PokemonsViewModelTest {

	private val pokemonRepository = mockk<PokemonRepository>(relaxed = true)

	private lateinit var pokemonsViewModel: PokemonsViewModel

	private val dispatcher = StandardTestDispatcher()

	@Before
	fun setUp() {

		Dispatchers.setMain(dispatcher)

		coEvery { pokemonRepository.getAllPokemon() } answers { flowOf(Ok(PokemonMother.createPokemonList())) }

		pokemonsViewModel = PokemonsViewModel(pokemonRepository)

	}

	@Test
	fun `when started then getPokemons is called and returns correct list`() {
		coVerify(exactly = 1) { pokemonRepository.getAllPokemon() }
		pokemonsViewModel.state.value.pokemons shouldBe PokemonMother.createPokemonList()
	}

	@Test
	fun `when sort button is pressed then getPokemons returned in reverse order`() {
		//pokemonsViewModel.sortPokemons()

		pokemonsViewModel.state.value.pokemons shouldBe PokemonMother.createPokemonList().reversed()
	}

}


