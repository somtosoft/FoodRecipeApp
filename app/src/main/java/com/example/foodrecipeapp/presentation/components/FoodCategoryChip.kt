package com.example.foodrecipeapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FoodCategoryChip(
    category: String,
    isSelected: Boolean = false,
    onSelectedCategoryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit
) {
    ElevatedAssistChip(
        modifier = Modifier.padding(6.dp, 0.dp),
        enabled = true,
        colors = if (isSelected) AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            labelColor = MaterialTheme.colorScheme.primary,
        )
        else AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            labelColor = MaterialTheme.colorScheme.error,
        ),
        onClick = {
            onSelectedCategoryChanged(category)
            onExecuteSearch()
        }, label = { Text(text = category) })
}