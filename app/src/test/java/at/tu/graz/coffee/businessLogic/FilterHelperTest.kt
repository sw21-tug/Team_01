package at.tu.graz.coffee.businessLogic

import at.tu.graz.coffee.businessLogic.FilterHelper.Companion.checkReviews
import at.tu.graz.coffee.businessLogic.FilterHelper.Companion.filterCoffee
import at.tu.graz.coffee.model.Coffee
import at.tu.graz.coffee.model.CoffeeType
import at.tu.graz.coffee.model.CoffeeType.*
import at.tu.graz.coffee.model.CoffeeWithReviews
import at.tu.graz.coffee.model.Review
import junit.framework.TestCase

class FilterHelperTest : TestCase() {
    private var coffeeList: List<CoffeeWithReviews> = listOf(
        CoffeeWithReviews(
            Coffee(
                "Caffe Crema", 9.00, "Supermarket",
                NONE, 1.00, 1, " ",
                "spar_premium_caffe_crema"
            ), listOf(
                Review(1, 2, 10, "Just a comment", 1),
                Review(10, 3, 4, "Another comment", 1),
                Review(6, 5, 5, "1", 1),
                Review(1, 1, 1, "2", 1),
                Review(6, 6, 2, "3", 1),
                Review(1, 7, 5, "4", 1),
                Review(3, 4, 7, "5", 1)
            )
        ),
        CoffeeWithReviews(
            Coffee(
                "Barista Espresso", 3.50, "Amazon",
                NONE, 1.00, 1, " ",
                "tchibo_barista_espresso"
            ), listOf()
        ),
        CoffeeWithReviews(
            Coffee(
                "Black and White", 5.0, "Billa",
                NONE, 1.00, 1, " ",
                "tchibo_black_and_white"
            ), listOf()
        ),
        CoffeeWithReviews(
            Coffee(
                "Caffe Crema", 9.00, "Spar",
                NONE, 1.00, 1, " ",
                "spar_premium_caffe_crema"
            ), listOf()
        ),
        CoffeeWithReviews(
            Coffee(
                "Barista Espresso", 3.50, "Supermarket",
                NONE, 1.00, 1, " ",
                "tchibo_barista_espresso"
            ), listOf()
        ),
        CoffeeWithReviews(
            Coffee(
                "Black and White", 5.0, "Amazon",
                NONE, 1.00, 1, " ",
                "tchibo_black_and_white"
            ), listOf()
        ),
        CoffeeWithReviews(
            Coffee(
                "Caffe Crema", 9.00, "Billa",
                NONE, 1.00, 1, " ",
                "spar_premium_caffe_crema"
            ), listOf()
        ),
        CoffeeWithReviews(
            Coffee(
                "Barista Espresso", 3.50, "Spar",
                NONE, 1.00, 1, " ",
                "tchibo_barista_espresso"
            ), listOf()
        ),
        CoffeeWithReviews(
            Coffee(
                "Black and White", 5.0, "Supermarket",
                NONE, 1.00, 1, " ",
                "tchibo_black_and_white"
            ), listOf()
        ),
        CoffeeWithReviews(
            Coffee(
                "Black and White", 5.0, "Supermarket",
                NONE, 1.00, 1, " ",
                "tchibo_black_and_white"
            ), listOf()
        )
    )

    fun testFilterCoffeeTotal() {
        val rangeTotal: List<Float> = listOf(3f, 7f)
        val rangeTaste: List<Float> = listOf(0f, 10f)
        val rangeCost: List<Float> = listOf(0f, 10f)
        val rangeAvailability: List<Float> = listOf(0f, 10f)
        val selectedStore = ""
        val searchText = ""
        val filteredCoffees = filterCoffee(
            coffeeList, rangeTotal, rangeTaste, rangeCost,
            rangeAvailability, selectedStore, searchText
        )

        for(coffeeId in filteredCoffees) {
            for (coffee in coffeeList) {
                if(coffeeId == coffee.coffee.coffeeId) {
                    assertEquals(true, coffee.coffee.evaluationTotal in 3.0..7.0)
                }
            }
        }
    }

    fun testCheckReviewReturnsTrue() {
        assertEquals(true, checkReviews(coffeeList[0].reviews, "1"))
    }

    fun testCheckReviewReturnsFalse() {
        assertEquals(false, checkReviews(coffeeList[0].reviews, "1244"))
    }
}