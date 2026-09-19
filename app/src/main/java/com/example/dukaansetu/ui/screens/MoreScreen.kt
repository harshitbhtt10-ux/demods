package com.example.dukaansetu.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dukaansetu.ui.theme.*

@Composable
fun MoreScreen(
    onNavigateToAi: () -> Unit,
    onLogout: () -> Unit,
    onNavigateToProfile: () -> Unit = {},
    onNavigateToBusinessSettings: () -> Unit = {},
    onNavigateToBackupSync: () -> Unit = {},
    onNavigateToHelpSupport: () -> Unit = {},
    onNavigateToLanguage: () -> Unit = {},
    onNavigateToAboutApp: () -> Unit = {}
) {
    val context = LocalContext.current
    var isDarkMode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(NavyPrimary)
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Text(
                text = "More",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Profile Card
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToProfile() }
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(NavyPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "HB",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Harshit Bhatt",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "harshit@example.com",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Profile",
                        tint = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Settings List
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    SettingsRowItem(
                        title = "Ask My Shop AI",
                        subtitle = "Your AI Business Assistant",
                        icon = Icons.Default.SmartToy,
                        onClick = onNavigateToAi
                    )
                    HorizontalDivider(color = Color(0xFFF1F5F9))

                    SettingsRowItem(
                        title = "Business Settings",
                        subtitle = "GST, Shop Details, Categories",
                        icon = Icons.Default.Settings,
                        onClick = onNavigateToBusinessSettings
                    )
                    HorizontalDivider(color = Color(0xFFF1F5F9))

                    SettingsRowItem(
                        title = "Backup & Sync",
                        subtitle = "Keep your data safe",
                        icon = Icons.Default.CloudUpload,
                        onClick = onNavigateToBackupSync
                    )
                    HorizontalDivider(color = Color(0xFFF1F5F9))

                    SettingsRowItem(
                        title = "Help & Support",
                        subtitle = "FAQ, Contact Us",
                        icon = Icons.Default.HelpOutline,
                        onClick = onNavigateToHelpSupport
                    )
                    HorizontalDivider(color = Color(0xFFF1F5F9))

                    SettingsRowItem(
                        title = "Share App",
                        subtitle = "Invite other shopkeepers & retailers",
                        icon = Icons.Default.Share,
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Try DukaanSetu - Har Dukaan Ka Saathi! Manage your shop, sales & inventory easily. Download now: https://play.google.com/store/apps/details?id=com.example.dukaansetu"
                                )
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share DukaanSetu via"))
                        }
                    )
                    HorizontalDivider(color = Color(0xFFF1F5F9))

                    // Dark Mode row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.background),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Nightlight,
                                    contentDescription = "Dark Mode",
                                    tint = PrimaryBlue,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Dark Mode",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { isDarkMode = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = PrimaryBlue)
                        )
                    }
                    HorizontalDivider(color = Color(0xFFF1F5F9))

                    SettingsRowItem(
                        title = "Language",
                        subtitle = "English (India)",
                        icon = Icons.Default.Language,
                        onClick = onNavigateToLanguage
                    )
                    HorizontalDivider(color = Color(0xFFF1F5F9))

                    SettingsRowItem(
                        title = "About DukkanSetu",
                        subtitle = "Version 1.0.0",
                        icon = Icons.Default.Info,
                        onClick = onNavigateToAboutApp
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Logout Button
            OutlinedButton(
                onClick = onLogout,
                shape = RoundedCornerShape(12.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(brush = SolidColor(RedDanger)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = "Logout",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = RedDanger
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun SettingsRowItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = PrimaryBlue,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(20.dp)
        )
    }
}
