package com.example.dukaansetu.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Main : Screen("main")
    object AiAssistant : Screen("ai_assistant")
    object DeadStock : Screen("dead_stock")
    object ExpiryWastage : Screen("expiry_wastage")
    object AddItem : Screen("add_item")
    object Reports : Screen("reports")
    object Notifications : Screen("notifications")
    object BusinessSettings : Screen("business_settings")
    object BackupSync : Screen("backup_sync")
    object HelpSupport : Screen("help_support")
    object LanguageSelect : Screen("language_select")
    object AboutApp : Screen("about_app")
    object UserProfile : Screen("user_profile")
    object AddParty : Screen("add_party")
    object Register : Screen("register")
}

enum class BottomTab(val title: String, val route: String) {
    HOME("Home", "home_tab"),
    ITEMS("Items", "items_tab"),
    BILLS("Bills", "bills_tab"),
    PARTIES("Parties", "parties_tab"),
    MORE("More", "more_tab")
}
