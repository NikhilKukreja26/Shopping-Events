@file:OptIn(ExperimentalMaterial3Api::class)

package dev.nikhilkukreja.shoppingevents.ui.addevent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.nikhilkukreja.shoppingevents.customcomposables.ShoppingAppbar
import dev.nikhilkukreja.shoppingevents.utils.formateDate
import kotlinx.coroutines.launch

@Composable
fun AddEventPage(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    navigateBack: () -> Unit,
    addEventViewModel: AddEventViewModel = hiltViewModel(),
    ) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ShoppingAppbar(
                title = "Add Event",
                canNavigateBack = true,
                navigateUp = navigateUp,
            )
        },
    ) {

       EventForm(
           modifier = modifier.padding(it),
           addEventUIState = addEventViewModel.addEventUIState,
           onEventValueChanged = addEventViewModel::updateUIState,
           onSaveClick = {
               coroutineScope.launch {
                   addEventViewModel.saveEvent()
                   navigateBack()
               }
           },
       )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventForm(
    modifier: Modifier = Modifier,
    addEventUIState: AddEventUIState,
    onEventValueChanged: (AddEventDetails) -> Unit,
    onSaveClick: () -> Unit,
) {
    var openDatePickerDialog by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,   // <-- use Top instead of Center
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextInputFields(
            addEventDetails = addEventUIState.addEventDetails,
            onEventValueChanged = onEventValueChanged,
        )

        DatePickerUI(
            shouldOpenDialog = openDatePickerDialog,
            datePickerState = datePickerState,
            onDismissRequest = { openDatePickerDialog = false },
            onClickConfirmButton = {
                datePickerState.selectedDateMillis?.let {
                    onEventValueChanged(
                        addEventUIState.addEventDetails.copy(eventDate = formateDate(it)!!)
                    )
                }
                openDatePickerDialog = false
            },
            onClickCancelButton = { openDatePickerDialog = false },
        )

        Spacer(modifier = Modifier.padding(top = 8.dp))

        DatePickerButtonUI(
            datePickerState = datePickerState,
            onSelectDateButtonClicked = { openDatePickerDialog = true },
        )

        Button(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onClick = onSaveClick,
            enabled = addEventUIState.isEntryValid
        ) {
            Text(
                text = "Save",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
fun TextInputFields(
    modifier: Modifier = Modifier,
    addEventDetails: AddEventDetails,
    onEventValueChanged: (AddEventDetails) -> Unit,
    ) {
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = addEventDetails.name,
            onValueChange = {
                onEventValueChanged(addEventDetails.copy(name = it)) },
            label = { Text(text = "Event Name") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
        OutlinedTextField(
            value = addEventDetails.initialBudget.toString(),
            onValueChange = {
                onEventValueChanged(addEventDetails.copy(initialBudget = it.toDouble())) },
            label = { Text(text = "Initial Budget") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerUI(
    modifier: Modifier = Modifier,
    shouldOpenDialog: Boolean,
    datePickerState: DatePickerState,
    onDismissRequest: () -> Unit,
    onClickConfirmButton: ()-> Unit,
    onClickCancelButton: () -> Unit,
) {
    if(shouldOpenDialog) {
        val confirmEnabled by remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }

        DatePickerDialog(
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(
                    enabled = confirmEnabled,
                    onClick = onClickConfirmButton
                ) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onClickCancelButton
                ) {
                    Text(text = "Cancel")
                }
            },
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerButtonUI(
    modifier: Modifier = Modifier,
    datePickerState: DatePickerState,
    onSelectDateButtonClicked: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ElevatedButton(
            onClick = onSelectDateButtonClicked,
        ) {
            Text(text = "Select Date")
        }

        Text(text = formateDate(datePickerState.selectedDateMillis)?: "Nothing Selected")
    }
}

@Preview
@Composable
private fun AddEventPagePreview() {
    AddEventPage(
        modifier = Modifier,
        navigateUp = {},
        navigateBack = {},
    )
    
}