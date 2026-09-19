package com.example.dukaansetu.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.dukaansetu.data.model.BillRecord
import com.example.dukaansetu.data.model.InventoryItem
import com.example.dukaansetu.data.model.PartyCustomer
import com.example.dukaansetu.data.repository.ShopRepository
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ShopViewModel : ViewModel() {
    private val repository = ShopRepository()

    val inventory: StateFlow<List<InventoryItem>> = repository.inventory
    val parties: StateFlow<List<PartyCustomer>> = repository.parties
    val bills: StateFlow<List<BillRecord>> = repository.bills
    val chatMessages = repository.chatMessages

    fun addItem(
        name: String,
        category: String,
        purchasePrice: Double,
        sellingPrice: Double,
        quantity: Int,
        unit: String,
        expiryDate: String
    ) {
        val newItem = InventoryItem(
            id = System.currentTimeMillis().toString(),
            name = name,
            category = category,
            purchasePrice = purchasePrice,
            sellingPrice = sellingPrice,
            quantity = quantity,
            unit = unit,
            expiryDate = expiryDate,
            badge = if (quantity <= 5) "Low Stock" else null
        )
        repository.addItem(newItem)
    }

    fun addParty(name: String, phone: String, pendingAmount: Double, address: String, type: String) {
        val newParty = PartyCustomer(
            id = System.currentTimeMillis().toString(),
            name = name,
            phone = phone,
            pendingAmount = pendingAmount,
            daysPending = if (pendingAmount > 0) 1 else 0,
            address = address,
            type = type
        )
        repository.addParty(newParty)
    }

    fun deleteItem(id: String) {
        repository.deleteItem(id)
    }

    fun sendAiQuery(query: String) {
        if (query.isNotBlank()) {
            repository.processAiQuery(query)
        }
    }
}
