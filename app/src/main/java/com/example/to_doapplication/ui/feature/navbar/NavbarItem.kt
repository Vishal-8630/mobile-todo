package com.example.to_doapplication.ui.feature.navbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.to_doapplication.R

@Composable
fun RowScope.NavbarItem(
    imageVector: ImageVector,
    contentDescription: String,
    text: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    val activeColor = colorResource(R.color.brand_color)
    val inactiveColor = colorResource(R.color.inactive_icon_color)

    Column(
        modifier = Modifier.weight(1f).clickable { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = if (isActive) activeColor  else inactiveColor,
            modifier = Modifier.size(35.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            color = if (isActive) activeColor else inactiveColor,
            fontSize = 20.sp
        )
    }
}