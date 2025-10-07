package com.example.to_doapplication.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.to_doapplication.R

@Composable
fun AuthNavigationTextComponent(
    staticText: String,
    actionText: String,
    onClickAction: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = staticText,
            fontSize = 16.sp,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = actionText,
            textDecoration = TextDecoration.Underline,
            color = colorResource(R.color.brand_color),
            fontSize = 16.sp,
            modifier = Modifier.clickable { onClickAction() }
        )
    }
}