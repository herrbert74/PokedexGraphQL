package com.zsoltbertalan.pokedexgraphql.presentation.ui.pokemons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zsoltbertalan.pokedexgraphql.domain.api.PokemonRepository
import com.zsoltbertalan.pokedexgraphql.domain.model.Pokemon
import com.zsoltbertalan.pokedexgraphql.domain.model.Failure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonsViewModel @Inject constructor(pokemonRepository: PokemonRepository) : ViewModel() {

	private val _state = MutableStateFlow(UiState())
	val state: StateFlow<UiState> = _state.asStateFlow()

	val pokemons : Flow<PagingData<Pokemon>> = pokemonRepository.getPokemonPageFlow().cachedIn(viewModelScope)

	data class UiState(
		val loading: Boolean = false,
		val pokemons: List<Pokemon> = emptyList(),
		val pokemonId: String? = null,
		val error: Failure? = null
	)

}
