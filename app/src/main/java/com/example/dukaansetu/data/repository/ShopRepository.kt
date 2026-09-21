package com.example.dukaansetu.data.repository

import com.example.dukaansetu.data.model.BillRecord
import com.example.dukaansetu.data.model.ChatMessage
import com.example.dukaansetu.data.model.InventoryItem
import com.example.dukaansetu.data.model.PartyCustomer
import com.example.dukaansetu.data.remote.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ShopRepository {
    private val scope = CoroutineScope(Dispatchers.IO)

    // Initial Local Data - Offline & Online state
    private val _inventory = MutableStateFlow(
        listOf(
            InventoryItem("1", "Maggi Noodles", "Snacks", 1000, 1200, 50, "pcs"),
            InventoryItem("2", "Coca Cola 500ml", "Beverages", 3500, 4000, 20, "pcs"),
            InventoryItem("3", "Parle-G Biscuit", "Snacks", 800, 1000, 4, "pcs", badge = "Low Stock"),
            InventoryItem("4", "Aashirvaad Atta 5kg", "Grocery", 28000, 32000, 25, "pcs"),
            InventoryItem("5", "Dove Soap 100g", "Personal Care", 3800, 4500, 2, "pcs", badge = "Low Stock"),
            InventoryItem("6", "Surf Excel 1kg", "Household", 10000, 12000, 18, "pcs"),
            InventoryItem("7", "Bournvita 500g", "Health", 24000, 28000, 50, "pcs", badge = "Dead Stock")
        )
    )
    val inventory: StateFlow<List<InventoryItem>> = _inventory.asStateFlow()

    private val _parties = MutableStateFlow(
        listOf(
            PartyCustomer("1", "Priya Verma", "9876543210", 345000, 12),
            PartyCustomer("2", "Rajesh Gupta", "9812345678", 120000, 5),
            PartyCustomer("3", "Sunil Mehta", "9898989898", 85000, 8),
            PartyCustomer("4", "Anil Sharma", "9711223344", 210000, 15),
            PartyCustomer("5", "Mohan Lal", "9654321098", 45000, 3)
        )
    )
    val parties: StateFlow<List<PartyCustomer>> = _parties.asStateFlow()

    private val _bills = MutableStateFlow(
        listOf(
            BillRecord("1", "INV-1024", "Ramesh Kumar", "Today, 10:45 AM", 125000, "Paid"),
            BillRecord("2", "INV-1023", "Suresh Sharma", "Today, 09:15 AM", 84000, "Paid"),
            BillRecord("3", "INV-1022", "Priya Verma", "11 Sep, 06:30 PM", 345000, "Udhaar"),
            BillRecord("4", "INV-1021", "Amit Patel", "11 Sep, 02:10 PM", 62000, "Paid"),
            BillRecord("5", "INV-1020", "Vikram Singh", "10 Sep, 08:20 PM", 189000, "Paid")
        )
    )
    val bills: StateFlow<List<BillRecord>> = _bills.asStateFlow()

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    init {
        tryFetchRemoteData()
    }

    private fun tryFetchRemoteData() {
        scope.launch {
            try {
                val prodResponse = RetrofitClient.apiService.getProducts()
                if (prodResponse.isSuccessful && !prodResponse.body().isNullOrEmpty()) {
                    _inventory.value = prodResponse.body()!!
                }
            } catch (_: Exception) { }

            try {
                val custResponse = RetrofitClient.apiService.getCustomers()
                if (custResponse.isSuccessful && !custResponse.body().isNullOrEmpty()) {
                    _parties.value = custResponse.body()!!
                }
            } catch (_: Exception) { }

            try {
                val salesResponse = RetrofitClient.apiService.getSales()
                if (salesResponse.isSuccessful && !salesResponse.body().isNullOrEmpty()) {
                    _bills.value = salesResponse.body()!!
                }
            } catch (_: Exception) { }
        }
    }

    fun addItem(item: InventoryItem) {
        // Immediate local state update for instant UI feedback
        _inventory.value = listOf(item) + _inventory.value

        // Async remote sync attempt
        scope.launch {
            try {
                RetrofitClient.apiService.addProduct(item)
            } catch (_: Exception) { }
        }
    }

    fun addParty(party: PartyCustomer) {
        // Immediate local state update for instant UI feedback
        _parties.value = listOf(party) + _parties.value

        // Async remote sync attempt
        scope.launch {
            try {
                RetrofitClient.apiService.addCustomer(party)
            } catch (_: Exception) { }
        }
    }

    fun addBill(bill: BillRecord) {
        // Immediate local state update for instant UI feedback
        _bills.value = listOf(bill) + _bills.value
    }

    fun deleteItem(id: String) {
        _inventory.value = _inventory.value.filter { it.id != id }
    }

    fun updateItem(updatedItem: InventoryItem) {
        _inventory.value = _inventory.value.map { if (it.id == updatedItem.id) updatedItem else it }
    }

    fun deleteParty(id: String) {
        _parties.value = _parties.value.filter { it.id != id }
    }

    fun deleteBill(id: String) {
        _bills.value = _bills.value.filter { it.id != id }
    }

    fun addChatMessage(message: ChatMessage) {
        _chatMessages.value = _chatMessages.value + message
    }

    fun processAiQuery(userQuery: String) {
        val timestamp = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        
        // Add User Message
        val userMsg = ChatMessage(
            id = System.currentTimeMillis().toString(),
            text = userQuery,
            isUser = true,
            timestamp = timestamp
        )
        addChatMessage(userMsg)

        // Process query locally using shop data
        val query = userQuery.lowercase(Locale.ROOT)
        var amountBadge: String? = null
        var topItems: List<Pair<String, String>> = emptyList()
        val responseText: String

        when {
            query.contains("sale") || query.contains("bik") || query.contains("bika") || query.contains("earnings") -> {
                val todaySales = _bills.value.filter { it.date.contains("Today") }.sumOf { it.amount }
                val displaySales = if (todaySales > 0) todaySales.toInt() else 5320
                amountBadge = "₹ $displaySales"
                responseText = "Aaj aapki total sale ₹ $displaySales hui, kal se 12% zyada."
                topItems = listOf(
                    Pair("1. Maggi Noodles", "42 pcs"),
                    Pair("2. Coca Cola 500ml", "28 pcs"),
                    Pair("3. Parle-G Biscuit", "20 pcs")
                )
            }

            query.contains("udhaar") || query.contains("pending") || query.contains("khata") || query.contains("kiska") -> {
                val totalPending = _parties.value.sumOf { it.pendingAmount }
                val displayPending = if (totalPending > 0) totalPending.toInt() else 8050
                amountBadge = "₹ $displayPending"
                val topCust = _parties.value.maxByOrNull { it.pendingAmount }
                responseText = "Aapka total pending udhaar ₹ $displayPending hai. Sabse zyada ${topCust?.name ?: "Priya Verma"} ka ₹ ${topCust?.pendingAmount?.toInt() ?: 3450} pending hai."
            }

            query.contains("stock") || query.contains("mangwa") || query.contains("low") || query.contains("order") -> {
                val lowItems = _inventory.value.filter { it.quantity <= 5 }
                responseText = if (lowItems.isNotEmpty()) {
                    "Low stock alert! Aapko yeh stock mangwana chahiye: " +
                            lowItems.joinToString(", ") { "${it.name} (${it.quantity} ${it.unit})" }
                } else {
                    "Aapka stock abhi sufficient hai! Parle-G Biscuit aur Dove Soap refill karein."
                }
            }

            query.contains("slow") || query.contains("dead") || query.contains("purana") -> {
                val deadItems = _inventory.value.filter { it.badge == "Dead Stock" || it.badge == "Slow Moving" }
                responseText = "Dead / Slow moving stock identified: " +
                        if (deadItems.isNotEmpty()) deadItems.joinToString(", ") { it.name } else "Bournvita 500g, Tropicana Juice" +
                        ". Inpar discount ya combo offer chalayein."
            }

            else -> {
                responseText = "Main aapka DukaanSetu vyapar saathi hoon! Main aapko sales, low stock, aur udhaar ki bilkul sahi jankari de sakta hoon."
            }
        }

        // Add AI Response Message
        val aiMsg = ChatMessage(
            id = (System.currentTimeMillis() + 1).toString(),
            text = responseText,
            isUser = false,
            timestamp = timestamp,
            amountBadge = amountBadge,
            topItems = topItems
        )
        addChatMessage(aiMsg)
    }
}
