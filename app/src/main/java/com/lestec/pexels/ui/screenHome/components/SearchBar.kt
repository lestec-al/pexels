package com.lestec.pexels.ui.screenHome.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.inputFieldColors
import androidx.compose.material3.Text
import androidx.compose.material3.TopSearchBar
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.lestec.pexels.R
import com.lestec.pexels.ui.screenHome.HomeViewModel
import com.lestec.pexels.ui.theme.DarkGrayOnLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(viewModel: HomeViewModel) {
    val searchText = stringResource(R.string.search)

    TopSearchBar(
        state = rememberSearchBarState(),
        inputField = {
            SearchBarDefaults.InputField(
                query = viewModel.searchValue,
                onQueryChange = viewModel::updateSearchValue,
                onSearch = viewModel::onSearch,
                expanded = false,
                onExpandedChange = {},
                placeholder = { Text(text = searchText) },
                leadingIcon = {
                    IconButton(onClick = { viewModel.onSearch(viewModel.searchValue) }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = searchText,
                            tint = Color.Unspecified
                        )
                    }
                },
                trailingIcon = if (viewModel.searchValue.isEmpty()) null else {
                    {
                        IconButton(onClick = { viewModel.selectFeatured("") }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_close),
                                contentDescription = "close",
                                tint = DarkGrayOnLight
                            )
                        }
                    }
                },
                colors = inputFieldColors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    )
}