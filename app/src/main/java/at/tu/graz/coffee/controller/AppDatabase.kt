package at.tu.graz.coffee.controller

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import at.tu.graz.coffee.model.Coffee
import at.tu.graz.coffee.model.CoffeeType
import at.tu.graz.coffee.model.CoffeeWithReviews
import at.tu.graz.coffee.model.Review
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

@Database(entities = [Coffee::class, Review::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coffeeDAO(): CoffeeDAO
    abstract fun reviewDAO(): ReviewDAO

    companion object {
        var TEST_MODE = false
        private val databaseName = "coffeeDatabase"

        private var db: AppDatabase? = null
        private var dbInstanceCoffee: CoffeeDAO? = null
        private var dbInstanceReview: ReviewDAO? = null

        fun getCoffeeInstance(context: Context, scope: CoroutineScope): CoffeeDAO {
            if (dbInstanceCoffee == null || dbInstanceReview == null) {
                if (TEST_MODE) {
                    if (db == null) {
                        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .addCallback(AppDatabaseCallback(scope))
                            .build()
                    }
                    dbInstanceCoffee = db?.coffeeDAO()
                    dbInstanceReview = db?.reviewDAO()

                } else {
                    if (db == null) {
                        db = Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
                            .fallbackToDestructiveMigration()
                            .addCallback(AppDatabaseCallback(scope))
                            .build()
                    }
                    dbInstanceCoffee = db?.coffeeDAO()
                    dbInstanceReview = db?.reviewDAO()
                }
            }
            return dbInstanceCoffee!!
        }

        fun getReviewInstance(context: Context, scope: CoroutineScope): ReviewDAO {
            if (dbInstanceCoffee == null || dbInstanceReview == null) {
                if (TEST_MODE) {
                    if (db == null) {
                        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .addCallback(AppDatabaseCallback(scope))
                            .build()
                    }
                    dbInstanceCoffee = db?.coffeeDAO()
                    dbInstanceReview = db?.reviewDAO()

                } else {
                    if (db == null) {
                        db = Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
                            .fallbackToDestructiveMigration()
                            .addCallback(AppDatabaseCallback(scope))
                            .build()
                    }
                    dbInstanceCoffee = db?.coffeeDAO()
                    dbInstanceReview = db?.reviewDAO()
                }
            }
            return dbInstanceReview!!
        }

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(dbSql: SupportSQLiteDatabase) {
                super.onCreate(dbSql)
                db.let { database ->
                    scope.launch(Dispatchers.IO) {
                        createData(database!!.coffeeDAO(), database.reviewDAO())
                    }
                }
            }
        }

        fun createData(coffeeDAO: CoffeeDAO, reviewDAO: ReviewDAO) {
            val coffees: List<Coffee> = listOf(
                Coffee(
                    "Caffe Crema", 9.00, "Supermarket",
                    CoffeeType.NONE, 1.00, 1, " ",
                    "spar_premium_caffe_crema"
                ),
                Coffee(
                    "Barista Espresso", 3.50, "Amazon",
                    CoffeeType.NONE, 1.00, 1, " ",
                    "tchibo_barista_espresso"
                ),
                Coffee(
                    "Black and White", 5.0, "Billa",
                    CoffeeType.NONE, 1.00, 1, " ",
                    "tchibo_black_and_white"
                ),
                Coffee(
                    "Caffe Crema", 9.00, "Spar",
                    CoffeeType.NONE, 1.00, 1, " ",
                    "spar_premium_caffe_crema"
                ),
                Coffee(
                    "Barista Espresso", 3.50, "Supermarket",
                    CoffeeType.NONE, 1.00, 1, " ",
                    "tchibo_barista_espresso"
                ),
                Coffee(
                    "Black and White", 5.0, "Amazon",
                    CoffeeType.NONE, 1.00, 1, " ",
                    "tchibo_black_and_white"
                ),
                Coffee(
                    "Caffe Crema", 9.00, "Billa",
                    CoffeeType.NONE, 1.00, 1, " ",
                    "spar_premium_caffe_crema"
                ),
                Coffee(
                    "Barista Espresso", 3.50, "Spar",
                    CoffeeType.NONE, 1.00, 1, " ",
                    "tchibo_barista_espresso"
                ),
                Coffee(
                    "Black and White", 5.0, "Supermarket",
                    CoffeeType.NONE, 1.00, 1, " ",
                    "tchibo_black_and_white"
                ),
                Coffee(
                    "Black and White", 5.0, "Supermarket",
                    CoffeeType.NONE, 1.00, 1, " ",
                    "tchibo_black_and_white"
                )
            )

            coffees.forEach { coffee ->
                val id = coffeeDAO.insertCoffee(coffee)

                val reviews: List<Review> = listOf(
                        Review(Random.nextInt(1, 10), Random.nextInt(1, 10), Random.nextInt(1, 10),
                                "Just a comment", Random.nextInt(1, 10)),
                        Review(Random.nextInt(1, 10), Random.nextInt(1, 10), Random.nextInt(1, 10),
                                "Another comment", Random.nextInt(1, 10))
                )
                reviews.forEach {review -> review.coffeeCreatorId = id.toInt() }
                reviewDAO.insertAll(reviews)
            }
        }
    }
}
