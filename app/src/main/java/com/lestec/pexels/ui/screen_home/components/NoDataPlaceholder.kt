package com.lestec.pexels.ui.screen_home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.lestec.pexels.R

@Composable
fun NoDataPlaceholder(
    reloadPhotos: () -> Unit,
    paddingValues: PaddingValues,
    photosNotFound: Boolean
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (photosNotFound) {
            Text(text = stringResource(R.string.no_results_found))
        } else {
            Icon(
                painter = painterResource(R.drawable.no_network_icon),
                contentDescription = null
            )
        }
        TextButton(onClick = reloadPhotos) {
            Text(
                text = stringResource(
                    if (photosNotFound) R.string.explore else R.string.try_again
                ),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}