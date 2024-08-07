package com.zsoltbertalan.pokedexgraphql

import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zsoltbertalan.pokedexgraphql.common.async.IoDispatcher
import com.zsoltbertalan.pokedexgraphql.common.async.MainDispatcher
import com.zsoltbertalan.pokedexgraphql.domain.api.PokemonRepository
import com.zsoltbertalan.pokedexgraphql.presentation.ui.PokedexGraphQLActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class PokemonDetailsTest {

	@get:Rule(order = 0)
	val hiltAndroidRule = HiltAndroidRule(this)

	@get:Rule(order = 1)
	val composeTestRule = createAndroidComposeRule<PokedexGraphQLActivity>()

	@Inject
	lateinit var pokemonRepository: PokemonRepository

	@Inject
	@MainDispatcher
	lateinit var mainContext: CoroutineDispatcher

	@Inject
	@IoDispatcher
	lateinit var ioContext: CoroutineDispatcher

	@Before
	fun setUp() {

		hiltAndroidRule.inject()

	}

	@Test
	fun showPokemons() {

		composeTestRule.waitUntilAtLeastOneExists(hasTestTag("PokemonsRow"), 1000L)

		composeTestRule.onNodeWithText("Affenpinscher", ignoreCase = true).assertExists()

	}

	@Test
	fun showPokemonImages() {

		composeTestRule.waitUntilAtLeastOneExists(hasTestTag("PokemonsHeader"), 3000L)

		composeTestRule.onNodeWithText("Affenpinscher", ignoreCase = true).performClick()

		composeTestRule.waitUntilAtLeastOneExists(hasTestTag("PokemonRow"), 3000L)

		composeTestRule.onAllNodesWithTag("PokemonRow").assertAny(hasTestTag("PokemonRow"))

	}

}