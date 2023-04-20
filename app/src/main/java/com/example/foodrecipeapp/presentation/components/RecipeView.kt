package com.example.foodrecipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.foodrecipeapp.domain.model.Recipe
import com.example.foodrecipeapp.util.loadPicture

@Composable
fun RecipeView(
   recipe:Recipe
) {
Column(
    modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxWidth()
        .padding(8.dp)
){
    recipe.featuredImage?.let { url->
        val image = loadPicture(url = url, defaultImage = DEFAULT_RECIPE_IMAGE).value
        image?.let{ img->
            Image(bitmap = image.asImageBitmap(), contentDescription ="Recipe Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(225.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
    Column(modifier = Modifier.padding(8.dp)) {
        recipe.title?.let { title->
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        recipe.publisher?.let { publisher->
            Text(
                text = publisher,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        val date = recipe.dateUpdated
        Text(
            text = "Updated $date by ${recipe.publisher}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
    recipe.rating?.let { rating->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            for (i in 1..5) {
                Icon(
                    imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = "Rating",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
    recipe.ingredients.let { ingredients->
        for (ingredient in ingredients) {
            Text(
                text = ingredient,
                modifier=Modifier.fillMaxWidth().padding(bottom = 8.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
}