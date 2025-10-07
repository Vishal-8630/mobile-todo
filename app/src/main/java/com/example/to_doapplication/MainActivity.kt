package com.example.to_doapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.to_doapplication.ui.feature.message.MessageHost
import com.example.to_doapplication.ui.navigation.AppNavHost
import com.example.to_doapplication.ui.theme.ToDoApplicationTheme
import com.example.to_doapplication.viewmodel.MessageViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoApplicationTheme {
                val messageViewModel: MessageViewModel = hiltViewModel()
                Scaffold(
                    modifier = Modifier.fillMaxSize().systemBarsPadding()
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavHost(messageViewModel)
                        MessageHost(
                            messageViewModel,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}