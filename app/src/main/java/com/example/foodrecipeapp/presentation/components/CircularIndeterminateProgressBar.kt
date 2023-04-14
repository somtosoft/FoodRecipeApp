package com.example.foodrecipeapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CircularIndeterminateProgressBar(
    isDisplayed: Boolean,
) {
    if (isDisplayed) {
       Row(modifier = Modifier.fillMaxWidth().padding(50.dp), horizontalArrangement = Arrangement.Center) {
           CircularProgressIndicator(
               modifier = Modifier
                   .fillMaxSize()
                   .wrapContentSize(Alignment.Center),
               color = MaterialTheme.colorScheme.primary
           )
       }
    }
}
