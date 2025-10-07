package com.example.to_doapplication.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.to_doapplication.R
import com.example.to_doapplication.model.User
import com.example.to_doapplication.model.toReadableString

@Composable
fun UserItemCard(
    user: User
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(12.dp)
    ) {
        Text(
            text = user.username,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.brand_color)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Created at:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = user.createdAt.toReadableString(),
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}