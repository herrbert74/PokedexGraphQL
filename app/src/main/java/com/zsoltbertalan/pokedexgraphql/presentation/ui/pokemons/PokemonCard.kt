package com.zsoltbertalan.pokedexgraphql.presentation.ui.pokemons

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zsoltbertalan.pokedexgraphql.presentation.component.LargeText
import com.zsoltbertalan.pokedexgraphql.presentation.component.ThumbnailCard
import com.zsoltbertalan.pokedexgraphql.presentation.component.TitleText
import com.zsoltbertalan.pokedexgraphql.presentation.design.Colors
import com.zsoltbertalan.pokedexgraphql.presentation.design.PokedexGraphQLTheme
import com.zsoltbertalan.pokedexgraphql.presentation.design.smallDimensions
import com.zsoltbertalan.pokedexgraphql.presentation.navigation.LocalPokemonsAnimatedVisibilityScope
import com.zsoltbertalan.pokedexgraphql.presentation.navigation.LocalSharedTransitionScope

private const val TRANSFORM_DURATION = 500

val boundsTransform = { _: Rect, _: Rect -> tween<Rect>(TRANSFORM_DURATION) }

@Composable
fun PokemonCard(
	modifier: Modifier = Modifier,
	id: String,
	name: String,
	imageUrl: String,
	onItemClick: (String, String) -> Unit,
) {

	val sharedTransitionScope = LocalSharedTransitionScope.current
		?: throw IllegalStateException("No Scope found")

	val sharedAnimatedVisibilityScope = LocalPokemonsAnimatedVisibilityScope.current
		?: throw IllegalStateException("No Scope found")

	Box(
		modifier = modifier
			.padding(horizontal = smallDimensions.marginLarge)
			.clickable { onItemClick(name, imageUrl) }
	) {

		Card(
			modifier = modifier
				.fillMaxWidth()
				.padding(top = smallDimensions.marginTripleLarge)
				.height(200.dp),
			shape = RoundedCornerShape(8.dp),
			elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
			colors = CardDefaults.cardColors(containerColor = Colors.surfaceContainerHighest)
		) {
			with(sharedTransitionScope) {
				Column(
					modifier = Modifier
						.fillMaxSize()
						.padding(start = smallDimensions.marginLarge, end = 200.dp),
					verticalArrangement = Arrangement.Center,
					horizontalAlignment = Alignment.Start,
				) {
					LargeText(rating = id)
					TitleText(
						modifier = Modifier
							.skipToLookaheadSize()
							.sharedBounds(
								sharedTransitionScope.rememberSharedContentState(key = "pokemon-$name"),
								exit = fadeOut(),
								enter = fadeIn(),
								renderInOverlayDuringTransition = false,
								animatedVisibilityScope = sharedAnimatedVisibilityScope,
								boundsTransform = boundsTransform
							),
						name = name
					)
				}
			}
		}

		with(sharedTransitionScope) {
			ThumbnailCard(
				modifier = Modifier
					.skipToLookaheadSize()
					.sharedBounds(
						sharedTransitionScope.rememberSharedContentState(key = "image-$imageUrl"),
						animatedVisibilityScope = sharedAnimatedVisibilityScope,
						renderInOverlayDuringTransition = false,
						exit = fadeOut(),
						enter = fadeIn(),
						boundsTransform = boundsTransform
					)
					.align(Alignment.CenterEnd),
				posterThumbnail = imageUrl
			)
		}

	}

}

@Preview
@Composable
fun ShowDetailCardPreview() {
	PokedexGraphQLTheme {
		PokemonCard(
			id = "0001",
			name = "ivysaur",
			imageUrl = "https://raw.githubusercontent" +
				".com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png",
			onItemClick = { _: String, _: String -> },
		)
	}
}
