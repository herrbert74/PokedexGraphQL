package com.zsoltbertalan.pokedexgraphql.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zsoltbertalan.pokedexgraphql.presentation.design.smallDimensions

@Composable
fun LargeText(modifier: Modifier = Modifier, rating: String) {
	Text(
		modifier = modifier
			.fillMaxWidth()
			.padding(vertical = smallDimensions.marginLarge),
		text = rating,
		maxLines = 1,
		style = MaterialTheme.typography.headlineMedium,
	)
}
