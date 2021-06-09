package at.tu.graz.coffee.ui.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import at.tu.graz.coffee.database.CoffeeRepository
import at.tu.graz.coffee.model.CoffeeWithReviews
import at.tu.graz.coffee.model.Review

class CommentViewModel(private val repository: CoffeeRepository) : ViewModel() {

    fun getCoffee(id: Int): LiveData<CoffeeWithReviews> {
        return repository.getCoffee(id).asLiveData()
    }

    suspend fun insertReviewForCoffee(newReviews: List<Review>) {
        return repository.insertReview(newReviews)
    }
}

class CommentViewModelFactory(private val repository: CoffeeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CommentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}