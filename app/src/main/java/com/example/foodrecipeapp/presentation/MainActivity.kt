package com.example.foodrecipeapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.fragment.NavHostFragment
import com.example.foodrecipeapp.R
import com.example.foodrecipeapp.ui.theme.FoodRecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//   setContentView(R.layout.activity_main)


//        Log.d("APP_DEBUG", "onCreate: $someRandomString")
//        val service = Retrofit.Builder()
//            .baseUrl("https://food2fork.ca/api/recipe/")
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build()
//            .create(RecipeService::class.java)
//        CoroutineScope(IO).launch {
//            val recipe = service.get(
//                token = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48",
//                id = 583
//            )
//            Log.d("MainActivity", "onCreate: ${recipe.title}")
//        }
//        setContent {
//            FoodRecipeAppTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
//            }
//        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FoodRecipeAppTheme {
        Greeting("Android")
    }
}