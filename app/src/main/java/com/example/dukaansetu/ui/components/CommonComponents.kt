package com.example.dukaansetu.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dukaansetu.R
import com.example.dukaansetu.ui.navigation.BottomTab
import com.example.dukaansetu.ui.theme.*

@Composable
fun DukaanSetuLogo(
    modifier: Modifier = Modifier,
    size: Int = 64
) {
    Image(
        painter = painterResource(id = R.drawable.official_dukaansetu_logo),
        contentDescription = "DukaanSetu Logo",
        contentScale = ContentScale.Fit,
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
    )
}

@Composable
fun DukaanSetuOfficialLogoHeader(
    modifier: Modifier = Modifier,
    logoSize: Int = 140,
    isDarkBg: Boolean = false
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Official PDF Extracted High-Res Logo Badge
        Image(
            painter = painterResource(id = R.drawable.official_dukaansetu_logo),
            contentDescription = "DukaanSetu Official Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(logoSize.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Tagline
        Text(
            text = "— Aapki Dukaan Ka Digital Saathi —",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isDarkBg) Color.White.copy(alpha = 0.9f) else TextSecondary
        )
    }
}

@Composable
fun DukaanSetuTopBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    trailingIcon: ImageVector? = null,
    onTrailingClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(NavyPrimary)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBackClick != null) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        if (trailingIcon != null && onTrailingClick != null) {
            IconButton(
                onClick = onTrailingClick,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = "Action",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun DukaanSetuBottomBar(
    currentTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit
) {
    Surface(
        color = NavyPrimary,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomTabItem(
                tab = BottomTab.HOME,
                isSelected = currentTab == BottomTab.HOME,
                icon = Icons.Outlined.Home,
                selectedIcon = Icons.Filled.Home,
                onClick = { onTabSelected(BottomTab.HOME) }
            )
            BottomTabItem(
                tab = BottomTab.ITEMS,
                isSelected = currentTab == BottomTab.ITEMS,
                icon = Icons.Outlined.Inventory2,
                selectedIcon = Icons.Filled.Inventory2,
                onClick = { onTabSelected(BottomTab.ITEMS) }
            )
            BottomTabItem(
                tab = BottomTab.BILLS,
                isSelected = currentTab == BottomTab.BILLS,
                icon = Icons.Outlined.ReceiptLong,
                selectedIcon = Icons.Filled.ReceiptLong,
                onClick = { onTabSelected(BottomTab.BILLS) }
            )
            BottomTabItem(
                tab = BottomTab.PARTIES,
                isSelected = currentTab == BottomTab.PARTIES,
                icon = Icons.Outlined.People,
                selectedIcon = Icons.Filled.People,
                onClick = { onTabSelected(BottomTab.PARTIES) }
            )
            BottomTabItem(
                tab = BottomTab.MORE,
                isSelected = currentTab == BottomTab.MORE,
                icon = Icons.Outlined.Menu,
                selectedIcon = Icons.Filled.Menu,
                onClick = { onTabSelected(BottomTab.MORE) }
            )
        }
    }
}

@Composable
private fun BottomTabItem(
    tab: BottomTab,
    isSelected: Boolean,
    icon: ImageVector,
    selectedIcon: ImageVector,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Icon(
            imageVector = if (isSelected) selectedIcon else icon,
            contentDescription = tab.title,
            tint = if (isSelected) Color.White else Color.White.copy(alpha = 0.6f),
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = tab.title,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    changeText: String,
    isPositive: Boolean,
    icon: ImageVector,
    iconBgColor: Color = PrimaryBlue.copy(alpha = 0.1f),
    iconColor: Color = PrimaryBlue,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(iconBgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = iconColor,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = title,
                        fontSize = 11.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(2.dp))

            if (changeText.isNotEmpty()) {
                Text(
                    text = changeText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isPositive) GreenSuccess else RedDanger
                )
            }
        }
    }
}
