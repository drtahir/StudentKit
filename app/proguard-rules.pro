# Keep all data models, question banks, and ViewModels
-keep class com.drtahir.studentkit.data.** { *; }
-keep class com.drtahir.studentkit.ui.screens.** { *; }
-keep class com.drtahir.studentkit.viewmodel.** { *; }

# Keep Kotlin reflection and coroutines metadata
-keepclassmembers class * {
    @androidx.room.* <fields>;
    @androidx.room.* <methods>;
}

# Keep Compose runtime
-keep class androidx.compose.** { *; }

