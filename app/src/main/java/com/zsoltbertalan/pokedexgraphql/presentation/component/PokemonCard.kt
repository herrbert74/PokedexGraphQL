package com.zsoltbertalan.pokedexgraphql.presentation.component

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zsoltbertalan.pokedexgraphql.presentation.design.Colors
import com.zsoltbertalan.pokedexgraphql.presentation.design.PokedexGraphQLTheme
import com.zsoltbertalan.pokedexgraphql.presentation.design.smallDimensions

@Composable
fun PokemonCard(
	modifier: Modifier = Modifier,
	id: String,
	name: String?,
	imageUrl: String?,
) {

	Box(
		modifier = modifier.padding(horizontal = smallDimensions.marginLarge)
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
			Column(
				modifier = Modifier
					.fillMaxSize()
					.padding(start = smallDimensions.marginLarge, end = 200.dp),
				verticalArrangement = Arrangement.Center,
				horizontalAlignment = Alignment.Start,
			) {
				RatingText(rating = id)
				name?.let { TitleText(name = it) }
			}
		}
		imageUrl?.let {
			ThumbnailCard(
				modifier = Modifier
					.align(Alignment.CenterEnd)
					.padding(bottom = smallDimensions.marginExtraLarge),
				posterThumbnail = it
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
			imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png"
		)
	}
}
