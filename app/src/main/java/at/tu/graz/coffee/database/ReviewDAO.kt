package at.tu.graz.coffee.database

import androidx.room.*
import at.tu.graz.coffee.model.Review

@Dao
interface ReviewDAO {
    @Query("SELECT * FROM review")
    fun getAll(): List<Review>

    @Insert
    fun insertAll(reviews: List<Review>)

    @Delete
    fun delete(reviews: Review)

    @Query("DELETE FROM review")
    fun purgeTable()
}