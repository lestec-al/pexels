package com.lestec.pexels.ui.screen_home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lestec.pexels.ui.screen_home.HomeViewModel
import com.lestec.pexels.ui.theme.White

@Composable
fun FeaturedRow(viewModel: HomeViewModel) {
    if (viewModel.featured.isNotEmpty()) {
        val shape = RoundedCornerShape(100.dp)

        LazyRow(state = viewModel.featuredListState) {
            item { Spacer(Modifier.width(18.5.dp)) }
            items(items = viewModel.featured) {
                val isSelected = it.lowercase() == viewModel.searchValue.lowercase()

                Box(
                    modifier = Modifier
                        .padding(horizontal = 5.5.dp)
                        .background(
                            color = if (isSelected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.surface
                            },
                            shape = shape
                        )
                        .clip(shape)
                        .clickable { viewModel.selectFeatured(if (isSelected) "" else it) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                        color = if (isSelected) White else Color.Unspecified
                    )
                }
            }
            item { Spacer(Modifier.width(18.5.dp)) }
        }
    }
}