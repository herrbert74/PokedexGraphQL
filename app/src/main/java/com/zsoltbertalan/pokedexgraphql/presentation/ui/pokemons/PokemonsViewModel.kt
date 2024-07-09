package com.zsoltbertalan.pokedexgraphql.presentation.ui.pokemons

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
class PokemonsViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) : ViewModel() {

	private val _state = MutableStateFlow(UiState())
	val state: StateFlow<UiState> = _state.asStateFlow()

	init {
		requestPokemons()
	}

	fun requestPokemons() {
		viewModelScope.launch {
			_state.update { it.copy(loading = true) }
			pokemonRepository.getAllPokemon().collect { result ->
				_state.update { uiState ->
					when {
						result.isOk -> {
							uiState.copy(
								pokemons = result.value,
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
		val pokemons: List<Pokemon> = emptyList(),
		val pokemonId: String? = null,
		val error: Failure? = null
	)

}
