package com.example.dukaansetu.data.model

import com.google.gson.annotations.SerializedName

data class InventoryItem(
    @SerializedName("_id") val id: String,
    val name: String,
    val category: String,
    @SerializedName("costPriceMinor") val purchasePriceMinor: Int, // in paise
    @SerializedName("sellingPriceMinor") val sellingPriceMinor: Int, // in paise
    @SerializedName("stockQuantity") val quantity: Int,
    val unit: String = "pcs",
    val expiryDate: String = "",
    val badge: String? = null
) {
    val purchasePrice: Double get() = purchasePriceMinor / 100.0
    val sellingPrice: Double get() = sellingPriceMinor / 100.0
}

data class PartyCustomer(
    @SerializedName("_id") val id: String,
    val name: String,
    val phone: String,
    @SerializedName("openingBalanceMinor") val pendingAmountMinor: Int, // in paise
    val daysPending: Int = 0,
    val address: String = "",
    val type: String = "Customer"
) {
    val pendingAmount: Double get() = pendingAmountMinor / 100.0
}

data class BillRecord(
    @SerializedName("_id") val id: String,
    @SerializedName("invoiceNumber") val billNo: String,
    @SerializedName("customerName") val customerName: String = "Walk-in Customer",
    @SerializedName("saleDate") val date: String,
    @SerializedName("totalMinor") val amountMinor: Int, // in paise
    val status: String = "Paid"
) {
    val amount: Double get() = amountMinor / 100.0
}

data class ChatMessage(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: String,
    val amountBadge: String? = null,
    val topItems: List<Pair<String, String>> = emptyList()
)

data class AuthResponse(
    val token: String,
    val user: UserDto,
    val shop: ShopDto?
)

data class UserDto(
    @SerializedName("_id") val id: String,
    val name: String,
    val email: String,
    val role: String
)

data class ShopDto(
    @SerializedName("_id") val id: String,
    val name: String,
    val slug: String,
    val currency: String
)

data class SaleRequest(
    val invoiceNumber: String,
    val items: List<SaleItemDto>,
    val subtotalMinor: Int,
    val totalMinor: Int,
    val paidMinor: Int,
    val creditMinor: Int,
    val paymentMethod: String
)

data class SaleItemDto(
    @SerializedName("productId") val productId: String,
    val quantity: Int,
    val unitPriceMinor: Int,
    val lineTotalMinor: Int
)
