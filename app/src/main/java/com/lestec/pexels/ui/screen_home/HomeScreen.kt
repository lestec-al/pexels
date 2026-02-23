package com.lestec.pexels.ui.screen_home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lestec.pexels.ui.components.ImagesGrid
import com.lestec.pexels.ui.screen_home.components.FeaturedRow
import com.lestec.pexels.ui.screen_home.components.NoDataPlaceholder
import com.lestec.pexels.ui.screen_home.components.SearchField
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    onImageClick: (Long) -> Unit,
    bottomBar: @Composable () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    // UI must be dumb (only show), but make that logic in viewModel makes it too complicated
    val isAtBottom by remember { derivedStateOf { !viewModel.photosGridState.canScrollForward } }
    LaunchedEffect(isAtBottom) { viewModel.addPhotos(isAtBottom) }

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
            NoDataPlaceholder(
                reloadPhotos = viewModel::reloadPhotos,
                paddingValues = paddingValues,
                photosNotFound = viewModel.photosNotFound
            )
        } else {
            ImagesGrid(
                onImageClick = onImageClick,
                modifier = Modifier.padding(paddingValues),
                isShowAutor = false,
                photos = viewModel.photos,
                gridState = viewModel.photosGridState
            )
        }
    }
}