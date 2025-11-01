package dev.nikhilkukreja.shoppingevents.data.repositories

import dev.nikhilkukreja.shoppingevents.data.daos.ShoppingItemDao
import dev.nikhilkukreja.shoppingevents.data.entities.ShoppingItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ShoppingItemRepository {
    suspend fun insert(shoppingItem: ShoppingItem)
    suspend fun update(shoppingItem: ShoppingItem)
    suspend fun delete(shoppingItem: ShoppingItem)
    fun getItems(): Flow<List<ShoppingItem>>
}

class ShoppingItemRepositoryImpl @Inject constructor(
    private val shoppingItemDao: ShoppingItemDao
) : ShoppingItemRepository {
    override suspend fun insert(shoppingItem: ShoppingItem) = shoppingItemDao.insert(shoppingItem)

    override suspend fun update(shoppingItem: ShoppingItem) = shoppingItemDao.update(shoppingItem)

    override suspend fun delete(shoppingItem: ShoppingItem) = shoppingItemDao.delete(shoppingItem)

    override fun getItems(): Flow<List<ShoppingItem>> = shoppingItemDao.getItems()
}