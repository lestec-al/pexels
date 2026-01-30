package com.lestec.pexels

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.lestec.pexels.ui.MainViewModel
import com.lestec.pexels.ui.Navigation
import com.lestec.pexels.ui.theme.PexelsTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        var keepSplashScreen = true

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        splashscreen.setKeepOnScreenCondition { keepSplashScreen }
        lifecycleScope.launch {
            val vm = getViewModel<MainViewModel>()
            while (vm.isLoading) {
                delay(200)
            }
            keepSplashScreen = false
        }

        setContent { PexelsTheme { Navigation() } }
    }
}