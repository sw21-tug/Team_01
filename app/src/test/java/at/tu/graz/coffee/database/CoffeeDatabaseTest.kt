package at.tu.graz.coffee.database

import androidx.annotation.Nullable
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.test.platform.app.InstrumentationRegistry
import at.tu.graz.coffee.model.Coffee
import at.tu.graz.coffee.model.CoffeeType
import at.tu.graz.coffee.model.CoffeeWithReviews
import at.tu.graz.coffee.model.Review
import junit.framework.TestCase
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@FixMethodOrder
class CoffeeDatabaseTest : TestCase() {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var coffeeDAO: CoffeeDAO
    private lateinit var reviewDAO: ReviewDAO
    private lateinit var db: AppDatabase

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        AppDatabase.TEST_MODE = true
        coffeeDAO = AppDatabase.getCoffeeInstance(
            InstrumentationRegistry.getInstrumentation().targetContext,
            testScope
        )
        reviewDAO = AppDatabase.getReviewInstance(
            InstrumentationRegistry.getInstrumentation().targetContext,
            testScope
        )
        db = AppDatabase.getDb()!!
    }


    @After
    public override fun tearDown() {
        AppDatabase.closeDb()
    }

    private fun returnOneCoffee(): CoffeeWithReviews {
        val coffee = Coffee(
            "Barista Espresso", 3.50, "Amazon",
            CoffeeType.NONE, 1.00, 1, " ",
            "tchibo_barista_espresso"
        )
        val reviews: List<Review> = listOf(
            Review(1, 2, 10, "Just a comment", coffee.coffeeId),
            Review(10, 3, 4, "Another comment", coffee.coffeeId),
            Review(6, 5, 5, "1", coffee.coffeeId),
            Review(1, 1, 1, "2", coffee.coffeeId),
            Review(6, 6, 2, "3", coffee.coffeeId),
            Review(1, 7, 5, "4", coffee.coffeeId),
            Review(3, 4, 7, "5", coffee.coffeeId)
        )

        return CoffeeWithReviews(coffee, reviews)
    }

    @Test
    fun testTrue() {
        assertEquals(true, true)
    }


    @Test
    fun insertCoffeeInDatabaseAndFetchAllCoffeesTest() {
        val coffeeWithReviews = returnOneCoffee()
        coffeeDAO.addCoffeeWithReviews(coffeeWithReviews.coffee, coffeeWithReviews.reviews)

        val coffeeTest = getOrAwaitValue(coffeeDAO.getAll().asLiveData())!!

        Assert.assertEquals(coffeeWithReviews.coffee.name, coffeeTest.single().coffee.name)
        Assert.assertEquals(coffeeWithReviews.reviews.size, coffeeTest.single().reviews.size)
        Assert.assertFalse(coffeeWithReviews.reviews[0].comment == coffeeTest.single().reviews[6].comment)
    }

    @Test
    fun insertCoffeeInDatabaseAndFetchAllByIdsTest() {
        val coffeeWithReviews = returnOneCoffee()
        coffeeDAO.addCoffeeWithReviews(coffeeWithReviews.coffee, coffeeWithReviews.reviews)

        val coffeeTest = getOrAwaitValue(coffeeDAO.getAllByIds(listOf(1)).asLiveData())!!

        Assert.assertEquals(coffeeWithReviews.coffee.name, coffeeTest.single().coffee.name)
        Assert.assertEquals(coffeeWithReviews.reviews.size, coffeeTest.single().reviews.size)
        Assert.assertFalse(coffeeWithReviews.reviews[0].comment == coffeeTest.single().reviews[6].comment)
    }

    @Test
    fun insertCoffeeInDatabaseAndFetchByIdTest() {
        val coffeeWithReviews = returnOneCoffee()
        coffeeDAO.addCoffeeWithReviews(coffeeWithReviews.coffee, coffeeWithReviews.reviews)

        val local = getOrAwaitValue(coffeeDAO.getById(1).asLiveData())!!

        Assert.assertEquals(coffeeWithReviews.coffee.name, local.coffee.name)
        Assert.assertEquals(coffeeWithReviews.reviews.size, local.reviews.size)
        Assert.assertFalse(coffeeWithReviews.reviews[0].comment == local.reviews[6].comment)
    }

    @Test
    fun insertCoffeeInDatabaseAndUpdateCoffeeTest() {
        val coffeeWithReviews = returnOneCoffee()
        coffeeDAO.addCoffeeWithReviews(coffeeWithReviews.coffee, coffeeWithReviews.reviews)

        var local = getOrAwaitValue(coffeeDAO.getById(1).asLiveData())!!

        local.coffee.name = "Different Coffee Name"

        coffeeDAO.updateCoffee(local.coffee)

        local =
            getOrAwaitValue(coffeeDAO.getById(1).asLiveData())!!

        Assert.assertEquals(local.coffee.name, "Different Coffee Name")
    }

    @Test
    fun insertReviewListTest() {
        val coffeeWithReviews = CoffeeWithReviews(returnOneCoffee().coffee, listOf())
        coffeeDAO.addCoffeeWithReviews(coffeeWithReviews.coffee, coffeeWithReviews.reviews)

        val reviews = returnOneCoffee().reviews

        reviews.forEach { it.coffeeCreatorId = 1 }

        coffeeDAO.insertReviewList(reviews)

        val local =
            getOrAwaitValue(coffeeDAO.getById(1).asLiveData())!!

        Assert.assertEquals(local.reviews.size, 7)
    }

    @Test
    fun getStoresOfAllCoffees() {
        val coffeeWithReviews = returnOneCoffee()
        coffeeDAO.addCoffeeWithReviews(coffeeWithReviews.coffee, coffeeWithReviews.reviews)

        val stores = getOrAwaitValue(coffeeDAO.getStoresOfAllCoffees().asLiveData())!!

        Assert.assertEquals(stores.single(), "Amazon")
    }

    @Test
    fun deleteAllCoffees() {
        val coffeeWithReviews = returnOneCoffee()
        coffeeDAO.addCoffeeWithReviews(coffeeWithReviews.coffee, coffeeWithReviews.reviews)

        var coffees = getOrAwaitValue(coffeeDAO.getAll().asLiveData())!!
        coffeeDAO.delete(coffees.first().coffee)

        coffees = getOrAwaitValue(coffeeDAO.getAll().asLiveData())!!

        Assert.assertEquals(coffees.size, 0)
    }

    @Test
    fun getAllReviews() {
        val coffeeWithReviews = returnOneCoffee()
        coffeeDAO.addCoffeeWithReviews(coffeeWithReviews.coffee, coffeeWithReviews.reviews)

        val reviews = reviewDAO.getAll()

        Assert.assertEquals(reviews.size, 7)
    }

    @Test
    fun deleteReview() {
        val coffeeWithReviews = returnOneCoffee()
        coffeeDAO.addCoffeeWithReviews(coffeeWithReviews.coffee, coffeeWithReviews.reviews)

        var reviews = reviewDAO.getAll()
        reviewDAO.delete(reviews.first())

        reviews = reviewDAO.getAll()

        Assert.assertEquals(reviews.size, 6)
    }

    @Test
    fun purgeCoffeeTable() {
        val coffeeWithReviews = returnOneCoffee()
        coffeeDAO.addCoffeeWithReviews(coffeeWithReviews.coffee, coffeeWithReviews.reviews)

        coffeeDAO.purgeTable()

        val list = getOrAwaitValue(coffeeDAO.getAll().asLiveData())!!

        Assert.assertEquals(list.size, 0)
    }

    @Test
    fun purgeReviewTable() {
        val coffeeWithReviews = returnOneCoffee()
        coffeeDAO.addCoffeeWithReviews(coffeeWithReviews.coffee, coffeeWithReviews.reviews)

        reviewDAO.purgeTable()

        val list = reviewDAO.getAll()

        Assert.assertEquals(list.size, 0)
    }

    @Test
    fun insertReviewList() {
        val reviews = returnOneCoffee().reviews

        reviewDAO.insertAll(reviews)

        val list = reviewDAO.getAll()

        Assert.assertEquals(list.size, 7)
    }
}

@Throws(InterruptedException::class)
fun <T> getOrAwaitValue(liveData: LiveData<T>): T? {
    val data = arrayOfNulls<Any>(1)
    val latch = CountDownLatch(1)
    val observer: Observer<T> = object : Observer<T> {
        override fun onChanged(@Nullable o: T) {
            data[0] = o
            latch.countDown()
            liveData.removeObserver(this)
        }
    }
    liveData.observeForever(observer)
    latch.await(2, TimeUnit.SECONDS)
    @Suppress("UNCHECKED_CAST")
    return data[0] as T?
}
