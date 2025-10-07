package com.example.to_doapplication.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.to_doapplication.R

@Composable
fun ButtonComponent(
    text: String,
    onClick: () -> Unit,
    textColor: Color = Color.White,
    containerColor: Color = colorResource(R.color.brand_color),
    shape: CornerBasedShape =  RoundedCornerShape(8.dp),
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        shape = shape
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}