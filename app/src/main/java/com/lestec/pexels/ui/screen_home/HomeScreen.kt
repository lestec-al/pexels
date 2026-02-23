package com.lestec.pexels.ui.screen_home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lestec.pexels.R
import com.lestec.pexels.ui.components.ImagesGrid
import com.lestec.pexels.ui.screen_home.components.FeaturedRow
import com.lestec.pexels.ui.screen_home.components.SearchField
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    onImageClick: (Long) -> Unit,
    bottomBar: @Composable () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.spacedBy(17.dp)
            ) {
                SearchField(viewModel)
                FeaturedRow(viewModel)
                if (viewModel.isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surface,
                        gapSize = 0.dp
                    )
                }
            }
        },
        bottomBar = bottomBar
    ) { paddingValues ->
        if (viewModel.photos.isEmpty() && !viewModel.isLoading) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (viewModel.photosNotFound) {
                    Text(text = stringResource(R.string.no_results_found))
                } else {
                    Icon(
                        painter = painterResource(R.drawable.no_network_icon),
                        contentDescription = null
                    )
                }
                TextButton(onClick = viewModel::reloadPhotos) {
                    Text(
                        text = stringResource(
                            if (viewModel.photosNotFound) R.string.explore else R.string.try_again
                        ),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        } else {
            ImagesGrid(
                onImageClick = onImageClick,
                modifier = Modifier.padding(paddingValues),
                atBottom = viewModel::addPhotos,
                isShowAutor = false,
                photos = viewModel.photos
            )
        }
    }
}