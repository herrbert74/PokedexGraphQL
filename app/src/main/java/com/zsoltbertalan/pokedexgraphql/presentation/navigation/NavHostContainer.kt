package com.zsoltbertalan.pokedexgraphql.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.zsoltbertalan.pokedexgraphql.presentation.ui.pokemondetails.PokemonDetailsScreen
import com.zsoltbertalan.pokedexgraphql.presentation.ui.pokemondetails.PokemonDetailsViewModel
import com.zsoltbertalan.pokedexgraphql.presentation.ui.pokemons.PokemonsScreen
import com.zsoltbertalan.pokedexgraphql.presentation.ui.pokemons.PokemonsViewModel

@SuppressLint("RestrictedApi")
@Composable
fun NavHostContainer(
	navController: NavHostController,
) {
	val pokemonsViewModel = hiltViewModel<PokemonsViewModel>()

	NavHost(
		navController = navController,
		startDestination = Destination.POKEMONS.route,
		modifier = Modifier,
		builder = {
			composable(Destination.POKEMONS.route) {
				PokemonsScreen(
					stateFlow = pokemonsViewModel.state,
					onItemClick = {
						if (navController.currentDestination ==
							navController.findDestination(Destination.POKEMONS.route)
						) {
							val pokemonId = pokemonsViewModel.state.value.pokemonId
							navController.navigate("details/{pokemonId}")
						}
					},
					onSortPokemonsClick = {
						pokemonsViewModel.requestPokemons()
					}
				)
			}
			composable(
				Destination.DETAILS.route,
				arguments = listOf(navArgument("pokemonId") { type = NavType.StringType }),
				enterTransition = {
					slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start)
				},
				popExitTransition = {
					slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End)
				}
			) {
				val detailsViewModel = hiltViewModel<PokemonDetailsViewModel>()
				PokemonDetailsScreen(
					stateFlow = detailsViewModel.state,
				) {
					navController.popBackStack()
				}
			}
		}
	)
}
