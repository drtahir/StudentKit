#!/bin/bash
sed -i '/fun clearCachedQuran()/a \
    // --- POS SYSTEM ---\
    val allPosProducts = dao.getAllPosProducts()\
    suspend fun insertPosProduct(product: PosProduct) = dao.insertPosProduct(product)\
    suspend fun deletePosProductById(id: String) = dao.deletePosProductById(id)\
\
    val allPosClients = dao.getAllPosClients()\
    suspend fun insertPosClient(client: PosClient) = dao.insertPosClient(client)\
    suspend fun deletePosClientById(id: String) = dao.deletePosClientById(id)\
\
    val allPosOrders = dao.getAllPosOrders()\
    suspend fun insertPosOrder(order: PosOrder) = dao.insertPosOrder(order)\
\
    fun getPosOrderItems(orderId: String) = dao.getPosOrderItems(orderId)\
    suspend fun insertPosOrderItem(item: PosOrderItem) = dao.insertPosOrderItem(item)' app/src/main/java/com/example/data/Repository.kt
