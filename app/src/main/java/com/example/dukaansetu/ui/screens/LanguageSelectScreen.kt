package com.example.dukaansetu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dukaansetu.ui.components.DukaanSetuTopBar
import com.example.dukaansetu.ui.theme.*

data class AppLanguage(val code: String, val name: String, val nativeName: String)

@Composable
fun LanguageSelectScreen(
    onBackClick: () -> Unit
) {
    var selectedLang by remember { mutableStateOf("en") }

    val languages = remember {
        listOf(
            AppLanguage("en", "English", "English (India)"),
            AppLanguage("hi", "Hindi", "हिन्दी (Hindi)"),
            AppLanguage("hin", "Hinglish", "Hinglish (Hindi + English)"),
            AppLanguage("pa", "Punjabi", "ਪੰਜਾਬੀ (Punjabi)"),
            AppLanguage("gu", "Gujarati", "ગુજરાતી (Gujarati)"),
            AppLanguage("mr", "Marathi", "मराठी (Marathi)"),
            AppLanguage("kn", "Kannada", "ಕನ್ನಡ (Kannada)"),
            AppLanguage("ml", "Malayalam", "മലയാളം (Malayalam)"),
            AppLanguage("ta", "Tamil", "தமிழ் (Tamil)"),
            AppLanguage("te", "Telugu", "తెలుగు (Telugu)"),
            AppLanguage("bn", "Bengali", "বাংলা (Bengali)"),
            AppLanguage("or", "Odia", "ଓଡ଼ିଆ (Odia)"),
            AppLanguage("as", "Assamese", "অসমীয়া (Assamese)"),
            AppLanguage("bho", "Bhojpuri", "भोजपुरी (Bhojpuri)"),
            AppLanguage("ur", "Urdu", "اردو (Urdu)"),
            AppLanguage("ar", "Arabic", "العربية (Arabic - Trade)")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        DukaanSetuTopBar(
            title = "Select Language",
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(languages) { lang ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedLang = lang.code }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(text = lang.name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                                Text(text = lang.nativeName, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }

                            RadioButton(
                                selected = selectedLang == lang.code,
                                onClick = { selectedLang = lang.code },
                                colors = RadioButtonDefaults.colors(selectedColor = PrimaryBlue)
                            )
                        }
                    }
                }
            }
        }

        Surface(
            color = Color.White,
            shadowElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = onBackClick,
                    colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = "Apply Language",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
