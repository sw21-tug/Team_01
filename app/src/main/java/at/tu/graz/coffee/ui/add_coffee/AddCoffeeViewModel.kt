package at.tu.graz.coffee.ui.add_coffee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import at.tu.graz.coffee.database.CoffeeRepository
import at.tu.graz.coffee.model.Coffee

class AddCoffeeViewModel(private val repository: CoffeeRepository) : ViewModel() {
    suspend fun addCoffee(coffee: Coffee) {
        repository.insert(coffee)
    }
}

class AddCoffeeViewModelFactory(private val repository: CoffeeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddCoffeeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddCoffeeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}