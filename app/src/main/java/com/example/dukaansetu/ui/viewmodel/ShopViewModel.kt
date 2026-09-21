package com.example.dukaansetu.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.dukaansetu.data.model.BillRecord
import com.example.dukaansetu.data.model.InventoryItem
import com.example.dukaansetu.data.model.PartyCustomer
import com.example.dukaansetu.data.repository.ShopRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShopViewModel : ViewModel() {
    private val repository = ShopRepository()

    val inventory: StateFlow<List<InventoryItem>> = repository.inventory
    val parties: StateFlow<List<PartyCustomer>> = repository.parties
    val bills: StateFlow<List<BillRecord>> = repository.bills
    val chatMessages = repository.chatMessages

    private val _currentLanguage = MutableStateFlow("en")
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    fun setLanguage(code: String) {
        _currentLanguage.value = code
    }

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
            id = "",
            name = name,
            category = category,
            purchasePriceMinor = (purchasePrice * 100).toInt(),
            sellingPriceMinor = (sellingPrice * 100).toInt(),
            quantity = quantity,
            unit = unit,
            expiryDate = expiryDate,
            badge = if (quantity <= 5) "Low Stock" else null
        )
        repository.addItem(newItem)
    }

    fun addParty(name: String, phone: String, pendingAmount: Double, address: String, type: String) {
        val newParty = PartyCustomer(
            id = "",
            name = name,
            phone = phone,
            pendingAmountMinor = (pendingAmount * 100).toInt(),
            daysPending = if (pendingAmount > 0) 1 else 0,
            address = address,
            type = type
        )
        repository.addParty(newParty)
    }

    fun deleteItem(id: String) {
        repository.deleteItem(id)
    }

    fun updateItem(
        id: String,
        name: String,
        category: String,
        purchasePrice: Double,
        sellingPrice: Double,
        quantity: Int,
        unit: String
    ) {
        val updatedItem = InventoryItem(
            id = id,
            name = name,
            category = category,
            purchasePriceMinor = (purchasePrice * 100).toInt(),
            sellingPriceMinor = (sellingPrice * 100).toInt(),
            quantity = quantity,
            unit = unit,
            badge = if (quantity <= 5) "Low Stock" else null
        )
        repository.updateItem(updatedItem)
    }

    fun deleteParty(id: String) {
        repository.deleteParty(id)
    }

    fun deleteBill(id: String) {
        repository.deleteBill(id)
    }

    fun sendAiQuery(query: String) {
        if (query.isNotBlank()) {
            repository.processAiQuery(query)
        }
    }
}
