package com.example.foodrecipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.domain.model.Recipe
import com.example.foodrecipeapp.util.loadPicture

const val DEFAULT_RECIPE_IMAGE = R.drawable.empty_plate
@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(6.dp, 6.dp)
            .fillMaxSize()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation()
    ) {
        Column() {
            recipe.featuredImage?.let { url ->
                val image = loadPicture(url,defaultImage =DEFAULT_RECIPE_IMAGE).value
                image?.let{ img->
                  Image(bitmap = image.asImageBitmap(), contentDescription ="Recipe Image",
                      modifier = Modifier
                          .fillMaxWidth()
                          .height(225.dp),
                      contentScale = ContentScale.Crop
                  )
                }
            }
            recipe.title?.let { title ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp, 12.dp)
                ) {
                    Text(
                        text = title, style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = recipe.rating.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.CenterVertically)
                    )

                }
            }
        }

    }
}