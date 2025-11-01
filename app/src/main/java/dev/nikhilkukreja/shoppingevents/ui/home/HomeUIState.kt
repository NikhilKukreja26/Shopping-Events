package dev.nikhilkukreja.shoppingevents.ui.home

import dev.nikhilkukreja.shoppingevents.data.entities.ShoppingEvent

data class HomeUIState(
    val events: List<ShoppingEvent> = emptyList(),
)
