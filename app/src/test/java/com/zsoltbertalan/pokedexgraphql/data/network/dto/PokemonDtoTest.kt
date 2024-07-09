package com.zsoltbertalan.pokedexgraphql.data.network.dto

import com.zsoltbertalan.pokedexgraphql.common.testhelper.PokemonDtoMother
import com.zsoltbertalan.pokedexgraphql.domain.model.Pokemon
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test

class PokemonDtoTest {

	private var mappedResponse :List<Pokemon> = emptyList()

	@Before
	fun setup() {
		val responseDto = PokemonDtoMother.createPokemonDtoList()
		mappedResponse = responseDto.toPokemonList()
	}

	@Test
	fun `when there is a response then name is mapped`() {
		mappedResponse[0].name shouldBe "Walter White"
	}

	@Test
	fun `when there is a response then occupation is mapped`() {
		mappedResponse[0].occupation shouldBe "High School Chemistry Teacher, Meth King Pin"
	}

	@Test
	fun `when there is a response then status is mapped`() {
		mappedResponse[0].status shouldBe Status.Unknown
	}

}
