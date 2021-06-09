package at.tu.graz.coffee.businessLogic

import at.tu.graz.coffee.model.CoffeeWithReviews

class SortHelper {
    companion object {
        fun sortCoffee(
                coffees: List<CoffeeWithReviews>
        ): List<CoffeeWithReviews> {
            return coffees.sortedByDescending { it.coffee.evaluationTotal }
        }
    }
}