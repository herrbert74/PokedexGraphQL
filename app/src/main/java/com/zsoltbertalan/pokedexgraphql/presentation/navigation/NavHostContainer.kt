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
import androidx.paging.compose.collectAsLazyPagingItems
import com.zsoltbertalan.pokedexgraphql.presentation.ui.pokemondetails.PokemonDetailsScreen
import com.zsoltbertalan.pokedexgraphql.presentation.ui.pokemondetails.PokemonDetailsViewModel
import com.zsoltbertalan.pokedexgraphql.presentation.ui.pokemons.PokemonsScreen
import com.zsoltbertalan.pokedexgraphql.presentation.ui.pokemons.PokemonsViewModel
import timber.log.Timber

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
				val list = pokemonsViewModel.pokemons.collectAsLazyPagingItems()
				PokemonsScreen(
					pokemonList = list,
					onItemClick = {name->
						Timber.d("zsoltbertalan* NavHostContainer, name: $name")
						if (navController.currentDestination ==
							navController.findDestination(Destination.POKEMONS.route)
						) {
							navController.navigate("details/$name")
						}
					}
				)
			}
			composable(
				Destination.DETAILS.route,
				arguments = listOf(navArgument("name") { type = NavType.StringType }),
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
