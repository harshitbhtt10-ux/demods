package com.example.dukaansetu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dukaansetu.ui.components.DukaanSetuOfficialLogoHeader
import com.example.dukaansetu.ui.components.DukaanSetuTopBar
import com.example.dukaansetu.ui.theme.*

@Composable
fun AboutAppScreen(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        DukaanSetuTopBar(
            title = "About DukkanSetu",
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            DukaanSetuOfficialLogoHeader(logoSize = 130, isDarkBg = false)

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                color = PrimaryBlue.copy(alpha = 0.1f),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "Version 1.0.0",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "DukaanSetu is an all-in-one smart business assistant for small retailers, shopkeepers, and local businesses in India. Manage inventory, voice sales counting, AI business assistant, billing, and Udhaar Khata seamlessly.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Made in India 🇮🇳",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
