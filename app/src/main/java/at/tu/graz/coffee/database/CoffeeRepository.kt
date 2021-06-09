package at.tu.graz.coffee.database

import androidx.annotation.WorkerThread
import at.tu.graz.coffee.model.Coffee
import at.tu.graz.coffee.model.CoffeeWithReviews
import at.tu.graz.coffee.model.Review
import kotlinx.coroutines.flow.Flow

class CoffeeRepository(private val coffeeDAO: CoffeeDAO, private val reviewDAO: ReviewDAO) {

    val allCoffees: Flow<List<CoffeeWithReviews>> = coffeeDAO.getAll()

    fun getCoffee(id: Int): Flow<CoffeeWithReviews> {
        return coffeeDAO.getById(id)
    }

    fun getCoffeesByIds(ids: List<Int>): Flow<List<CoffeeWithReviews>> {
        return coffeeDAO.getAllByIds(ids)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(coffee: Coffee) {
        coffeeDAO.insertCoffee(coffee)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertReview(reviews: List<Review>) {
        reviewDAO.insertAll(reviews)
    }

    fun getStoresOfAllCoffees(): Flow<List<String>> {
        return coffeeDAO.getStoresOfAllCoffees()
    }
}