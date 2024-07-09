package com.zsoltbertalan.pokedexgraphql.presentation.ui.pokemons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zsoltbertalan.pokedexgraphql.R
import com.zsoltbertalan.pokedexgraphql.presentation.design.PokedexGraphQLTheme
import com.zsoltbertalan.pokedexgraphql.presentation.design.PokedexGraphQLTypography
import com.zsoltbertalan.pokedexgraphql.domain.model.Pokemon
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun PokemonsScreen(
	stateFlow: StateFlow<PokemonsViewModel.UiState>,
	onItemClick: (Pokemon) -> Unit,
	onSortPokemonsClick: () -> Unit,
) {

	val uiState by stateFlow.collectAsStateWithLifecycle()

	Scaffold(
		topBar = {
			TopAppBar(
				colors = topAppBarColors(
					containerColor = PokedexGraphQLTheme.colorScheme.primaryContainer,
					titleContentColor = PokedexGraphQLTheme.colorScheme.primary,
				),
				title = {
					Text("PokedexGraphQl")
				},
				actions = {
					IconButton(onClick = { onSortPokemonsClick() }) {
						Icon(
							painter = painterResource(R.drawable.ic_sort_by_alpha),
							contentDescription = "Sort icon",
						)
					}
				}
			)
		}
	) { innerPadding ->
		if (uiState.error == null) {
			LazyColumn(
				modifier = Modifier.padding(innerPadding),
				content = {
					items(
						uiState.pokemons.size,
						{ index -> uiState.pokemons[index] }
					) { index ->
						val pokemon = uiState.pokemons[index]
						PokemonRow(
							pokemon = pokemon,
							onItemClicked = onItemClick
						)
					}
				}
			)
		} else {
			ErrorView(innerPadding)
		}
	}
}

@Composable
private fun ErrorView(innerPadding: PaddingValues) {
	Column(
		Modifier
			.fillMaxSize(1f)
			.padding(innerPadding),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Image(
			painter = painterResource(R.drawable.ic_business_error),
			contentDescription = null
		)
		Text(
			"Something went wrong",
			style = PokedexGraphQLTypography.titleLarge,
			modifier = Modifier
				.align(Alignment.CenterHorizontally),
		)
	}
}

@Preview("Pokemons Screen Preview")
@Composable
fun PokemonsScreenPreview() {
	PokemonsScreen(
		MutableStateFlow(PokemonsViewModel.UiState()),
		{},
		{}
	)
}

@Preview("Pokemons Screen Error Preview")
@Composable
fun PokemonsScreenErrorPreview() {
	PokemonsScreen(
		MutableStateFlow(PokemonsViewModel.UiState()),
		{},
		{}
	)
}
