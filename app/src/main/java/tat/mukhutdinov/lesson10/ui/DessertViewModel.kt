package tat.mukhutdinov.lesson10.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import tat.mukhutdinov.lesson10.data.Datasource.dessertList
import tat.mukhutdinov.lesson10.data.UIState

class DessertViewModel : ViewModel()  {
    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    fun onDessertClicked() {
        _uiState.update {
            val dessertsSold = it.dessertsSold + 1
            val nextDessertIndex = determineDessertIndex(dessertsSold)
            it.copy(
                revenue = it.revenue+it.currentDessertPrice,
                dessertsSold = dessertsSold,
                currentDessertIndex = nextDessertIndex,
                currentDessertPrice = dessertList[nextDessertIndex].price,
                currentDessertImageId = dessertList[nextDessertIndex].imageId
            )
        }
    }

    private fun determineDessertIndex(
        dessertsSold: Int
    ): Int {
        var dessertIndex = 0
        for (index in dessertList.indices) {
            if (dessertsSold >= dessertList[index].startProductionAmount) {
                dessertIndex = index
            } else {
                // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
                // you'll start producing more expensive desserts as determined by startProductionAmount
                // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
                // than the amount sold.
                break
            }
        }
        return dessertIndex
    }
}