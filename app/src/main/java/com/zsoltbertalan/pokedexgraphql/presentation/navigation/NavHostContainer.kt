package com.zsoltbertalan.pokedexgraphql.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
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

	SharedTransitionLayout {
		CompositionLocalProvider(
			LocalSharedTransitionScope provides this
		) {
			NavHost(
				navController = navController,
				startDestination = Destination.POKEMONS.route,
				modifier = Modifier,
				builder = {
					composable(Destination.POKEMONS.route) {
						val list = pokemonsViewModel.pokemons.collectAsLazyPagingItems()
						PokemonsScreen(
							pokemonList = list,
							this@composable,
							onItemClick = { name, imageUrl ->
								val i = imageUrl.replace("/", "dash")
								Timber.d("zsoltbertalan* NavHostContainer, image: $i")
								if (navController.currentDestination ==
									navController.findDestination(Destination.POKEMONS.route)
								) {
									navController.navigate("details/$name/$i")
								}
							}
						)
					}
					composable(
						Destination.DETAILS.route,
						arguments = listOf(
							navArgument("name") { type = NavType.StringType },
							navArgument("imageUrl") { type = NavType.StringType }
						),
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
							this@composable
						) {
							navController.navigateUp()
						}
					}
				}
			)
		}
	}
}

val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }
