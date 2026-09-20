package com.example.dukaansetu.data.remote

import com.example.dukaansetu.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface DukaanSetuApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: Map<String, String>): Response<AuthResponse>

    @POST("api/auth/register")
    suspend fun register(@Body request: Map<String, String>): Response<AuthResponse>

    @GET("api/shop")
    suspend fun getShop(): Response<ShopDto>

    @GET("api/products")
    suspend fun getProducts(): Response<List<InventoryItem>>

    @POST("api/products")
    suspend fun addProduct(@Body item: InventoryItem): Response<InventoryItem>

    @GET("api/customers")
    suspend fun getCustomers(): Response<List<PartyCustomer>>

    @POST("api/customers")
    suspend fun addCustomer(@Body customer: PartyCustomer): Response<PartyCustomer>

    @GET("api/sales")
    suspend fun getSales(): Response<List<BillRecord>>

    @POST("api/sales")
    suspend fun createSale(@Body request: SaleRequest): Response<BillRecord>

    @POST("api/ai/query")
    suspend fun queryAi(@Body request: Map<String, String>): Response<Map<String, String>>
}
