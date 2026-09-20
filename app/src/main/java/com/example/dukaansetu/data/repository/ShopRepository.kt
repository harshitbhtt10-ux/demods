package com.example.dukaansetu.data.repository

import com.example.dukaansetu.data.model.BillRecord
import com.example.dukaansetu.data.model.ChatMessage
import com.example.dukaansetu.data.model.InventoryItem
import com.example.dukaansetu.data.model.PartyCustomer
import com.example.dukaansetu.data.model.SaleItemDto
import com.example.dukaansetu.data.model.SaleRequest
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
    private val api = RetrofitClient.apiService
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _inventory = MutableStateFlow<List<InventoryItem>>(emptyList())
    val inventory: StateFlow<List<InventoryItem>> = _inventory.asStateFlow()

    private val _parties = MutableStateFlow<List<PartyCustomer>>(emptyList())
    val parties: StateFlow<List<PartyCustomer>> = _parties.asStateFlow()

    private val _bills = MutableStateFlow<List<BillRecord>>(emptyList())
    val bills: StateFlow<List<BillRecord>> = _bills.asStateFlow()

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    init {
        refreshData()
    }

    fun refreshData() {
        scope.launch {
            try {
                val prodResponse = api.getProducts()
                if (prodResponse.isSuccessful && prodResponse.body() != null) {
                    _inventory.value = prodResponse.body()!!
                }

                val custResponse = api.getCustomers()
                if (custResponse.isSuccessful && custResponse.body() != null) {
                    _parties.value = custResponse.body()!!
                }

                val salesResponse = api.getSales()
                if (salesResponse.isSuccessful && salesResponse.body() != null) {
                    _bills.value = salesResponse.body()!!
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addItem(item: InventoryItem) {
        scope.launch {
            try {
                val response = api.addProduct(item)
                if (response.isSuccessful) {
                    refreshData()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addParty(party: PartyCustomer) {
        scope.launch {
            try {
                val response = api.addCustomer(party)
                if (response.isSuccessful) {
                    refreshData()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addBill(bill: BillRecord, selectedItem: InventoryItem? = null) {
        scope.launch {
            try {
                val productId = selectedItem?.id ?: "600000000000000000000001" // Fallback placeholder only if quick bill without item selection
                val saleReq = SaleRequest(
                    invoiceNumber = bill.billNo,
                    items = listOf(SaleItemDto(productId, 1, bill.amountMinor, bill.amountMinor)),
                    subtotalMinor = bill.amountMinor,
                    totalMinor = bill.amountMinor,
                    paidMinor = if (bill.status == "Paid") bill.amountMinor else 0,
                    creditMinor = if (bill.status == "Udhaar") bill.amountMinor else 0,
                    paymentMethod = if (bill.status == "Paid") "cash" else "credit"
                )
                val response = api.createSale(saleReq)
                if (response.isSuccessful) {
                    refreshData()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteItem(id: String) {
        _inventory.value = _inventory.value.filter { it.id != id }
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
        scope.launch {
            val timestamp = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
            val userMsg = ChatMessage(
                id = System.currentTimeMillis().toString(),
                text = "$userQuery 🎙️",
                isUser = true,
                timestamp = timestamp
            )
            addChatMessage(userMsg)

            try {
                val response = api.queryAi(mapOf("query" to userQuery))
                val replyBody = response.body()
                val replyText = if (response.isSuccessful && replyBody != null) {
                    replyBody["reply"] ?: "Aapke sawal ka jawab mil gaya."
                } else {
                    "Main aapke vyapar saathi hoon! Sabhi system online hain."
                }

                val aiMsg = ChatMessage(
                    id = (System.currentTimeMillis() + 1).toString(),
                    text = replyText,
                    isUser = false,
                    timestamp = timestamp
                )
                addChatMessage(aiMsg)
            } catch (e: Exception) {
                val errMsg = ChatMessage(
                    id = (System.currentTimeMillis() + 1).toString(),
                    text = "Server connection error. Kripya backend server check karein.",
                    isUser = false,
                    timestamp = timestamp
                )
                addChatMessage(errMsg)
            }
        }
    }
}
