package dev.nikhilkukreja.shoppingevents.ui.eventdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.nikhilkukreja.shoppingevents.customcomposables.DismissibleItem
import dev.nikhilkukreja.shoppingevents.customcomposables.EditListItem
import dev.nikhilkukreja.shoppingevents.customcomposables.EmptyListUI
import dev.nikhilkukreja.shoppingevents.customcomposables.ShoppingAppbar
import dev.nikhilkukreja.shoppingevents.ui.addevent.AddEventDetails
import dev.nikhilkukreja.shoppingevents.ui.home.ShoppingEvent
import kotlinx.coroutines.launch

@Composable
fun EventDetailsPage(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    navigateBack: () -> Unit,
    eventDetailsViewModel: EventDetailsViewModel = hiltViewModel(),
) {
    val eventDetailsUIState by eventDetailsViewModel.eventDetailsUIState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    Scaffold(
        topBar = {
            ShoppingAppbar(
                title = "Event Details",
                canNavigateBack = true,
                navigateUp = navigateUp,
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        eventDetailsViewModel.addItem()
                        if (eventDetailsUIState.itemList.isNotEmpty()) {
                            lazyListState.animateScrollToItem(eventDetailsUIState.itemList.size - 1)
                        }
                    }
                },
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Text(
                    text = "Add Item",
                )

            }
        }
    ) {

        if (eventDetailsUIState.itemList.isEmpty()) {
            EmptyListUI(
                message = "No items found\nAdd an item to get started",
                modifier = modifier.padding(it),
            )
            return@Scaffold
        }

        ShoppingItemList(
            modifier = modifier.padding(it),
            eventDetails = eventDetailsUIState.eventDetails,
            itemList = eventDetailsUIState.itemList,
            lazyListState = lazyListState,
            onEditModeChanged = eventDetailsViewModel::onEditModeChanged,
            onValueChanged = eventDetailsViewModel::onValueChanged,
            onItemUpdate = {itemDetails ->
                coroutineScope.launch {
                    eventDetailsViewModel.updateItem(itemDetails)
                }
            },
            onDeleteItem = { itemDetails ->
                coroutineScope.launch {
                    eventDetailsViewModel.deleteShoppingItem(itemDetails)
                }
            }
        )
    }
}

@Composable
fun ShoppingItemList(
    modifier: Modifier = Modifier,
    eventDetails: AddEventDetails,
    itemList: List<ItemUIState>,
    lazyListState: LazyListState,
    onEditModeChanged: (ItemDetails) -> Unit,
    onValueChanged: (ItemDetails) -> Unit,
    onItemUpdate: (ItemDetails) -> Unit,
    onDeleteItem: (ItemDetails) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
    ) {
        item {
            ListItem(
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                headlineContent = {
                    Text(
                        text = "Budget: \$${eventDetails.initialBudget}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                trailingContent = {
                    Text(
                        text = "\$${eventDetails.totalCost}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }


            )
        }
        items(
            itemList,
            key = { item -> item.itemDetails.itemId })
        { itemUIState ->
            SingleListItem(
                modifier = modifier,
                itemUIState = itemUIState,
                onValueChanged = onValueChanged,
                onItemUpdate = onItemUpdate,
                onEditModeChanged = onEditModeChanged,
                onDeleteItem = onDeleteItem
            )
        }
        item {
            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}

@Composable
fun SingleListItem(
    modifier: Modifier = Modifier,
    itemUIState: ItemUIState,
    onValueChanged: (ItemDetails) -> Unit,
    onItemUpdate: (ItemDetails) -> Unit,
    onDeleteItem: (ItemDetails) -> Unit,
    onEditModeChanged: (ItemDetails) -> Unit,
) {
    if (itemUIState.isEdit) {
        EditListItem(
            modifier = modifier,
            itemDetails = itemUIState.itemDetails,
            onValueChanged = onValueChanged,
            onItemUpdate = onItemUpdate,
        )
    } else {
        DismissibleItem(
            onDelete = {
                onDeleteItem(itemUIState.itemDetails)
            },
        ) {
            ListItem(
                leadingContent = {
                    IconButton(onClick = {
                        onEditModeChanged(itemUIState.itemDetails)
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                },
                headlineContent = {
                    Text(
                        text = itemUIState.itemDetails.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                supportingContent = {
                    Text(
                        text = "Quantity: ${itemUIState.itemDetails.quantity}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                trailingContent = {
                    Text(
                        text = "Price: \$${itemUIState.itemDetails.price}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            )
        }
    }

}