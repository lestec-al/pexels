package com.lestec.pexels.ui

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lestec.pexels.R
import com.lestec.pexels.domain.Photo
import com.lestec.pexels.ui.components.ImagesGrid
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarksScreen(
    onImageClick: (Photo) -> Unit,
    bottomBar: @Composable () -> Unit,
    viewModel: MainViewModel = koinViewModel()
) {
    val onBackDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    // Using only one viewModel, so put init (for screen) here
    LaunchedEffect(Unit) { viewModel.loadLocalPhotos() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    Text(
                        text = stringResource(R.string.bookmarks),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                modifier = Modifier.padding(top = 12.dp)
            )
        },
        bottomBar = bottomBar
    ) { paddingValues ->
        if (viewModel.localPhotos.isEmpty()) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(R.string.you_not_saved_anything_yet))
                TextButton(onClick = { onBackDispatcher?.onBackPressed() }) {
                    Text(
                        text = stringResource(R.string.explore),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        } else {
            ImagesGrid(
                onImageClick = onImageClick,
                modifier = Modifier.padding(paddingValues),
                atBottom = {},
                isShowAutor = true,
                photos = viewModel.localPhotos
            )
        }
    }
}