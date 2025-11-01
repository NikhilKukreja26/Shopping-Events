package dev.nikhilkukreja.shoppingevents.hiltmodules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.nikhilkukreja.shoppingevents.data.repositories.ShoppingEventRepository
import dev.nikhilkukreja.shoppingevents.data.repositories.ShoppingEventRepositoryImpl
import dev.nikhilkukreja.shoppingevents.data.repositories.ShoppingItemRepository
import dev.nikhilkukreja.shoppingevents.data.repositories.ShoppingItemRepositoryImpl


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindShoppingEventRepository(impl: ShoppingEventRepositoryImpl): ShoppingEventRepository

    @Binds
    abstract fun bindShoppingItemRepository(impl: ShoppingItemRepositoryImpl) : ShoppingItemRepository

}