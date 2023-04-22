package com.example.foodrecipeapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun ConnectivityMonitor(isNetworkAvailable: Boolean) {
    if (!isNetworkAvailable) {
        Column(modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.errorContainer)) {
            Text(
                text = "No Internet Connection",
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally).padding(vertical=8.dp),
                style = MaterialTheme.typography.bodyMedium.plus(TextStyle(color = MaterialTheme.colorScheme.onErrorContainer))
            )
        }
    }
}