package at.tu.graz.coffee.ui.home

import androidx.lifecycle.*
import at.tu.graz.coffee.database.CoffeeRepository
import at.tu.graz.coffee.model.CoffeeWithReviews

class HomeViewModel(repository: CoffeeRepository) : ViewModel() {

    val allCoffees: LiveData<List<CoffeeWithReviews>> = repository.allCoffees.asLiveData()
}

class HomeViewModelFactory(private val repository: CoffeeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}