package com.example.dukaansetu.data.model

import androidx.compose.ui.graphics.Color

data class InventoryItem(
    val id: String,
    val name: String,
    val category: String,
    val purchasePrice: Double,
    val sellingPrice: Double,
    val quantity: Int,
    val unit: String = "pcs",
    val expiryDate: String = "",
    val badge: String? = null
)

data class PartyCustomer(
    val id: String,
    val name: String,
    val phone: String,
    val pendingAmount: Double,
    val daysPending: Int,
    val address: String = "",
    val type: String = "Customer"
)

data class BillRecord(
    val id: String,
    val billNo: String,
    val customerName: String,
    val date: String,
    val amount: Double,
    val status: String = "Paid"
)

data class ChatMessage(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: String,
    val amountBadge: String? = null,
    val topItems: List<Pair<String, String>> = emptyList()
)
