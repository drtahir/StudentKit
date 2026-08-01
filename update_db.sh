#!/bin/bash
sed -i 's/version = 6,/version = 7,/g' app/src/main/java/com/example/data/AppDatabase.kt
sed -i '/CachedQuranVerse::class/a \
        ,PosProduct::class,\
        PosClient::class,\
        PosOrder::class,\
        PosOrderItem::class' app/src/main/java/com/example/data/AppDatabase.kt

sed -i '/fun clearCachedQuran()/a \
    // --- POS SYSTEM ---\
    @Query("SELECT * FROM pos_products ORDER BY name ASC")\
    fun getAllPosProducts(): Flow<List<PosProduct>>\
    @Insert(onConflict = OnConflictStrategy.REPLACE)\
    suspend fun insertPosProduct(product: PosProduct)\
    @Query("DELETE FROM pos_products WHERE id = :id")\
    suspend fun deletePosProductById(id: String)\
\
    @Query("SELECT * FROM pos_clients ORDER BY name ASC")\
    fun getAllPosClients(): Flow<List<PosClient>>\
    @Insert(onConflict = OnConflictStrategy.REPLACE)\
    suspend fun insertPosClient(client: PosClient)\
    @Query("DELETE FROM pos_clients WHERE id = :id")\
    suspend fun deletePosClientById(id: String)\
\
    @Query("SELECT * FROM pos_orders ORDER BY date DESC")\
    fun getAllPosOrders(): Flow<List<PosOrder>>\
    @Insert(onConflict = OnConflictStrategy.REPLACE)\
    suspend fun insertPosOrder(order: PosOrder)\
\
    @Query("SELECT * FROM pos_order_items WHERE orderId = :orderId")\
    fun getPosOrderItems(orderId: String): Flow<List<PosOrderItem>>\
    @Insert(onConflict = OnConflictStrategy.REPLACE)\
    suspend fun insertPosOrderItem(item: PosOrderItem)' app/src/main/java/com/example/data/AppDatabase.kt
