package com.lestec.pexels.ui.screen_details.components

import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lestec.pexels.R

@Composable
fun IconSelectableButton(
    onClick: () -> Unit,
    isSelected: Boolean
) {
    FilledIconButton(
        onClick = onClick,
        modifier = Modifier.sizeIn(minWidth = 50.dp, minHeight = 50.dp),
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Icon(
            painter = painterResource(
                if (isSelected) {
                    R.drawable.ic_bookmark_active
                } else {
                    R.drawable.ic_bookmark_inactive
                }
            ),
            contentDescription = stringResource(R.string.bookmarks),
            tint = if (isSelected) {
                Color.Unspecified
            } else {
                MaterialTheme.colorScheme.onBackground
            }
        )
    }
}