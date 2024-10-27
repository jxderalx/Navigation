package com.example.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.navigation.Screen
import com.example.navigation.ui.theme.Blue40
import com.example.navigation.ui.theme.Blue80
import com.example.navigation.ui.theme.NavigationTheme


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationTheme {
                val navController = rememberNavController()
                var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
                var textoUsuario by remember { mutableStateOf("") }
                var textoNuevo by remember { mutableStateOf("{new Text}") }


                val tabs = listOf(
                    NavItem(
                        label = if (selectedTabIndex == 0) {
                            "Pantalla 1"
                        } else {
                            ""
                        },
                        icon = Icons.Filled.Home,
                        screen = Screen.HomeScreen
                    ),
                    NavItem(
                        label = if (selectedTabIndex == 1) {
                            "Pantalla 2"
                        } else {
                            ""
                        },
                        icon = Icons.Filled.Settings,
                        screen = Screen.SettingScreen(textoNuevo)
                    ),
                    NavItem(
                        label = if (selectedTabIndex == 2) {
                            "Pantalla 3"
                        } else {
                            ""
                        },
                        icon = Icons.Filled.Favorite,
                        screen = Screen.ProfileScreen("newText")
                    ),
                )

                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = Blue40,
                                titleContentColor = Color.White
                            ),
                            title = { Text("Top app bar") }
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = Blue40,
                            contentColor = Blue80
                        ) {
                            tabs.mapIndexed { index, navItem ->
                                NavigationBarItem(
                                    selected = selectedTabIndex == index,
                                    onClick = {
                                        selectedTabIndex = index
                                        navController.navigate(navItem.screen)
                                    },
                                    icon = {
                                        Icon(
                                            tint = Color.DarkGray,
                                            imageVector = navItem.icon,
                                            contentDescription = null
                                        )
                                    },
                                    label = {
                                        Text(
                                            color = Color.White,
                                            text = navItem.label
                                        )
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.HomeScreen
                        ) {
                            composable<Screen.HomeScreen> {
                                Column(modifier = Modifier.fillMaxSize().padding(innerPadding), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Pantalla 1", fontSize = 30.sp, color = Color.Red)

                                    OutlinedTextField(
                                        value = textoUsuario,
                                        onValueChange = { textoUsuario = it },
                                        label = { Text("Introducir texto") }
                                    )

                                    TextButton(
                                        onClick = {
                                            navController.navigate(
                                                Screen.SettingScreen(
                                                    textoUsuario
                                                )
                                            )
                                            selectedTabIndex = 1
                                        }, colors = ButtonColors(
                                            containerColor = Blue40,
                                            contentColor = Color.White,
                                            disabledContentColor = Color.White,
                                            disabledContainerColor = Color.Blue
                                        )
                                    ) { Text(text = "Enviar") }

                                }
                            }
                            composable<Screen.SettingScreen> { backStackEntry ->
                                val texto =
                                    backStackEntry.toRoute<Screen.SettingScreen>().textoRecibido
                                Text(text = texto, fontSize = 30.sp, color = Color.Red)
                            }
                            composable<Screen.ProfileScreen> {
                                Text("Pantalla 3", fontSize = 30.sp, color = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NavigationTheme {

    }
}

data class NavItem(
    val screen: Screen,
    val label: String,
    val icon: ImageVector
)