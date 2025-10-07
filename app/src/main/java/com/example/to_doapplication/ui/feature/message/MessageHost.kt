package com.example.to_doapplication.ui.feature.message

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.to_doapplication.ui.components.MessageComponent
import com.example.to_doapplication.viewmodel.MessageViewModel

@Composable
fun MessageHost(
    messageViewModel: MessageViewModel,
    modifier: Modifier = Modifier
) {
    val messages by messageViewModel.messages.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        messages.forEach { message ->
            AnimatedVisibility(
                visible = true,
                enter = slideInHorizontally(initialOffsetX = { it }),
                exit = slideOutHorizontally(targetOffsetX = { it })
            ) {
                MessageComponent(
                    message = message,
                    onDismiss = { id -> messageViewModel.dismissMessage(id) }
                )
            }
        }
    }
}