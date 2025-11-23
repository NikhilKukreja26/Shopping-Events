package dev.nikhilkukreja.shoppingevents.ui.eventdetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.nikhilkukreja.shoppingevents.data.entities.ShoppingItem
import dev.nikhilkukreja.shoppingevents.data.repositories.ShoppingEventRepository
import dev.nikhilkukreja.shoppingevents.data.repositories.ShoppingItemRepository
import dev.nikhilkukreja.shoppingevents.destinations.EventDetailsRoute
import dev.nikhilkukreja.shoppingevents.ui.addevent.AddEventDetails
import dev.nikhilkukreja.shoppingevents.ui.addevent.toAddEventDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val shoppingEventRepository: ShoppingEventRepository,
    private  val shoppingItemRepository: ShoppingItemRepository
) : ViewModel() {

    private val detailsRoute: EventDetailsRoute = savedStateHandle.toRoute<EventDetailsRoute>()

    private val _eventDetailsUIState = MutableStateFlow<EventDetailsUIState>(EventDetailsUIState())

    val eventDetailsUIState = _eventDetailsUIState.asStateFlow()

    init {
        viewModelScope.launch {
            shoppingEventRepository.getEventAndItems(detailsRoute.eventId).collect {map->
                Log.d("EventDetailsViewModel", map.toString())
                val entry = map.entries.firstOrNull()
                _eventDetailsUIState.update {
                    it.copy(
                        eventDetails = entry?.key?.toAddEventDetails() ?: AddEventDetails(
                            name = detailsRoute.eventName,
                        ),
                        itemList = entry?.value?.map { it ->
                            ItemUIState(
                                itemDetails = it.toItemDetails()
                            )
                        } ?: emptyList()
                    )
                }
            }
        }
    }

    fun onEditModeChanged(itemDetails: ItemDetails) {
        _eventDetailsUIState.update {
            it.copy(
                itemList = it.itemList.map { item ->
                    if (item.itemDetails.itemId == itemDetails.itemId) {
                        item.copy(isEdit = true)
                    } else {
                        item
                    }
                }
            )
        }
    }

    fun onValueChanged(itemDetails: ItemDetails) {
        _eventDetailsUIState.update {
            it.copy(
                itemList = it.itemList.map { item ->
                    if (item.itemDetails.itemId == itemDetails.itemId) {
                        item.copy(itemDetails = itemDetails)
                    } else {
                        item
                    }
                }
            )
        }
    }

    suspend fun addItem() {
        val item = ShoppingItem(
            eventId =  detailsRoute.eventId,
            itemName = "Item"
        )
        shoppingItemRepository.insert(item);
    }

    suspend fun updateItem(
        itemDetails: ItemDetails
    ) {
        val item = itemDetails.toShoppingItem()
        shoppingItemRepository.update(item)
    }

    suspend fun deleteShoppingItem(
        itemDetails: ItemDetails

    ) {
        shoppingItemRepository.delete(itemDetails.toShoppingItem())
    }
}