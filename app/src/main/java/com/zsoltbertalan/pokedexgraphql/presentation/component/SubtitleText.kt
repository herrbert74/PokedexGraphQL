package com.zsoltbertalan.pokedexgraphql.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.zsoltbertalan.pokedexgraphql.presentation.design.Colors
import com.zsoltbertalan.pokedexgraphql.presentation.design.PokedexGraphQLTypography
import com.zsoltbertalan.pokedexgraphql.presentation.design.headlineMediumBold
import com.zsoltbertalan.pokedexgraphql.presentation.design.smallDimensions

@Composable
fun SubtitleText(
	name: String,
	modifier: Modifier = Modifier
) {
	Text(
		modifier = modifier,
		text = name,
		maxLines = 2,
		overflow = TextOverflow.Ellipsis,
		color = Colors.onSurface,
		style = PokedexGraphQLTypography.headlineMediumBold
	)
}

/**
 * Modifier to pass in to SubtitleText by default. Sometimes you want to override this because the parent has these
 * paddings already.
 */
val subTitleModifier = Modifier
	.padding(horizontal = smallDimensions.marginLarge, vertical = smallDimensions.marginLarge)
