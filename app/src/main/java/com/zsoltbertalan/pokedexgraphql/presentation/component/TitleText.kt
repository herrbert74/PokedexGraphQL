package com.zsoltbertalan.pokedexgraphql.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zsoltbertalan.pokedexgraphql.presentation.design.Colors
import com.zsoltbertalan.pokedexgraphql.presentation.design.PokedexGraphQLTypography
import com.zsoltbertalan.pokedexgraphql.presentation.design.titleMediumBold

@Composable
fun TitleText(modifier: Modifier = Modifier, name: String) {
	Text(
		modifier = modifier
			.fillMaxWidth()
			.padding(horizontal = 8.dp),
		text = name,
		maxLines = 2,
		overflow = TextOverflow.Ellipsis,
		color = Colors.onSurface,
		style = PokedexGraphQLTypography.titleMediumBold
	)
}
