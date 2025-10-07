package com.example.to_doapplication.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleComponent(
    title: String,
    color: Color = Color.Black
) {
    Text(
        text = title,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(12.dp).fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = color
    )
}