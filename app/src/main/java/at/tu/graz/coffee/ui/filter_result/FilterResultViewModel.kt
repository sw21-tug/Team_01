package at.tu.graz.coffee.ui.filter_result

import androidx.lifecycle.*
import at.tu.graz.coffee.database.CoffeeRepository
import at.tu.graz.coffee.model.CoffeeWithReviews

class FilterResultViewModel(private val repository: CoffeeRepository) : ViewModel() {

    fun filterCoffees(ids: List<Int>): LiveData<List<CoffeeWithReviews>> {
        return repository.getCoffeesByIds(ids).asLiveData()
    }
}

class FilterResultViewModelFactory(private val repository: CoffeeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilterResultViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FilterResultViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}