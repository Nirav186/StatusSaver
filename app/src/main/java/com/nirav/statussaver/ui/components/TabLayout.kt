package com.nirav.statussaver.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nirav.statussaver.ui.theme.BackgroundColor
import com.nirav.statussaver.ui.theme.Primary

@Composable
fun CustomTabLayout(selectedTabPos: Int, onSelectPos: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp, vertical = 20.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = Primary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(0.5f)
                .padding(1.dp)
                .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                .background(if (selectedTabPos == 0) Primary else BackgroundColor)
                .clickable(onClick = {
                    onSelectPos(0)
                })
                .padding(6.dp),
            text = "Images",
            style = TextStyle(
                textAlign = TextAlign.Center,
                color = if (selectedTabPos == 0) Color.Gray else Primary
            )
        )
        Text(
            modifier = Modifier
                .weight(0.5f)
                .padding(1.dp)
                .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
                .background(if (selectedTabPos == 0) BackgroundColor else Primary)
                .clickable(onClick = {
                    onSelectPos(1)
                })
                .padding(6.dp),
            text = "Videos",
            style = TextStyle(
                textAlign = TextAlign.Center,
                color = if (selectedTabPos == 0) Primary else Color.Gray
            )
        )
    }
}