package com.lestec.pexels.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.lestec.pexels.domain.Photo
import com.lestec.pexels.ui.components.BottomBar
import com.lestec.pexels.ui.screenBookmarks.BookmarksScreen
import com.lestec.pexels.ui.screenDetails.DetailsScreen
import com.lestec.pexels.ui.screenHome.HomeScreen

internal data object HomeRoute
internal data object BookmarksRoute
internal data class DetailsRoute(val it: Photo)

@Composable
fun Navigation() {
    val backStack = remember { mutableStateListOf<Any>(HomeRoute) }
    fun onBack() { if (backStack.size > 1) backStack.removeAt(backStack.size - 1) }

    NavDisplay(
        backStack = backStack,
        onBack = ::onBack,
        entryProvider = entryProvider {
            entry<HomeRoute> {
                HomeScreen(
                    onImageClick = { backStack.add(DetailsRoute(it)) },
                    bottomBar = {
                        BottomBar(
                            onItemClick = backStack::add,
                            activeRoute = HomeRoute
                        )
                    }
                )
            }
            entry<BookmarksRoute> {
                BookmarksScreen(
                    onImageClick = { backStack.add(DetailsRoute(it)) },
                    bottomBar = {
                        BottomBar(
                            // For now there is only 2 screens in BottomBar,
                            // so only one direction is possible (to Home)
                            onItemClick = { onBack() },
                            activeRoute = BookmarksRoute
                        )
                    }
                )
            }
            entry<DetailsRoute> { DetailsScreen(photo = it.it) }
        }
    )
}