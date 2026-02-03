# Pexels - Android App

## Features
- Browse popular photos, search photos
- View photo details, save to bookmarks
- Dark/light theme

## Implement Tech Stack
- Android SDK, Kotlin, Jetpack Compose
- Compose Navigation 3
- Ktor (networking)
- Coroutines (concurrency)
- Room (local storage)
- Coil (image loading)
- Splash Screen API
- Dependency Injection: Koin
- Architecture: Single Activity, Clean Architecture, MVVM

## Setup
1. Get API key from [Pexels](https://www.pexels.com/api/)
2. Create file `keys.properties`
3. Add there `API_KEY=` with your key

## Project Status
#### Done:
- All main screens (Home, Details, Bookmarks)
- Basic functionality: browse, search, save
- UI according to Figma design
- Theme support
#### Partial / Not done (due to lack of time):
- Caching
- Auto-search on typing (works on button press)
- Pagination in Bookmarks (loads all at once)
- Russian localization
- Complex animations
- Toast messages
- Image zoom gestures
- Minor UI inconsistencies (e.g., fonts)
- Downloading picture issues