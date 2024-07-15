package com.zsoltbertalan.pokedexgraphql.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.zsoltbertalan.pokedexgraphql.R
import com.zsoltbertalan.pokedexgraphql.presentation.design.Colors

@Composable
fun ThumbnailCard(modifier: Modifier = Modifier, posterThumbnail: String) {
	val painter = rememberAsyncImagePainter(
		model = ImageRequest.Builder(LocalContext.current)
			.data(posterThumbnail)
			.diskCachePolicy(CachePolicy.ENABLED)
			.size(200, 380)
			.build()
	)
	val painterRem by remember(posterThumbnail) {
		mutableStateOf(painter)
	}
	when (painterRem.state) {
		is AsyncImagePainter.State.Success -> {
			Box(
				modifier = modifier
					.width(180.dp)
					.height(240.dp),
			) {
				Image(
					painter = painterRem,
					contentDescription = "",
					modifier = modifier.fillMaxWidth(),
					contentScale = ContentScale.FillWidth
				)
			}
		}

		is AsyncImagePainter.State.Error -> {
			Box(
				modifier = modifier
					.padding(start = 16.dp, top = 8.dp, end = 8.dp, bottom = 24.dp)
					.width(100.dp)
					.height(190.dp),
			) {
				Icon(
					modifier = Modifier
						.align(Alignment.Center),
					painter = painterResource(id = R.drawable.ic_error),
					contentDescription = null,
				)
			}
		}

		is AsyncImagePainter.State.Loading -> {
			Box(
				modifier = modifier
					.padding(start = 16.dp, top = 8.dp, end = 8.dp, bottom = 24.dp)
					.width(120.dp)
					.height(200.dp),
			) {
				CircularProgressIndicator(
					modifier = Modifier
						.align(Alignment.Center),
					color = Colors.primary
				)
			}
		}

		else -> Unit
	}
}
