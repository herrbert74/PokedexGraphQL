package com.zsoltbertalan.pokedexgraphql.presentation.ui.pokemons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.zsoltbertalan.pokedexgraphql.domain.model.Pokemon
import com.zsoltbertalan.pokedexgraphql.presentation.design.Dimens
import com.zsoltbertalan.pokedexgraphql.presentation.design.PokedexGraphQLTheme
import com.zsoltbertalan.pokedexgraphql.presentation.design.PokedexGraphQLTypography

@Composable
fun PokemonRow(pokemon: Pokemon, onItemClicked: (Pokemon) -> Unit) {

	Row(modifier = Modifier
		.clickable { onItemClicked(pokemon) }
		.background(PokedexGraphQLTheme.colorScheme.surface)
		.padding(vertical = Dimens.marginNormal, horizontal = Dimens.marginLarge)
		.testTag("PokemonsRow")
	) {

		Text(
			text = pokemon.name,
			style = PokedexGraphQLTypography.titleLarge,
			modifier = Modifier.weight(1.0f)
				.fillMaxWidth()
				.padding(vertical = Dimens.marginNormal, horizontal = Dimens.marginExtraLarge)
				//.recomposeHighlighter()
		)

	}

}
