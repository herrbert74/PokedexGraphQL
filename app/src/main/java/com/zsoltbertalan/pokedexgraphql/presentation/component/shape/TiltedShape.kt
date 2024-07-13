package com.zsoltbertalan.pokedexgraphql.presentation.component.shape

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class TiltedShape : Shape {

	override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
		val path = Path().apply {
			moveTo(40f, 0f)
			lineTo(size.width, 0f)
			lineTo(size.width - 40f, size.height)
			lineTo(0f, size.height)
			close()
		}
		return Outline.Generic(path)
	}

}