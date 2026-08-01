#!/bin/bash
sed -i '/fun clearCachedQuran()/a \
    // --- POS SYSTEM ---\
    val allPosProducts: StateFlow<List<PosProduct>> = repository.allPosProducts.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())\
    fun insertPosProduct(product: PosProduct) = viewModelScope.launch { repository.insertPosProduct(product) }\
    fun deletePosProductById(id: String) = viewModelScope.launch { repository.deletePosProductById(id) }\
\
    val allPosClients: StateFlow<List<PosClient>> = repository.allPosClients.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())\
    fun insertPosClient(client: PosClient) = viewModelScope.launch { repository.insertPosClient(client) }\
    fun deletePosClientById(id: String) = viewModelScope.launch { repository.deletePosClientById(id) }\
\
    val allPosOrders: StateFlow<List<PosOrder>> = repository.allPosOrders.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())\
    fun insertPosOrder(order: PosOrder) = viewModelScope.launch { repository.insertPosOrder(order) }\
    fun insertPosOrderItem(item: PosOrderItem) = viewModelScope.launch { repository.insertPosOrderItem(item) }' app/src/main/java/com/example/viewmodel/StudentKitViewModel.kt
