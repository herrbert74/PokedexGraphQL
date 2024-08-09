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
		mappedResponse[0].name shouldBe "name1"
	}

	@Test
	fun `when there is a response then occupation is mapped`() {
		mappedResponse[0].evolutions shouldBe listOf("Bulbasaur")
	}

	@Test
	fun `when there is a response then status is mapped`() {
		mappedResponse[0].abilities shouldBe emptyList()
	}

}
