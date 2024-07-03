package julian.scholler.brauerei.breweries.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import julian.scholler.brauerei.data.Result
import julian.scholler.brauerei.data.remote.model.Brewery
import julian.scholler.brauerei.data.remote.repository.BreweryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BreweriesViewModel @Inject constructor(private val repository: BreweryRepository) : ViewModel() {

    private val _breweries = MutableStateFlow<Result<List<Brewery>>>(Result.Loading)
    val breweries: StateFlow<Result<List<Brewery>>> = _breweries

    private val _dailyBrewery = MutableStateFlow<Result<Brewery>>(Result.Loading)
    val dailyBrewery: StateFlow<Result<Brewery>> = _dailyBrewery.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        fetchDailyBrewery()
        fetchBreweries()
    }

    private fun fetchBreweries() {
        Timber.d("fetchBreweries")
        viewModelScope.launch {
            repository.getBreweries().collect { result ->
                _breweries.value = result
            }
        }
    }

    private fun fetchDailyBrewery() {
        viewModelScope.launch {
            repository.getRandomBrewery().collect {
                _dailyBrewery.value = Result.Success(it)
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
