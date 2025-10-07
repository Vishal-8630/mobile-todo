package com.example.to_doapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.to_doapplication.R
import com.example.to_doapplication.model.ToDo
import com.example.to_doapplication.model.toReadableTime
import com.example.to_doapplication.ui.feature.todo.ToDoStatus
import com.example.to_doapplication.ui.navigation.Routes
import kotlin.math.exp

@Composable
fun ToDoItemCard(
    toDo: ToDo,
    navHostController: NavHostController,
    onStatusSelected: (ToDoStatus) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = toDo.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.brand_color),
                textDecoration = TextDecoration.Underline,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .clickable { navHostController.navigate(Routes.ToDoDetailScreen(toDo.id)) }
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = toDo.updatedAt.toReadableTime(),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = toDo.description,
                fontSize = 18.sp,
                color = colorResource(R.color.input_text_color),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { expanded = !expanded }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = toDo.status,
                    fontSize = 14.sp,
                    color =  Color.Gray
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon",
                    modifier = Modifier.size(22.dp),
                    tint = Color.Gray
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(color = Color.Gray.copy(alpha = 0.2f))
                ) {
                    ToDoStatus.entries.forEach { status ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = status.displayName,
                                    color = Color.Black.copy(alpha = 0.7f)
                                )
                            },
                            onClick = {
                                expanded = false
                                onStatusSelected(status)
                            }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}