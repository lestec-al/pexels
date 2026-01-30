package com.lestec.pexels.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.BottomBarItem(
    onItemClick: () -> Unit,
    isSelected: Boolean,
    @DrawableRes activeIcon: Int,
    @DrawableRes inactiveIcon: Int,
) {
    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .height(2.dp)
                .width(24.dp)
                .background(
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.Unspecified
                    },
                    shape = RoundedCornerShape(10.dp)
                )
        )
        IconButton(onClick = { if (!isSelected) onItemClick() }) {
            Icon(
                painter = painterResource(if (isSelected) activeIcon else inactiveIcon),
                contentDescription = "",
                tint = Color.Unspecified
            )
        }
    }
}