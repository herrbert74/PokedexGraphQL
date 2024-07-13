package com.zsoltbertalan.pokedexgraphql.presentation.ui.pokemondetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zsoltbertalan.pokedexgraphql.domain.api.PokemonRepository
import com.zsoltbertalan.pokedexgraphql.domain.model.Failure
import com.zsoltbertalan.pokedexgraphql.domain.model.PokemonDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	private val pokedexGraphQLRepository: PokemonRepository
) : ViewModel() {

	private val _state = MutableStateFlow(UiState())
	val state: StateFlow<UiState> = _state.asStateFlow()

	private val name: String = checkNotNull(savedStateHandle["name"])

	init {
		requestPokemonDetails()
	}

	private fun requestPokemonDetails() {
		viewModelScope.launch {
			Timber.d("zsoltbertalan* requestPokemonDetails name: $name")
			_state.update { it.copy(loading = true) }
			val pokemonDetails = pokedexGraphQLRepository.getPokemon(name)
			Timber.d("zsoltbertalan* requestPokemonDetails: $pokemonDetails")
			_state.update { uiState ->
				when {
					pokemonDetails.isOk -> {
						uiState.copy(
							pokemon = pokemonDetails.value,
							loading = false,
							error = null,
						)
					}

					else -> uiState.copy(loading = false, error = pokemonDetails.error)
				}
			}
		}
	}

	data class UiState(
		val loading: Boolean = false,
		val pokemon: PokemonDetails = PokemonDetails(),
		val error: Failure? = null
	)

}
