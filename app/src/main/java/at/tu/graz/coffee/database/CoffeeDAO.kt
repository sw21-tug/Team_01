package at.tu.graz.coffee.database

import androidx.room.*
import at.tu.graz.coffee.model.Coffee
import at.tu.graz.coffee.model.CoffeeWithReviews
import at.tu.graz.coffee.model.Review
import kotlinx.coroutines.flow.Flow

@Dao
interface CoffeeDAO {
    @Transaction
    @Query("SELECT * FROM coffee")
    fun getAll(): Flow<List<CoffeeWithReviews>>

    @Transaction
    @Query("SELECT * FROM coffee WHERE coffeeId LIKE :id")
    fun getById(id: Int): Flow<CoffeeWithReviews>

    @Insert
    fun insertCoffee(coffee: Coffee): Long

    @Update
    fun updateCoffee(coffee: Coffee): Int

    @Transaction
    fun addCoffeeWithReviews(coffee: Coffee, reviews: List<Review>) {
        val listId = insertCoffee(coffee)
        reviews.forEach { it.coffeeCreatorId = listId.toInt() }
        insertReviewList(reviews)
    }

    @Delete
    fun delete(coffee: Coffee)

    @Insert
    fun insertReviewList(reviews: List<Review>)

    @Transaction
    @Query("SELECT * FROM coffee WHERE coffeeId IN (:ids)")
    fun getAllByIds(ids: List<Int>): Flow<List<CoffeeWithReviews>>

    @Transaction
    @Query("SELECT store_to_buy_from FROM coffee GROUP BY store_to_buy_from")
    fun getStoresOfAllCoffees(): Flow<List<String>>

    @Query("DELETE FROM coffee")
    fun purgeTable()
}