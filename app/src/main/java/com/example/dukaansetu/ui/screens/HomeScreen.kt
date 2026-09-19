package com.example.dukaansetu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.dukaansetu.ui.components.DukaanSetuLogo
import com.example.dukaansetu.ui.components.StatCard
import com.example.dukaansetu.ui.theme.*
import com.example.dukaansetu.ui.viewmodel.ShopViewModel

@Composable
fun HomeScreen(
    viewModel: ShopViewModel,
    onNavigateToAi: () -> Unit,
    onNavigateToItems: () -> Unit,
    onNavigateToReports: () -> Unit,
    onNavigateToDeadStock: () -> Unit,
    onNavigateToExpiry: () -> Unit,
    onNavigateToAddItem: () -> Unit,
    onNavigateToParties: () -> Unit,
    onNavigateToBills: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToMore: () -> Unit
) {
    val inventory by viewModel.inventory.collectAsState()
    val parties by viewModel.parties.collectAsState()
    val bills by viewModel.bills.collectAsState()
    
    val todaySales = bills.filter { it.date.contains("Today") }.sumOf { it.amount }
    val totalPending = parties.sumOf { it.pendingAmount }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgLight)
            .verticalScroll(rememberScrollState())
    ) {
        // Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(NavyPrimary)
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(PrimaryBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "HB",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Good Morning, Harshit ",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = "👋", fontSize = 16.sp)
                        }
                        Text(
                            text = "Aaj ka din, behtar vyapar ke naam",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    DukaanSetuLogo(size = 38)

                    Spacer(modifier = Modifier.width(10.dp))

                    IconButton(onClick = onNavigateToNotifications) {
                        BadgedBox(
                            badge = { Badge { Text("") } }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = "Notifications",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Date Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp),
                    shadowElevation = 1.dp
                ) {
                    Text(
                        text = "Fri, 12 Sep 2025",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextSecondary,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Stat Cards Grid
            Row(modifier = Modifier.fillMaxWidth()) {
                StatCard(
                    title = "Today's Sales",
                    value = "₹ ${todaySales.toInt()}",
                    changeText = "↑ 12%",
                    isPositive = true,
                    icon = Icons.Default.TrendingUp,
                    iconBgColor = GreenLight,
                    iconColor = GreenSuccess,
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToReports
                )
                Spacer(modifier = Modifier.width(12.dp))
                StatCard(
                    title = "Purchases",
                    value = "₹ 2,480",
                    changeText = "↓ 8%",
                    isPositive = false,
                    icon = Icons.Default.ShoppingBag,
                    iconBgColor = RedLight,
                    iconColor = RedDanger,
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToBills
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                StatCard(
                    title = "Total Profit",
                    value = "₹ 12,540",
                    changeText = "↑ 15%",
                    isPositive = true,
                    icon = Icons.Default.AccountBalanceWallet,
                    iconBgColor = GreenLight,
                    iconColor = GreenSuccess,
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToReports
                )
                Spacer(modifier = Modifier.width(12.dp))
                StatCard(
                    title = "Pending Udhaar",
                    value = "₹ ${totalPending.toInt()}",
                    changeText = "${parties.filter { it.pendingAmount > 0 }.size} customers",
                    isPositive = false,
                    icon = Icons.Default.Person,
                    iconBgColor = OrangeLight,
                    iconColor = OrangeWarning,
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToParties
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ask My Shop AI Banner
            Card(
                colors = CardDefaults.cardColors(containerColor = NavyPrimary),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToAi() }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(PrimaryBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = "AI",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "Ask My Shop AI",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Apne business ka jawab ab awaaz se",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        AiPromptChip("-> Aaj kitni sale hui?", onClick = onNavigateToAi)
                        AiPromptChip("-> Kiska udhaar pending hai?", onClick = onNavigateToAi)
                        AiPromptChip("-> Kaunsa stock mangwana chahiye?", onClick = onNavigateToAi)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Quick Actions Grid
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Quick Actions",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        QuickActionButton(
                            title = "Scan Receipt\n(OCR)",
                            icon = Icons.Outlined.CenterFocusWeak,
                            onClick = onNavigateToAddItem
                        )
                        QuickActionButton(
                            title = "Voice Count",
                            icon = Icons.Outlined.Mic,
                            onClick = onNavigateToAi
                        )
                        QuickActionButton(
                            title = "Items",
                            icon = Icons.Outlined.Inventory2,
                            onClick = onNavigateToItems
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        QuickActionButton(
                            title = "Udhaar Khata",
                            icon = Icons.Outlined.AccountBalance,
                            onClick = onNavigateToParties
                        )
                        QuickActionButton(
                            title = "Reports",
                            icon = Icons.Outlined.BarChart,
                            onClick = onNavigateToReports
                        )
                        QuickActionButton(
                            title = "Dead Stock",
                            icon = Icons.Outlined.Warning,
                            onClick = onNavigateToDeadStock
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        QuickActionButton(
                            title = "Expiry Log",
                            icon = Icons.Outlined.Timer,
                            onClick = onNavigateToExpiry
                        )
                        QuickActionButton(
                            title = "Add Item",
                            icon = Icons.Outlined.AddCircle,
                            onClick = onNavigateToAddItem
                        )
                        QuickActionButton(
                            title = "More",
                            icon = Icons.Outlined.GridView,
                            onClick = onNavigateToMore
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun AiPromptChip(
    text: String,
    onClick: () -> Unit
) {
    Surface(
        color = Color.White.copy(alpha = 0.12f),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
private fun QuickActionButton(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(8.dp)
            .width(80.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(BgLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = NavyPrimary,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = TextPrimary,
            modifier = Modifier.wrapContentWidth()
        )
    }
}