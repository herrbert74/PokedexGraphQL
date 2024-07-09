package com.zsoltbertalan.pokedexgraphql.presentation.ui.pokemondetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zsoltbertalan.pokedexgraphql.domain.api.PokemonRepository
import com.zsoltbertalan.pokedexgraphql.domain.model.Pokemon
import com.zsoltbertalan.pokedexgraphql.domain.model.Failure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	private val pokedexGraphQLRepository: PokemonRepository
) : ViewModel() {

	private val _state = MutableStateFlow(UiState())
	val state: StateFlow<UiState> = _state.asStateFlow()

	private val pokemonId: Int = checkNotNull(savedStateHandle["pokemonId"])

	init {
		requestPokemonDetails()
	}

	private fun requestPokemonDetails() {
		viewModelScope.launch {
			_state.update { it.copy(loading = true) }
			pokedexGraphQLRepository.getPokemon(pokemonId).collect { result ->
				_state.update { uiState ->
					when {
						result.isOk -> {
							uiState.copy(
								pokemon = result.value,
								loading = false,
								error = null,
							)
						}
						else -> uiState.copy(loading = false, error = result.error)
					}
				}
			}
		}
	}

	data class UiState(
		val loading: Boolean = false,
		val pokemon: Pokemon = Pokemon(),
		val error: Failure? = null
	)

}
