package com.zsoltbertalan.pokedexgraphql.presentation.ui.pokemons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.zsoltbertalan.pokedexgraphql.R
import com.zsoltbertalan.pokedexgraphql.presentation.design.PokedexGraphQLTheme
import com.zsoltbertalan.pokedexgraphql.presentation.design.PokedexGraphQLTypography
import com.zsoltbertalan.pokedexgraphql.domain.model.Pokemon
import com.zsoltbertalan.pokedexgraphql.presentation.component.PokemonCard
import com.zsoltbertalan.pokedexgraphql.presentation.component.ShowLoading
import com.zsoltbertalan.pokedexgraphql.presentation.design.Colors

@Composable
fun PokemonsScreen(
	pokemonList: LazyPagingItems<Pokemon>,
	onItemClick: (String) -> Unit,
) {

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

				}
			)
		}
	) { paddingValues ->
		LazyColumn(
			modifier = Modifier
				.background(Colors.surface)
				.padding(paddingValues)
		) {
			showPokemons(pokemonList, onItemClick)
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

private fun LazyListScope.showPokemons(
	pokemonLazyPagingItems: LazyPagingItems<Pokemon>,
	onItemClick: (String) -> Unit,
) {

	items(pokemonLazyPagingItems.itemCount) { index ->
		pokemonLazyPagingItems[index].let {
			it?.let {
				PokemonCard(
					id = it.id.toString(),
					name = it.name,
					imageUrl = it.imageUrl,
					onItemClick = onItemClick
				)
			}

		}
	}

	item {
		Spacer(modifier = Modifier.height(20.dp))
	}

	when {
		pokemonLazyPagingItems.loadState.refresh is LoadState.Loading -> {
			item { ShowLoading(text = stringResource(id = R.string.loading)) }
		}

		pokemonLazyPagingItems.loadState.append is LoadState.Loading -> {
			item { ShowLoading(text = stringResource(id = R.string.loading)) }
		}

		pokemonLazyPagingItems.loadState.refresh is LoadState.Error -> {
			item { Text(text = "Not Loading") }
		}
	}
}

//@Preview("Pokemons Screen Preview")
//@Composable
//fun PokemonsScreenPreview() {
//	PokemonsScreen(
//		MutableStateFlow(PokemonsViewModel.UiState()),
//		{},
//		{}
//	)
//}
//
//@Preview("Pokemons Screen Error Preview")
//@Composable
//fun PokemonsScreenErrorPreview() {
//	PokemonsScreen(
//		MutableStateFlow(PokemonsViewModel.UiState()),
//		{},
//		{}
//	)
//}
