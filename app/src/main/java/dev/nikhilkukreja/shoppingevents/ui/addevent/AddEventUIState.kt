package dev.nikhilkukreja.shoppingevents.ui.addevent

import dev.nikhilkukreja.shoppingevents.data.entities.ShoppingEvent

data class AddEventDetails(
    val id: Long = 0,
    val name: String = "",
    val initialBudget: Double = 0.0,
    val totalCost: Double = 0.0,
    val eventDate: String = "",
    val completed: Boolean = false,
)

data class AddEventUIState(
    val addEventDetails: AddEventDetails = AddEventDetails(),
    val isEntryValid: Boolean = false,
)


fun AddEventDetails.toShoppingEvent(): ShoppingEvent = ShoppingEvent(
    id = id,
    name = name,
    initialBudget = initialBudget,
    totalCost = totalCost,
    eventDate = eventDate,
    completed = completed,
)

fun ShoppingEvent.toAddEventDetails(): AddEventDetails = AddEventDetails(
    id = id,
    name = name,
    initialBudget = initialBudget,
    totalCost = totalCost,
    eventDate = eventDate,
    completed = completed,
)