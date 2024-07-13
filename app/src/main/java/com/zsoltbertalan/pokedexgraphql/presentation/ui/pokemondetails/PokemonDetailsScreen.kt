package com.zsoltbertalan.pokedexgraphql.presentation.ui.pokemondetails

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
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
import com.zsoltbertalan.pokedexgraphql.presentation.component.TitleText
import com.zsoltbertalan.pokedexgraphql.presentation.component.shape.TiltedShape
import com.zsoltbertalan.pokedexgraphql.presentation.design.Colors
import com.zsoltbertalan.pokedexgraphql.presentation.design.Dimens
import com.zsoltbertalan.pokedexgraphql.presentation.design.PokedexGraphQLTheme
import com.zsoltbertalan.pokedexgraphql.presentation.design.PokedexGraphQLTypography
import com.zsoltbertalan.pokedexgraphql.presentation.design.smallDimensions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PokemonDetailsScreen(
	stateFlow: StateFlow<PokemonDetailsViewModel.UiState>,
	onBackClick: () -> Unit,
) {

	val uiState by stateFlow.collectAsStateWithLifecycle()

	BackHandler(onBack = { onBackClick() })

	val pokemon = uiState.pokemon

	Scaffold(
		topBar = {
			TopAppBar(
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = PokedexGraphQLTheme.colorScheme.primaryContainer,
					titleContentColor = PokedexGraphQLTheme.colorScheme.primary,
				),
				title = {
					Text(uiState.pokemon.name)
				},
			)
		}
	) { innerPadding ->
		LazyColumn(
			modifier = Modifier
				.padding(innerPadding)
				.testTag("PokemonDetail")
		) {
			item {
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
							.testTag("PokemonsRow")
					) {

						Text(
							text = "Type: ${pokemon.types}",
							style = PokedexGraphQLTypography.bodyLarge,
						)
					}

					val imageRequest = ImageRequest.Builder(LocalContext.current)
						.data(pokemon.imageUrl)
						.dispatcher(Dispatchers.IO)
						.memoryCacheKey(pokemon.imageUrl)
						.diskCacheKey(pokemon.imageUrl)
						.diskCachePolicy(CachePolicy.ENABLED)
						.memoryCachePolicy(CachePolicy.ENABLED)
						.build()
					AsyncImage(
						model = imageRequest,
						contentDescription = null,
						modifier = Modifier
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
			item { TitleText(modifier = Modifier.padding(smallDimensions.marginNormal), name = "Abilities") }
			item {
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
							Text(text = it)
						}
					}
				}
			}
			item { TitleText(modifier = Modifier.padding(smallDimensions.marginNormal), name = "Moves") }
			item {
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
								Text(modifier = Modifier.padding(smallDimensions.marginNormal), text = it, maxLines = 1)
							}
						}
					}
				}
			}
			item { TitleText(modifier = Modifier.padding(smallDimensions.marginNormal), name = "Stats") }
			item {
				FlowRow(modifier = Modifier.padding(smallDimensions.marginNormal)) {
					pokemon.stats.forEach {
						Box(
							modifier = Modifier
								.padding(smallDimensions.marginNormal)
								.clip(TiltedShape())
								.background(color = Colors.tertiaryContainer),
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
								Text(
									modifier = Modifier.padding(smallDimensions.marginNormal),
									text = it.name,
									maxLines = 1
								)
							}
						}
					}
				}
			}
		}
	}
}
