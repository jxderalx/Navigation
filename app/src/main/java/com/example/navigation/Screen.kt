package com.example.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object HomeScreen : Screen()

    @Serializable
    data class SettingScreen(var textoRecibido: String) : Screen()

    @Serializable
    data class ProfileScreen(val userId: String) : Screen()

}