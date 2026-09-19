package com.example.dukaansetu.data.repository

import com.example.dukaansetu.data.model.BillRecord
import com.example.dukaansetu.data.model.ChatMessage
import com.example.dukaansetu.data.model.InventoryItem
import com.example.dukaansetu.data.model.PartyCustomer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ShopRepository {

    private val _inventory = MutableStateFlow(
        listOf(
            InventoryItem("1", "Maggi Noodles", "Snacks", 10.0, 12.0, 50, "pcs"),
            InventoryItem("2", "Coca Cola 500ml", "Beverages", 35.0, 40.0, 20, "pcs"),
            InventoryItem("3", "Parle-G Biscuit", "Snacks", 8.0, 10.0, 4, "pcs", badge = "Low Stock"),
            InventoryItem("4", "Aashirvaad Atta 5kg", "Grocery", 280.0, 320.0, 25, "pcs"),
            InventoryItem("5", "Dove Soap 100g", "Personal Care", 38.0, 45.0, 2, "pcs", badge = "Low Stock"),
            InventoryItem("6", "Surf Excel 1kg", "Household", 100.0, 120.0, 18, "pcs"),
            InventoryItem("7", "Bournvita 500g", "Health", 240.0, 280.0, 50, "pcs", badge = "Dead Stock")
        )
    )
    val inventory: StateFlow<List<InventoryItem>> = _inventory.asStateFlow()

    private val _parties = MutableStateFlow(
        listOf(
            PartyCustomer("1", "Priya Verma", "9876543210", 3450.0, 12),
            PartyCustomer("2", "Rajesh Gupta", "9812345678", 1200.0, 5),
            PartyCustomer("3", "Sunil Mehta", "9898989898", 850.0, 8),
            PartyCustomer("4", "Anil Sharma", "9711223344", 2100.0, 15),
            PartyCustomer("5", "Mohan Lal", "9654321098", 450.0, 3)
        )
    )
    val parties: StateFlow<List<PartyCustomer>> = _parties.asStateFlow()

    private val _bills = MutableStateFlow(
        listOf(
            BillRecord("1", "INV-1024", "Ramesh Kumar", "Today, 10:45 AM", 1250.0, "Paid"),
            BillRecord("2", "INV-1023", "Suresh Sharma", "Today, 09:15 AM", 840.0, "Paid"),
            BillRecord("3", "INV-1022", "Priya Verma", "11 Sep, 06:30 PM", 3450.0, "Udhaar"),
            BillRecord("4", "INV-1021", "Amit Patel", "11 Sep, 02:10 PM", 620.0, "Paid"),
            BillRecord("5", "INV-1020", "Vikram Singh", "10 Sep, 08:20 PM", 1890.0, "Paid")
        )
    )
    val bills: StateFlow<List<BillRecord>> = _bills.asStateFlow()

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    fun addItem(item: InventoryItem) {
        _inventory.value = listOf(item) + _inventory.value
    }

    fun addParty(party: PartyCustomer) {
        _parties.value = listOf(party) + _parties.value
    }

    fun addBill(bill: BillRecord) {
        _bills.value = listOf(bill) + _bills.value
    }

    fun addChatMessage(message: ChatMessage) {
        _chatMessages.value = _chatMessages.value + message
    }

    fun processAiQuery(userQuery: String): ChatMessage {
        val query = userQuery.lowercase(Locale.ROOT)
        val timestamp = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

        // User query message
        val userMsg = ChatMessage(
            id = System.currentTimeMillis().toString(),
            text = "$userQuery 🎙️",
            isUser = true,
            timestamp = timestamp
        )
        addChatMessage(userMsg)

        // AI Response Logic
        val responseText: String
        var amountBadge: String? = null
        var topItems: List<Pair<String, String>> = emptyList()

        when {
            query.contains("sale") || query.contains("bik") || query.contains("sales") -> {
                val todaySales = _bills.value.filter { it.date.contains("Today") }.sumOf { it.amount }
                amountBadge = "₹ ${todaySales.toInt()}"
                responseText = "Aaj aapki total sale ₹ ${todaySales.toInt()} hui, kal se 12% zyada."
                topItems = listOf(
                    Pair("1. Maggi Noodles", "42 pcs"),
                    Pair("2. Coca Cola 500ml", "28 pcs"),
                    Pair("3. Parle-G Biscuit", "20 pcs")
                )
            }

            query.contains("udhaar") || query.contains("pending") || query.contains("khata") -> {
                val totalPending = _parties.value.sumOf { it.pendingAmount }
                amountBadge = "₹ ${totalPending.toInt()}"
                val topCustomer = _parties.value.maxByOrNull { it.pendingAmount }
                responseText = "Aapka total pending udhaar ₹ ${totalPending.toInt()} hai. Sabse zyada ${topCustomer?.name ?: "Priya Verma"} ka ₹ ${topCustomer?.pendingAmount?.toInt() ?: 3450} pending hai."
            }

            query.contains("stock") || query.contains("mangwa") || query.contains("low") -> {
                val lowStockItems = _inventory.value.filter { it.quantity <= 5 }
                responseText = "Aapke paas ${lowStockItems.size} items low stock hain: " +
                        lowStockItems.joinToString(", ") { "${it.name} (${it.quantity} ${it.unit})" } + ". Kripya inhe jald order karein."
            }

            query.contains("slow") || query.contains("dead") || query.contains("purana") -> {
                val deadStockItems = _inventory.value.filter { it.badge == "Dead Stock" || it.badge == "Slow Moving" }
                responseText = "Dead / Slow moving items identified: " +
                        deadStockItems.joinToString(", ") { it.name } + ". Inpar discount ya combo offer chalayein."
            }

            else -> {
                responseText = "Main aapka vyapar saathi hoon! Main aapke DukaanSetu store ki sales, stock, aur udhaar ki saari jankari bata sakta hoon."
            }
        }

        val aiMsg = ChatMessage(
            id = (System.currentTimeMillis() + 1).toString(),
            text = responseText,
            isUser = false,
            timestamp = timestamp,
            amountBadge = amountBadge,
            topItems = topItems
        )
        addChatMessage(aiMsg)
        return aiMsg
    }
}
