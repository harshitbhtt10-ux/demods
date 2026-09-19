package com.example.dukaansetu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dukaansetu.ui.components.DukaanSetuTopBar
import com.example.dukaansetu.ui.theme.*
import com.example.dukaansetu.ui.viewmodel.ShopViewModel

@Composable
fun AddPartyScreen(
    viewModel: ShopViewModel,
    onBackClick: () -> Unit,
    onPartyAdded: () -> Unit
) {
    var isCustomer by remember { mutableStateOf(true) }
    var partyName by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var openingUdhaar by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        DukaanSetuTopBar(
            title = "Add Party / Customer",
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Segmented Toggle: Customer / Supplier
            TabRow(
                selectedTabIndex = if (isCustomer) 0 else 1,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = NavyPrimary,
                modifier = Modifier.clip(RoundedCornerShape(12.dp))
            ) {
                Tab(
                    selected = isCustomer,
                    onClick = { isCustomer = true },
                    text = { Text("Customer (Grahak)", fontWeight = FontWeight.Bold, fontSize = 13.sp) }
                )
                Tab(
                    selected = !isCustomer,
                    onClick = { isCustomer = false },
                    text = { Text("Supplier (Vyapari)", fontWeight = FontWeight.Bold, fontSize = 13.sp) }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Form Fields
            OutlinedTextField(
                value = partyName,
                onValueChange = { partyName = it },
                label = { Text("Party / Customer Name *") },
                placeholder = { Text("Enter customer name") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = TextSecondary)
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = PrimaryBlue,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = mobileNumber,
                onValueChange = { mobileNumber = it },
                label = { Text("Mobile Number *") },
                placeholder = { Text("Enter 10-digit phone number") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = TextSecondary)
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = PrimaryBlue,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = openingUdhaar,
                onValueChange = { openingUdhaar = it },
                label = { Text("Opening Udhaar Balance (₹)") },
                placeholder = { Text("e.g. 1500 (Leave 0 if none)") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = PrimaryBlue,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address (Optional)") },
                placeholder = { Text("e.g. Near Main Market") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = PrimaryBlue,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                )
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Bottom Action Button
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
                    onClick = {
                        val parsedPendingAmount = openingUdhaar.toDoubleOrNull() ?: 0.0
                        viewModel.addParty(
                            name = partyName,
                            phone = mobileNumber,
                            pendingAmount = parsedPendingAmount,
                            address = address,
                            type = if (isCustomer) "Customer" else "Supplier"
                        )
                        onPartyAdded()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = "Save Party / Customer",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
