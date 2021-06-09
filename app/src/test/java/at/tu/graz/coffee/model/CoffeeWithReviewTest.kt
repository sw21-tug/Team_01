package at.tu.graz.coffee.model

import junit.framework.TestCase

class CoffeeWithReviewTest : TestCase() {

    private val reviews: List<Review> = listOf(
        Review(1, 2, 10, "Just a comment", 1),
        Review(10, 3, 4, "Another comment", 1),
        Review(6, 5, 5, "1", 1),
        Review(1, 1, 1, "2", 1),
        Review(6, 6, 2, "3", 1),
        Review(1, 7, 5, "4", 1),
        Review(3, 4, 7, "5", 1)
    )
    private val coffeeWithReviews: CoffeeWithReviews = CoffeeWithReviews(
        Coffee(
            "My coffee",
            5.99,
            "Amazon",
            CoffeeType.MIXED,
            1.0,
            3,
            "Very good",
            "rocket_coffee"
        ), reviews
    )


    fun testReview() {

        assertEquals(reviews[0], coffeeWithReviews.reviews[0])
        assertEquals(reviews[1], coffeeWithReviews.reviews[1])
        assertEquals(reviews[2], coffeeWithReviews.reviews[2])


        coffeeWithReviews.coffee.evaluationCost = 6.0
        coffeeWithReviews.coffee.evaluationTaste = 6.0
        coffeeWithReviews.coffee.evaluationAvailability = 3.0
        coffeeWithReviews.coffee.evaluationTotal = 5.0

        assertEquals(6.0, coffeeWithReviews.coffee.evaluationTaste, 0.1)
        assertEquals(6.0, coffeeWithReviews.coffee.evaluationCost, 0.1)
        assertEquals(3.0, coffeeWithReviews.coffee.evaluationAvailability, 0.1)
        assertEquals(5.0, coffeeWithReviews.coffee.evaluationTotal, 0.1)

        assertEquals(coffeeWithReviews.reviews.size, 7)

        assertEquals(coffeeWithReviews.reviews[0].availability, 10);
        assertEquals(coffeeWithReviews.reviews[1].taste, 10);
        assertEquals(coffeeWithReviews.reviews[2].comment, "1");
    }

    fun testTestGetName() {
        assertEquals("My coffee", coffeeWithReviews.coffee.name)
    }

    fun testGetPrice() {
        assertEquals(5.99, coffeeWithReviews.coffee.price, 0.01)
    }

    fun testGetStoreToBuyFrom() {
        assertEquals("Amazon", coffeeWithReviews.coffee.storeToBuyFrom)
    }

    fun testGetCoffeeType() {
        assertEquals(CoffeeType.MIXED, coffeeWithReviews.coffee.coffeeType)
    }

    fun testGetQuantity() {
        assertEquals(1.0, coffeeWithReviews.coffee.quantity, 0.01)
    }

    fun testGetStrength() {
        assertEquals(3, coffeeWithReviews.coffee.strength)
    }

    fun testGetAdditionalInformation() {
        assertEquals("Very good", coffeeWithReviews.coffee.additionalInformation)
    }

    fun testGetImage() {
        assertEquals("rocket_coffee", coffeeWithReviews.coffee.image)
    }

    fun testGetId() {
        assertEquals(0, coffeeWithReviews.coffee.coffeeId)
    }
}