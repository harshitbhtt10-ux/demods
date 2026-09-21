package com.example.dukaansetu.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.dukaansetu.ui.components.DukaanSetuBottomBar
import com.example.dukaansetu.ui.navigation.BottomTab
import com.example.dukaansetu.ui.navigation.Screen
import com.example.dukaansetu.ui.screens.*
import com.example.dukaansetu.ui.viewmodel.ShopViewModel
import kotlinx.coroutines.launch

@Composable
fun DukaanSetuApp() {
    val viewModel: ShopViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val currentLang by viewModel.currentLanguage.collectAsState()
    
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Splash) }
    var currentBottomTab by remember { mutableStateOf(BottomTab.HOME) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            if (currentScreen == Screen.Main) {
                DukaanSetuBottomBar(
                    currentTab = currentBottomTab,
                    langCode = currentLang,
                    onTabSelected = { selectedTab ->
                        currentBottomTab = selectedTab
                    }
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Crossfade(targetState = currentScreen, label = "ScreenTransition") { screen ->
                when (screen) {
                    Screen.Splash -> {
                        SplashScreen(
                            onNavigateToLogin = {
                                currentScreen = Screen.Login
                            }
                        )
                    }

                    Screen.Login -> {
                        LoginScreen(
                            onLoginSuccess = {
                                currentScreen = Screen.Main
                                currentBottomTab = BottomTab.HOME
                            },
                            onNavigateToRegister = {
                                currentScreen = Screen.Register
                            }
                        )
                    }

                    Screen.Main -> {
                        when (currentBottomTab) {
                            BottomTab.HOME -> {
                                HomeScreen(
                                    viewModel = viewModel,
                                    onNavigateToAi = { currentScreen = Screen.AiAssistant },
                                    onNavigateToItems = { currentBottomTab = BottomTab.ITEMS },
                                    onNavigateToReports = { currentScreen = Screen.Reports },
                                    onNavigateToDeadStock = { currentScreen = Screen.DeadStock },
                                    onNavigateToExpiry = { currentScreen = Screen.ExpiryWastage },
                                    onNavigateToAddItem = { currentScreen = Screen.AddItem },
                                    onNavigateToParties = { currentBottomTab = BottomTab.PARTIES },
                                    onNavigateToBills = { currentBottomTab = BottomTab.BILLS },
                                    onNavigateToNotifications = { currentScreen = Screen.Notifications },
                                    onNavigateToMore = { currentBottomTab = BottomTab.MORE }
                                )
                            }

                            BottomTab.ITEMS -> {
                                ItemsScreen(
                                    viewModel = viewModel,
                                    onNavigateToAddItem = { currentScreen = Screen.AddItem },
                                    onNavigateToDeadStock = { currentScreen = Screen.DeadStock }
                                )
                            }

                            BottomTab.BILLS -> {
                                BillsScreen(
                                    viewModel = viewModel,
                                    onNavigateToAddItem = { currentScreen = Screen.AddItem }
                                )
                            }

                            BottomTab.PARTIES -> {
                                PartiesScreen(
                                    viewModel = viewModel,
                                    onNavigateToAddParty = { currentScreen = Screen.AddParty }
                                )
                            }

                            BottomTab.MORE -> {
                                MoreScreen(
                                    onNavigateToAi = { currentScreen = Screen.AiAssistant },
                                    onLogout = { currentScreen = Screen.Login },
                                    onNavigateToProfile = { currentScreen = Screen.UserProfile },
                                    onNavigateToBusinessSettings = { currentScreen = Screen.BusinessSettings },
                                    onNavigateToBackupSync = { currentScreen = Screen.BackupSync },
                                    onNavigateToHelpSupport = { currentScreen = Screen.HelpSupport },
                                    onNavigateToLanguage = { currentScreen = Screen.LanguageSelect },
                                    onNavigateToAboutApp = { currentScreen = Screen.AboutApp }
                                )
                            }
                        }
                    }

                    Screen.AiAssistant -> {
                        AiAssistantScreen(
                            viewModel = viewModel,
                            onBackClick = { currentScreen = Screen.Main }
                        )
                    }

                    Screen.Notifications -> {
                        NotificationsScreen(
                            onBackClick = { currentScreen = Screen.Main }
                        )
                    }

                    Screen.BusinessSettings -> {
                        BusinessSettingsScreen(
                            onBackClick = { currentScreen = Screen.Main }
                        )
                    }

                    Screen.BackupSync -> {
                        BackupSyncScreen(
                            onBackClick = { currentScreen = Screen.Main }
                        )
                    }

                    Screen.HelpSupport -> {
                        HelpSupportScreen(
                            onBackClick = { currentScreen = Screen.Main }
                        )
                    }

                    Screen.LanguageSelect -> {
                        LanguageSelectScreen(
                            viewModel = viewModel,
                            onBackClick = { currentScreen = Screen.Main }
                        )
                    }

                    Screen.AboutApp -> {
                        AboutAppScreen(
                            onBackClick = { currentScreen = Screen.Main }
                        )
                    }

                    Screen.UserProfile -> {
                        UserProfileScreen(
                            onBackClick = { currentScreen = Screen.Main }
                        )
                    }

                    Screen.AddParty -> {
                        AddPartyScreen(
                            viewModel = viewModel,
                            onBackClick = { currentScreen = Screen.Main },
                            onPartyAdded = {
                                currentScreen = Screen.Main
                                currentBottomTab = BottomTab.PARTIES
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Party / Customer added successfully!")
                                }
                            }
                        )
                    }

                    Screen.Register -> {
                        RegisterScreen(
                            onRegisterSuccess = {
                                currentScreen = Screen.Main
                                currentBottomTab = BottomTab.HOME
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Account created successfully! Welcome to DukaanSetu.")
                                }
                            },
                            onNavigateToLogin = {
                                currentScreen = Screen.Login
                            }
                        )
                    }

                    Screen.DeadStock -> {
                        DeadStockScreen(
                            onBackClick = { currentScreen = Screen.Main }
                        )
                    }

                    Screen.ExpiryWastage -> {
                        ExpiryWastageScreen(
                            onBackClick = { currentScreen = Screen.Main }
                        )
                    }

                    Screen.AddItem -> {
                        AddItemScreen(
                            viewModel = viewModel,
                            onBackClick = { currentScreen = Screen.Main },
                            onItemAdded = {
                                currentScreen = Screen.Main
                                currentBottomTab = BottomTab.ITEMS
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Item added successfully!")
                                }
                            }
                        )
                    }

                    Screen.Reports -> {
                        ReportsScreen(
                            onBackClick = { currentScreen = Screen.Main }
                        )
                    }
                }
            }
        }
    }
}
