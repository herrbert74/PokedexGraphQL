package com.zsoltbertalan.pokedexgraphql.presentation.ui.pokemondetails

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.zsoltbertalan.pokedexgraphql.R
import com.zsoltbertalan.pokedexgraphql.presentation.component.DetailText
import com.zsoltbertalan.pokedexgraphql.presentation.component.SubtitleText
import com.zsoltbertalan.pokedexgraphql.presentation.ui.pokemons.boundsTransform
import com.zsoltbertalan.pokedexgraphql.presentation.component.shape.TiltedShape
import com.zsoltbertalan.pokedexgraphql.presentation.component.subTitleModifier
import com.zsoltbertalan.pokedexgraphql.presentation.design.Colors
import com.zsoltbertalan.pokedexgraphql.presentation.design.Dimens
import com.zsoltbertalan.pokedexgraphql.presentation.design.PokedexGraphQLTheme
import com.zsoltbertalan.pokedexgraphql.presentation.design.smallDimensions
import com.zsoltbertalan.pokedexgraphql.presentation.navigation.LocalSharedTransitionScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

const val STATS_MAXIMUM = 250f

@Composable
fun PokemonDetailsScreen(
	stateFlow: StateFlow<PokemonDetailsViewModel.UiState>,
	animatedContentScope: AnimatedContentScope,
	onBackClick: () -> Unit
) {

	val sharedTransitionScope = LocalSharedTransitionScope.current
		?: throw IllegalStateException("No Scope found")

	val uiState by stateFlow.collectAsStateWithLifecycle()

	BackHandler(onBack = { onBackClick() })

	val pokemon = uiState.pokemon
	val title = uiState.title
	val imageUrl = uiState.imageUrl

	Scaffold(
		topBar = {
			with(sharedTransitionScope) {
				TopAppBar(
					colors = TopAppBarDefaults.topAppBarColors(
						containerColor = PokedexGraphQLTheme.colorScheme.primaryContainer,
						titleContentColor = PokedexGraphQLTheme.colorScheme.onPrimaryContainer,
					),
					title = {
						Text(
							text = title,
							modifier = Modifier
								.sharedBounds(
									sharedTransitionScope.rememberSharedContentState(key = "pokemon-${title}"),
									animatedVisibilityScope = animatedContentScope,
									boundsTransform = boundsTransform,
									exit = fadeOut(),
									enter = fadeIn(),
								)
								.skipToLookaheadSize()
						)
					},
					navigationIcon = {
						IconButton(onClick = { onBackClick() }) {
							Icon(
								imageVector = Icons.AutoMirrored.Filled.ArrowBack,
								contentDescription = "Finish",
								tint = MaterialTheme.colorScheme.onPrimaryContainer
							)
						}
					}
				)
			}
		}
	) { innerPadding ->

		Column(
			modifier = Modifier
				.padding(innerPadding)
				.verticalScroll(ScrollState(0))
				.testTag("PokemonDetails")
		) {

			with(sharedTransitionScope) {
				Row(
					verticalAlignment = Alignment.CenterVertically,
					modifier = Modifier
						.background(Colors.primaryContainer)
						.wrapContentHeight()
						.fillMaxWidth()
				) {

					Column(
						modifier = Modifier
							.weight(2f)
							.background(Colors.primaryContainer)
							.padding(vertical = Dimens.marginNormal, horizontal = Dimens.marginLarge)
					) {

						SubtitleText(name = "Types", Modifier.padding(horizontal = 0.dp))
						FlowRow(modifier = Modifier.padding(vertical = smallDimensions.marginNormal)) {
							pokemon.types.forEach {
								Box(
									modifier = Modifier
										.padding(vertical = smallDimensions.marginNormal)
										.height(smallDimensions.marginTripleLarge)
										.defaultMinSize(minWidth = smallDimensions.buttonWidth)
										.clip(RoundedCornerShape(smallDimensions.marginDoubleLarge))
										.background(color = Colors.secondaryContainer),
									contentAlignment = Alignment.Center
								) {
									DetailText(text = it)
								}
							}
						}
					}

					Timber.d("zsoltbertalan* PokemonDetailsScreen: $imageUrl")

					val imageRequest = ImageRequest.Builder(LocalContext.current)
						.data(imageUrl)
						.dispatcher(Dispatchers.IO)
						.memoryCacheKey(imageUrl)
						.diskCacheKey(imageUrl)
						.diskCachePolicy(CachePolicy.ENABLED)
						.memoryCachePolicy(CachePolicy.ENABLED)
						.build()
					AsyncImage(
						model = imageRequest,
						contentDescription = null,
						modifier = Modifier
							.sharedBounds(
								sharedTransitionScope.rememberSharedContentState(key = "image-${imageUrl}"),
								animatedVisibilityScope = animatedContentScope,
								exit = fadeOut(),
								enter = fadeIn(),
								boundsTransform = boundsTransform,
							)
							.skipToLookaheadSize()
							.weight(1f)
							.padding(Dimens.marginSmall)
							.clip(RoundedCornerShape(Dimens.marginLarge))
							.testTag("PokemonImage"),
						placeholder = painterResource(R.drawable.ic_transparent),
						error = painterResource(id = R.drawable.ic_error),
						contentScale = ContentScale.FillWidth,
					)
				}
			}
			SubtitleText(name = "Abilities", subTitleModifier)
			FlowRow(modifier = Modifier.padding(smallDimensions.marginNormal)) {
				pokemon.abilities.forEach {
					Box(
						modifier = Modifier.padding(smallDimensions.marginNormal),
						contentAlignment = Alignment.Center
					) {
						val stroke = Stroke(
							width = 6f,
							pathEffect = PathEffect.dashPathEffect(floatArrayOf(40f, 10f), 0f)
						)
						val color = Colors.primary
						Canvas(
							Modifier
								.fillMaxWidth()
								.height(70.dp)
						) {
							drawRoundRect(color = color, style = stroke)
						}
						DetailText(text = it)
					}
				}
			}
			SubtitleText(name = "Moves", subTitleModifier)
			FlowRow(modifier = Modifier.padding(smallDimensions.marginNormal)) {
				pokemon.moves.forEach {
					Box(
						modifier = Modifier
							.padding(smallDimensions.marginNormal)
							.clip(TiltedShape())
							.background(color = Colors.primaryContainer),
						contentAlignment = Alignment.Center
					) {
						Box(
							modifier = Modifier
								.padding(smallDimensions.marginSmall)
								.clip(TiltedShape())
								.background(color = Colors.surfaceContainerLow)
								.padding(smallDimensions.marginSmall),
							contentAlignment = Alignment.Center
						) {
							DetailText(text = it)
						}
					}
				}
			}
			SubtitleText(name = "Stats", subTitleModifier)
			Column(modifier = Modifier.padding(smallDimensions.marginNormal)) {

				pokemon.stats.forEach {
					DetailText(text = it.name)
					LinearProgressIndicator(
						modifier = Modifier
							.padding(vertical = smallDimensions.marginLarge, horizontal = smallDimensions.marginNormal)
							.fillMaxWidth(),
						progress = { it.baseStat / STATS_MAXIMUM },
						color = Colors.onPrimaryContainer,
						trackColor = Colors.secondaryContainer
					)
				}

			}
		}
	}
}
