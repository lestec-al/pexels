package com.lestec.pexels.ui.screenDetails.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lestec.pexels.ui.theme.White

@Composable
fun IconTextButton(
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    text: String
) {
    Row(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = CircleShape
            )
            .clip(CircleShape)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilledIconButton(
            onClick = onClick,
            modifier = Modifier.sizeIn(minWidth = 48.dp, minHeight = 48.dp),
            colors = IconButtonDefaults.filledIconButtonColors(
                contentColor = White
            )
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = text
            )
        }
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 15.dp)
        )
    }
}