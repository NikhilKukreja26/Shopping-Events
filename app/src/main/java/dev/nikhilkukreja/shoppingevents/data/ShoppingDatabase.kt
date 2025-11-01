package dev.nikhilkukreja.shoppingevents.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.nikhilkukreja.shoppingevents.data.daos.ShoppingEventDao
import dev.nikhilkukreja.shoppingevents.data.daos.ShoppingItemDao
import dev.nikhilkukreja.shoppingevents.data.entities.ShoppingEvent
import dev.nikhilkukreja.shoppingevents.data.entities.ShoppingItem

@Database(entities = [ShoppingEvent::class, ShoppingItem::class], version = 1)
abstract class ShoppingDatabase: RoomDatabase() {
    abstract fun shoppingEventDao(): ShoppingEventDao
    abstract fun shoppingItemDao(): ShoppingItemDao

    companion object {
        const val DATABASE_NAME = "shopping_events"

        @Volatile
        private var Instance: ShoppingDatabase? = null

        fun getInstance(context: Context) : ShoppingDatabase {
            return  Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    ShoppingDatabase::class.java,
                    DATABASE_NAME
                ).build().also {
                    Instance = it
                }
            }
        }
    }
}