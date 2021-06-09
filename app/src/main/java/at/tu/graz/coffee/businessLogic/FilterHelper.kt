package at.tu.graz.coffee.businessLogic

import at.tu.graz.coffee.model.CoffeeWithReviews
import at.tu.graz.coffee.model.Review

class FilterHelper {
    companion object {
        fun filterCoffee(
            coffees: List<CoffeeWithReviews>,
            rangeTotal: List<Float>,
            rangeTaste: List<Float>,
            rangeCost: List<Float>,
            rangeAvailability: List<Float>,
            selectedStore: String,
            searchText: String
        ): List<Int> {
            val filteredCoffees: MutableList<Int> = mutableListOf()

            coffees.forEach {
                if (it.coffee.evaluationTotal >= rangeTotal[0] && it.coffee.evaluationTotal <= rangeTotal[1] &&
                    it.coffee.evaluationTaste >= rangeTaste[0] && it.coffee.evaluationTaste <= rangeTaste[1] &&
                    it.coffee.evaluationCost >= rangeCost[0] && it.coffee.evaluationCost <= rangeCost[1] &&
                    it.coffee.evaluationAvailability >= rangeAvailability[0] &&
                    it.coffee.evaluationAvailability <= rangeAvailability[1] &&
                    (it.coffee.storeToBuyFrom == selectedStore || selectedStore == "") &&
                    (it.coffee.name.contains(searchText, ignoreCase = true) || searchText == "" ||
                            checkReviews(it.reviews, searchText))
                ) {

                    filteredCoffees.add(it.coffee.coffeeId)
                }
            }
            return filteredCoffees
        }

        fun checkReviews(reviews: List<Review>, searchText: String): Boolean {
            reviews.forEach {
                if (it.comment.contains(searchText, ignoreCase = true)) {
                    return true
                }
            }
            return false
        }
    }


}