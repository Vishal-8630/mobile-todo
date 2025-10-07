package com.example.to_doapplication.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.to_doapplication.R

@Composable
fun TextFieldComponent(
    value: String,
    labelText: String,
    labelColor: Color = colorResource(R.color.input_text_color),
    onValueChange: (String) -> Unit,
    leadingIconVector: ImageVector,
    leadingIconDescription: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    error: String? = null,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column {
        TextField(
            value = value,
            label = { Text(text = labelText, color = labelColor) },
            onValueChange = onValueChange,
            leadingIcon = {
                Icon(
                    imageVector = leadingIconVector,
                    contentDescription = leadingIconDescription,
                    tint = colorResource(R.color.input_text_color)
                )
            },
            trailingIcon = {
                if (isPassword) {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible }
                    ) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        )
                    }
                }
            },
            isError = error != null,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(R.color.input_background_color),
                unfocusedContainerColor = colorResource(R.color.input_background_color),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = colorResource(R.color.border_color),
                    shape = RoundedCornerShape(8.dp)
                ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            visualTransformation = if(isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None
        )
        if(error != null) {
            Text(
                text = error,
                color = Color.Red,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }
    }
}