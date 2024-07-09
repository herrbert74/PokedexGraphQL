package com.zsoltbertalan.pokedexgraphql.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.zsoltbertalan.pokedexgraphql.presentation.design.PokedexGraphQLTheme
import com.zsoltbertalan.pokedexgraphql.presentation.navigation.NavHostContainer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokedexGraphQLActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			PokedexGraphQLTheme {
				val navController = rememberNavController()

				NavHostContainer(navController = navController)
			}
		}
	}

}
