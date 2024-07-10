package com.zsoltbertalan.pokedexgraphql.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zsoltbertalan.pokedexgraphql.presentation.design.Colors

@Composable
fun PokemonCard(
	modifier: Modifier = Modifier,
	name: String?,
	type: String?,
	region: String?,
	imageUrl: String?,
) {
	Box(
		modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 0.dp)
	) {
		ShowDetailCard(
			name = name,
			region = region,
			type = type,
		)
		imageUrl?.let {
			ThumbnailCard(posterThumbnail = it)
		}
	}
}

@Composable
fun ShowDetailCard(
	modifier: Modifier = Modifier,
	name: String?,
	region: String?,
	type: String?,
) {
	Card(
		modifier = modifier
			.fillMaxWidth()
			.padding(top = 45.dp)
			.height(200.dp),
		shape = RoundedCornerShape(8.dp),
		elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
		colors = CardDefaults.cardColors(containerColor = Colors.surfaceContainerHighest)
	) {
		Column(
			modifier = Modifier
				.padding(start = 140.dp)
		) {
			type?.let {
				RatingText(rating = it)
			}
			name?.let {
				TitleText(name = it)
			}
			region?.let {
				DescriptionText(description = it)
			}
		}
	}
}
