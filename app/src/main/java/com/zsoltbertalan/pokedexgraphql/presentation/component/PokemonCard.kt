package com.zsoltbertalan.pokedexgraphql.presentation.component

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.unit.dp
import com.zsoltbertalan.pokedexgraphql.presentation.design.Colors
import com.zsoltbertalan.pokedexgraphql.presentation.design.smallDimensions
import com.zsoltbertalan.pokedexgraphql.presentation.navigation.LocalSharedTransitionScope

private const val TRANSFORM_DURATION = 750

val boundsTransform = { _: Rect, _: Rect -> tween<Rect>(TRANSFORM_DURATION) }

@Composable
fun PokemonCard(
	modifier: Modifier = Modifier,
	id: String,
	name: String,
	imageUrl: String,
	onItemClick: (String, String) -> Unit,
	animatedContentScope: AnimatedContentScope,
) {



	val sharedTransitionScope = LocalSharedTransitionScope.current
		?: throw IllegalStateException("No Scope found")

	//with(sharedTransitionScope) {
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
						name = name,
						modifier = Modifier
							.sharedElement(
								sharedTransitionScope.rememberSharedContentState(key = "pokemon-$name"),
								animatedVisibilityScope = animatedContentScope,
								boundsTransform = boundsTransform
							)
					)
				}
			}
		}

//		with(sharedTransitionScope) {
		ThumbnailCard(
			modifier = Modifier
//					.sharedElement(
//						sharedTransitionScope.rememberSharedContentState(key = "image-$imageUrl"),
//						animatedVisibilityScope = animatedContentScope,
//						boundsTransform = boundsTransform
//					)
				.align(Alignment.CenterEnd)
				.padding(bottom = smallDimensions.marginExtraLarge),
			posterThumbnail = imageUrl
		)
//		}

	}
//	}

}

//@Preview
//@Composable
//fun ShowDetailCardPreview() {
//	PokedexGraphQLTheme {
//		PokemonCard(
//			id = "0001",
//			name = "ivysaur",
//			imageUrl = "https://raw.githubusercontent" +
//				".com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png",
//			onItemClick = {},
//			sharedTransitionScope = SharedTransitionScope(),
//			animatedContentScope= AnimatedContentScope(),
//		)
//	}
//}
