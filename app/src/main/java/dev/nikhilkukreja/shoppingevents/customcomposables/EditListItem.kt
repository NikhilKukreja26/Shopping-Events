package dev.nikhilkukreja.shoppingevents.customcomposables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import dev.nikhilkukreja.shoppingevents.ui.eventdetails.ItemDetails

@Composable
fun EditListItem(
    modifier: Modifier = Modifier,
    itemDetails: ItemDetails,
    onValueChanged: (ItemDetails) -> Unit,
    onItemUpdate: (ItemDetails) -> Unit,
    ) {
    ListItem(
        modifier = modifier,
        headlineContent =  {
            OutlinedTextField(
                value = itemDetails.name,
                onValueChange = {
                    onValueChanged(itemDetails.copy(name = it))
                },
                label = { Text(text = "Item Name") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
            )
        },
        supportingContent = {
            Row {
                OutlinedTextField(
                    value = itemDetails.quantity,
                    onValueChange = {
                        onValueChanged(itemDetails.copy(quantity = it))
                    },
                    label = { Text(text = "Quantity") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                )
                OutlinedTextField(
                    value = itemDetails.price,
                    onValueChange = {
                        onValueChanged(itemDetails.copy(price = it))
                    },
                    label = { Text(text = "Price") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                )
            }
        },
        trailingContent = {
            IconButton(onClick = { onItemUpdate(itemDetails) }) {
                Icon(Icons.Filled.Done, contentDescription = "Done")
            }
        }
    )
}