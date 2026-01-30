package com.lestec.pexels.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.lestec.pexels.R
import com.lestec.pexels.domain.Photo
import com.lestec.pexels.ui.theme.Black
import com.lestec.pexels.ui.theme.White

@Composable
fun ImagesGrid(
    onImageClick: (Photo) -> Unit,
    modifier: Modifier,
    atBottom: (Boolean) -> Unit,
    isShowAutor: Boolean,
    photos: List<Photo>
) {
    val itemShape = RoundedCornerShape(12.dp)
    val imagePlaceholder = painterResource(R.drawable.empty_image)

    val state = rememberLazyStaggeredGridState()
    val isAtBottom by remember { derivedStateOf { !state.canScrollForward } }
    LaunchedEffect(isAtBottom) { atBottom(isAtBottom) }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        state = state,
        modifier = modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp),
        verticalItemSpacing = 17.dp,
        horizontalArrangement = Arrangement.spacedBy(17.dp)
    ) {
        items(items = photos) {
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = itemShape
                    )
                    .clip(itemShape)
                    .clickable { onImageClick(it) },
                contentAlignment = Alignment.BottomCenter
            ) {
                AsyncImage(
                    model = it.src.medium,
                    contentDescription = it.alt,
                    placeholder = imagePlaceholder,
                    error = imagePlaceholder,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth()
                )
                if (isShowAutor) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Black.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = it.photographer,
                            color = White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}