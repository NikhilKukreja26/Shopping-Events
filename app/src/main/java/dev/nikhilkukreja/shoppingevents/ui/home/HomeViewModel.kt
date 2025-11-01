package dev.nikhilkukreja.shoppingevents.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.nikhilkukreja.shoppingevents.data.repositories.ShoppingEventRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val shoppingEventRepository: ShoppingEventRepository
): ViewModel() {

    private val _homeUIState = MutableStateFlow<HomeUIState>(HomeUIState())
    val homeUIState: StateFlow<HomeUIState> = _homeUIState.asStateFlow()

    init {
        viewModelScope.launch() {
            shoppingEventRepository.getEvents().collect { events ->
               _homeUIState.update {
                   it.copy(events = events)
               }
            }
        }
    }
}