package at.tu.graz.coffee.businessLogic

import at.tu.graz.coffee.businessLogic.FilterHelper.Companion.checkReviews
import at.tu.graz.coffee.businessLogic.SortHelper.Companion.sortCoffee
import at.tu.graz.coffee.model.Coffee
import at.tu.graz.coffee.model.CoffeeType
import at.tu.graz.coffee.model.CoffeeType.*
import at.tu.graz.coffee.model.CoffeeWithReviews
import at.tu.graz.coffee.model.Review
import junit.framework.TestCase

class SortHelperTest : TestCase() {
    private var coffeeList: List<CoffeeWithReviews> = listOf(
        CoffeeWithReviews(
            Coffee(
                "Caffe Crema", 9.00, "Supermarket",
                NONE, 1.00, 1, " ",
                "spar_premium_caffe_crema"
            ), listOf(
                Review(4, 4, 4, "Just a comment", 1),
                Review(10, 3, 4, "Another comment", 1)
            )
        ),
        CoffeeWithReviews(
            Coffee(
                "Barista Espresso", 3.50, "Amazon",
                NONE, 1.00, 1, " ",
                "tchibo_barista_espresso"
            ), listOf(
                Review(4, 10, 4, "Just a comment", 1),
                Review(10, 3, 4, "Another comment", 1)
            )
        ),
        CoffeeWithReviews(
            Coffee(
                "Black and White", 5.0, "Billa",
                NONE, 1.00, 1, " ",
                "tchibo_black_and_white"
            ), listOf(
                Review(8, 4, 10, "Just a comment", 1),
                Review(10, 3, 4, "Another comment", 1)
            )
        ),
        CoffeeWithReviews(
            Coffee(
                "Caffe Crema", 9.00, "Spar",
                NONE, 1.00, 1, " ",
                "spar_premium_caffe_crema"
            ), listOf(
                Review(1, 6, 9, "Just a comment", 1),
                Review(10, 3, 4, "Another comment", 1)
            )
        ),
        CoffeeWithReviews(
            Coffee(
                "Barista Espresso", 3.50, "Supermarket",
                NONE, 1.00, 1, " ",
                "tchibo_barista_espresso"
            ), listOf(
                Review(8, 1, 7, "Just a comment", 1),
                Review(10, 3, 4, "Another comment", 1))
        ),
        CoffeeWithReviews(
            Coffee(
                "Black and White", 5.0, "Amazon",
                NONE, 1.00, 1, " ",
                "tchibo_black_and_white"
            ), listOf(
                Review(4, 6, 5, "Just a comment", 1),
                Review(10, 3, 4, "Another comment", 1))
        ),
        CoffeeWithReviews(
            Coffee(
                "Caffe Crema", 9.00, "Billa",
                NONE, 1.00, 1, " ",
                "spar_premium_caffe_crema"
            ), listOf(
                Review(5, 1, 3, "Just a comment", 1),
                Review(10, 3, 4, "Another comment", 1))
        ),
        CoffeeWithReviews(
            Coffee(
                "Barista Espresso", 3.50, "Spar",
                NONE, 1.00, 1, " ",
                "tchibo_barista_espresso"
            ), listOf(
                Review(9, 4, 3, "Just a comment", 1),
                Review(10, 3, 4, "Another comment", 1)
            )
        ),
        CoffeeWithReviews(
            Coffee(
                "Black and White", 5.0, "Supermarket",
                NONE, 1.00, 1, " ",
                "tchibo_black_and_white"
            ), listOf(
                Review(7, 5, 10, "Just a comment", 1),
                Review(10, 3, 4, "Another comment", 1))
        ),
        CoffeeWithReviews(
            Coffee(
                "Black and White", 5.0, "Supermarket",
                NONE, 1.00, 1, " ",
                "tchibo_black_and_white"
            ), listOf(
                Review(8, 2, 5, "Just a comment", 1),
                Review(10, 3, 4, "Another comment", 1)
            )
        )
    )

    fun testSortCoffee() {
        coffeeList.forEach { it.calculateNewEvaluation() }
        val sortedCoffees = sortCoffee(coffeeList)
        var tempValue = Double.MAX_VALUE

        for(coffee in sortedCoffees) {
            assert(coffee.coffee.evaluationTotal <= tempValue)
            tempValue = coffee.coffee.evaluationTotal
        }
    }
}