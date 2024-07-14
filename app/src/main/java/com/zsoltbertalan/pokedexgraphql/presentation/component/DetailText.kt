package com.zsoltbertalan.pokedexgraphql.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.zsoltbertalan.pokedexgraphql.presentation.design.Colors
import com.zsoltbertalan.pokedexgraphql.presentation.design.smallDimensions
import com.zsoltbertalan.pokedexgraphql.presentation.design.titleLargeBold

@Composable
fun DetailText(text: String, modifier: Modifier = Modifier) {
	Text(
		modifier = modifier
			.padding(vertical = smallDimensions.marginNormal, horizontal = smallDimensions.marginNormal),
		text = text,
		maxLines = 1,
		style = MaterialTheme.typography.titleLargeBold,
		color = Colors.onSurface,
		overflow = TextOverflow.Ellipsis,
	)
}
