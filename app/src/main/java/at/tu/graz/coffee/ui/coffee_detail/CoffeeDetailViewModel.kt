package at.tu.graz.coffee.ui.coffee_detail

import androidx.lifecycle.*
import at.tu.graz.coffee.database.CoffeeRepository
import at.tu.graz.coffee.model.*


class CoffeeDetailViewModel(private val repository: CoffeeRepository) : ViewModel() {

    fun getCoffee(id: Int): LiveData<CoffeeWithReviews> {
        return repository.getCoffee(id).asLiveData()
    }
}

class CoffeeDetailViewModelFactory(private val repository: CoffeeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoffeeDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CoffeeDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}