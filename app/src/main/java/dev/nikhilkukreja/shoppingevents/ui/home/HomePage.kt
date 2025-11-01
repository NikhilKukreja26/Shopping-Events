package dev.nikhilkukreja.shoppingevents.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.nikhilkukreja.shoppingevents.customcomposables.ShoppingAppbar
import dev.nikhilkukreja.shoppingevents.data.entities.ShoppingEvent
import dev.nikhilkukreja.shoppingevents.customcomposables.EmptyListUI

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToAddEvent: () -> Unit,
    onNavigateToEventDetails: (Long, String) -> Unit,
    ) {

    val homeUIState by viewModel.homeUIState.collectAsState()

    Scaffold(
        topBar = {
            ShoppingAppbar(
                title = "Shopping Events",
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAddEvent
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,

    ) {
        if(homeUIState.events.isEmpty()) {
            EmptyListUI(
                message = "No events found\nAdd an event to get started",
                modifier = modifier.padding(it),

            )
            return@Scaffold
        }
        ShoppingList(
            shoppingEvents = homeUIState.events,
            modifier = modifier.padding(it),
            onNavigateToEventDetails = onNavigateToEventDetails,

        )

    }
}

@Composable
fun ShoppingList(
    modifier: Modifier = Modifier,
    shoppingEvents: List<ShoppingEvent>,
    onNavigateToEventDetails : (Long, String) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(shoppingEvents) {event ->
            ShoppingEvent(
                shoppingEvent = event,
                onTapEvent = onNavigateToEventDetails,
            )
        }
    }
}

@Composable
fun ShoppingEvent(
    modifier: Modifier = Modifier,
    shoppingEvent: ShoppingEvent,
    onTapEvent : (Long, String) -> Unit,
) {
    ListItem(
        modifier = modifier.padding(8.dp).clickable {
            onTapEvent(shoppingEvent.id, shoppingEvent.name)
        },
        tonalElevation = 10.dp,
        headlineContent = {
            Text(text = shoppingEvent.name)
        },
        supportingContent = {
            Text(text = shoppingEvent.eventDate)
        },
        trailingContent = {
            Text(
                text = "\$${shoppingEvent.totalCost.toString()}",
                style = MaterialTheme.typography.bodyLarge
            )
        },
        leadingContent = {
            IconButton(
                onClick = {}
            ) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete")
            }
        },

    )
}