package dev.nikhilkukreja.shoppingevents.ui.addevent

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.nikhilkukreja.shoppingevents.data.repositories.ShoppingEventRepository
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val shoppingEventRepository: ShoppingEventRepository,
): ViewModel() {
    var addEventUIState by mutableStateOf(AddEventUIState())



    fun updateUIState(
        addEventDetails: AddEventDetails,
    ) {
        addEventUIState = AddEventUIState(
            addEventDetails = addEventDetails,
            isEntryValid = validateInput(addEventDetails),
        )
    }


    private fun validateInput(
        eventDetails: AddEventDetails = addEventUIState.addEventDetails,
    ): Boolean {
        with(eventDetails) {
            return name.isNotBlank() && eventDate.isNotBlank()
        }
    }

    suspend fun saveEvent() {
        if(validateInput()) {
            shoppingEventRepository.insert(shoppingEvent = addEventUIState.addEventDetails.toShoppingEvent())
        }
    }
}