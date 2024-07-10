package com.zsoltbertalan.pokedexgraphql.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zsoltbertalan.pokedexgraphql.presentation.design.Colors

@Composable
fun RatingText(modifier: Modifier = Modifier, rating: String) {
	Text(
		modifier = modifier
			.fillMaxWidth()
			.padding(end = 8.dp, top = 8.dp),
		text = rating,
		textAlign = TextAlign.End,
		fontSize = 14.sp,
		maxLines = 1,
		style = MaterialTheme.typography.headlineSmall,
		color = Colors.tertiary,
	)
}
