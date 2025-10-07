package com.example.to_doapplication.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.to_doapplication.R
import com.example.to_doapplication.model.AppMessage
import kotlinx.coroutines.delay

@Composable
fun MessageComponent(
    message: AppMessage,
    onDismiss: (String) -> Unit
) {
    var progress by remember { mutableStateOf(1f) }

    LaunchedEffect(message.id) {
        val steps = message.durationMillis / 50L
        repeat(steps.toInt()) {
            delay(50L)
            progress -= 1f / steps
        }
        onDismiss(message.id)
    }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(0.8f)
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.brand_color)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message.text,
                    modifier = Modifier.weight(1f),
                    color = Color.White
                )
                IconButton(
                    onClick = { onDismiss(message.id) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
            }
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                color = Color.White,
                trackColor = Color.White.copy(alpha = 0.6f)
            )
        }
    }
}