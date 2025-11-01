package dev.nikhilkukreja.shoppingevents.hiltmodules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.nikhilkukreja.shoppingevents.data.ShoppingDatabase
import dev.nikhilkukreja.shoppingevents.data.daos.ShoppingEventDao
import dev.nikhilkukreja.shoppingevents.data.daos.ShoppingItemDao

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideShoppingEventDao(@ApplicationContext context: Context): ShoppingEventDao {
        return ShoppingDatabase.getInstance(context).shoppingEventDao()
    }

    @Provides
    fun provideShoppingItemDao(@ApplicationContext context: Context): ShoppingItemDao {
        return ShoppingDatabase.getInstance(context).shoppingItemDao()
    }
}