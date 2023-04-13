package com.example.foodrecipeapp.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.foodrecipeapp.R

class RecipeFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply{
            setContent {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "This is Recipe Fragment",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(onClick = {
//                        findNavController().navigate(R.id.viewRecipe)

//                        TODO: Navigate to recipe list fragment
                    }) {
                        Text(text = "To recipe list fragment")
                    }
                }
            }
        }
    }
}







