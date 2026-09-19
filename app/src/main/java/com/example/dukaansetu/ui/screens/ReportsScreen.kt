package com.example.dukaansetu.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dukaansetu.ui.components.DukaanSetuTopBar
import com.example.dukaansetu.ui.components.StatCard
import com.example.dukaansetu.ui.theme.*

@Composable
fun ReportsScreen(
    onBackClick: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf("7D") }
    val filters = listOf("7D", "30D", "3M", "1Y", "Custom")

    // Dynamic data based on selected filter
    val (sales, profit, deadStock, expiry) = when (selectedFilter) {
        "30D" -> listOf("₹ 2,15,400", "₹ 48,200", "₹ 7,100", "₹ 1,200")
        "3M"  -> listOf("₹ 6,45,000", "₹ 1,32,000", "₹ 12,000", "₹ 800")
        "1Y"  -> listOf("₹ 24,10,000", "₹ 5,10,000", "₹ 15,500", "₹ 2,100")
        "Custom" -> listOf("₹ 0", "₹ 0", "₹ 0", "₹ 0")
        else -> listOf("₹ 52,430", "₹ 12,540", "₹ 8,230", "₹ 2,450") // 7D default
    }

    val salesChange = when (selectedFilter) {
        "30D" -> "↑ 5%"
        "3M"  -> "↓ 2%"
        "1Y"  -> "↑ 12%"
        "Custom" -> "-"
        else -> "↑ 18%"
    }
    
    val profitChange = when (selectedFilter) {
        "30D" -> "↑ 8%"
        "3M"  -> "↑ 1%"
        "1Y"  -> "↑ 15%"
        "Custom" -> "-"
        else -> "↑ 22%"
    }
    
    val salesPos = when (selectedFilter) {
        "3M" -> false
        else -> true
    }
    
    val profitPos = true

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        DukaanSetuTopBar(
            title = "Reports",
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Time Range Filters
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filters) { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        label = {
                            Text(
                                text = filter,
                                fontSize = 12.sp,
                                fontWeight = if (selectedFilter == filter) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = NavyPrimary,
                            selectedLabelColor = MaterialTheme.colorScheme.surface,
                            containerColor = MaterialTheme.colorScheme.surface,
                            labelColor = TextSecondary
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stat Cards Grid
            Row(modifier = Modifier.fillMaxWidth()) {
                StatCard(
                    title = "Total Sales",
                    value = sales,
                    changeText = salesChange,
                    isPositive = salesPos,
                    icon = Icons.Default.TrendingUp,
                    iconBgColor = GreenLight,
                    iconColor = GreenSuccess,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                StatCard(
                    title = "Total Profit",
                    value = profit,
                    changeText = profitChange,
                    isPositive = profitPos,
                    icon = Icons.Default.AccountBalanceWallet,
                    iconBgColor = GreenLight,
                    iconColor = GreenSuccess,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                StatCard(
                    title = "Dead Stock Value",
                    value = deadStock,
                    changeText = if (selectedFilter == "7D") "↓ 12%" else "",
                    isPositive = false,
                    icon = Icons.Default.DeleteSweep,
                    iconBgColor = RedLight,
                    iconColor = RedDanger,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                StatCard(
                    title = "Expiring Soon Value",
                    value = expiry,
                    changeText = if (selectedFilter == "7D") "5 items" else "",
                    isPositive = false,
                    icon = Icons.Default.Timer,
                    iconBgColor = OrangeLight,
                    iconColor = OrangeWarning,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Sales Trend Chart Card
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Sales Trend",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Surface(
                            color = NavyPrimary,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "₹ 5,320",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Line Graph Canvas
                    val graphLineColor = PrimaryBlue
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                    ) {
                        val width = size.width
                        val height = size.height

                        val points = listOf(
                            Offset(0f, height * 0.7f),
                            Offset(width * 0.16f, height * 0.5f),
                            Offset(width * 0.33f, height * 0.6f),
                            Offset(width * 0.5f, height * 0.3f),
                            Offset(width * 0.66f, height * 0.55f),
                            Offset(width * 0.83f, height * 0.2f), // Peak
                            Offset(width, height * 0.4f)
                        )

                        val path = Path().apply {
                            moveTo(points[0].x, points[0].y)
                            for (i in 1 until points.size) {
                                lineTo(points[i].x, points[i].y)
                            }
                        }

                        drawPath(
                            path = path,
                            color = graphLineColor,
                            style = Stroke(width = 6f)
                        )

                        // Draw Peak Point Dot
                        drawCircle(
                            color = graphLineColor,
                            radius = 12f,
                            center = points[5]
                        )
                        drawCircle(
                            color = Color.White,
                            radius = 6f,
                            center = points[5]
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    val dates = listOf("6 Sep", "7 Sep", "8 Sep", "9 Sep", "10 Sep", "11 Sep", "12 Sep")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        dates.forEach { date ->
                            Text(
                                text = date,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Category Wise Sales Card
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Category Wise Sales",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CategoryProgressRow("Beverages", 0.28f, "28%")
                    CategoryProgressRow("Snacks", 0.24f, "24%")
                    CategoryProgressRow("Dairy", 0.18f, "18%")
                    CategoryProgressRow("Personal Care", 0.12f, "12%")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun CategoryProgressRow(
    category: String,
    progress: Float,
    percentage: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = category, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground)
            Text(text = percentage, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        Spacer(modifier = Modifier.height(6.dp))

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = PrimaryBlue,
            trackColor = MaterialTheme.colorScheme.background,
            drawStopIndicator = {}
        )
    }
}
