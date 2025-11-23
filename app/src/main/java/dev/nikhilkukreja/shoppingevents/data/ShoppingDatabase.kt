package dev.nikhilkukreja.shoppingevents.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.nikhilkukreja.shoppingevents.data.daos.ShoppingEventDao
import dev.nikhilkukreja.shoppingevents.data.daos.ShoppingItemDao
import dev.nikhilkukreja.shoppingevents.data.entities.ShoppingEvent
import dev.nikhilkukreja.shoppingevents.data.entities.ShoppingItem

// ShoppingDatabase.kt (file ke start me ya class ke upar)
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("PRAGMA foreign_keys=OFF")
        db.execSQL(
            """
            DELETE FROM shopping_items
            WHERE event_id NOT IN (SELECT id FROM shopping_events)
        """.trimIndent()
        )
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS shopping_items_new (
                itemId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                event_id INTEGER NOT NULL,
                item_name TEXT NOT NULL,
                price REAL NOT NULL,
                quantity REAL NOT NULL,
                FOREIGN KEY(event_id) REFERENCES shopping_events(id) ON DELETE CASCADE
            )
        """.trimIndent()
        )
        db.execSQL(
            """
            INSERT INTO shopping_items_new (itemId, event_id, item_name, price, quantity)
            SELECT itemId, COALESCE(event_id,0), COALESCE(item_name,''), COALESCE(price,0.0), COALESCE(quantity,1.0)
            FROM shopping_items
            WHERE event_id IN (SELECT id FROM shopping_events)
        """.trimIndent()
        )
        db.execSQL("DROP TABLE shopping_items")
        db.execSQL("ALTER TABLE shopping_items_new RENAME TO shopping_items")
//        db.execSQL("CREATE INDEX IF NOT EXISTS index_shopping_items_event_id ON shopping_items(event_id)")
        db.execSQL("PRAGMA foreign_keys=ON")
    }
}

@Database(entities = [ShoppingEvent::class, ShoppingItem::class], version = 2)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun shoppingEventDao(): ShoppingEventDao
    abstract fun shoppingItemDao(): ShoppingItemDao

    companion object {
        const val DATABASE_NAME = "shopping_events"

        @Volatile
        private var Instance: ShoppingDatabase? = null

        fun getInstance(context: Context): ShoppingDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    ShoppingDatabase::class.java,
                    DATABASE_NAME
                ).addMigrations(MIGRATION_1_2).build().also {
                    Instance = it
                }
            }
        }
    }
}