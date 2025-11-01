package dev.nikhilkukreja.shoppingevents.ui.eventdetails

import dev.nikhilkukreja.shoppingevents.data.entities.ShoppingItem
import dev.nikhilkukreja.shoppingevents.ui.addevent.AddEventDetails

data class ItemDetails(
    val itemId: Long = 0,
    val eventId: Long = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = ""
)

data class ItemUIState(
    val isEdit: Boolean = false,
    val itemDetails: ItemDetails = ItemDetails()
)

data class EventDetailsUIState(
    val eventDetails: AddEventDetails = AddEventDetails(),
    val itemList: List<ItemUIState> = emptyList()
)

fun ShoppingItem.toItemDetails() : ItemDetails = ItemDetails(
    itemId = itemId,
    eventId = eventId,
    name = itemName,
    price = price.toString(),
    quantity = quantity.toString()
)

fun ItemDetails.toShoppingItem() : ShoppingItem = ShoppingItem(
    itemId = itemId,
    eventId = eventId,
    itemName = name,
    price = price.toDoubleOrNull() ?:0.0,
    quantity = quantity.toDoubleOrNull() ?:0.0,
)