package com.lestec.pexels

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.lestec.pexels.ui.Navigation
import com.lestec.pexels.ui.screenHome.HomeViewModel
import com.lestec.pexels.ui.theme.PexelsTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val vm = getViewModel<HomeViewModel>()
        splashscreen.setKeepOnScreenCondition { vm.isLoading }

        setContent { PexelsTheme { Navigation() } }
    }
}