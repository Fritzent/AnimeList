package com.example.animelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.animelist.navigation.NavigationHandler
import com.example.animelist.screen.HomeScreen
import com.example.animelist.ui.theme.AnimeListTheme
import dagger.hilt.android.AndroidEntryPoint

/*To tell main activity that we want to inject some dependecy*/
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeListTheme {
                NavigationHandler()
            }
        }
    }
}
