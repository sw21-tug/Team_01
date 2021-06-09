package at.tu.graz.coffee.model

import androidx.room.Embedded
import androidx.room.Relation


data class CoffeeWithReviews (
    @Embedded val coffee: Coffee,
    @Relation(
        parentColumn = "coffeeId",
        entityColumn = "coffeeCreatorId",
        entity = Review::class
    )
    val reviews: List<Review>

){
    fun calculateNewEvaluation() {
        var evaluationTaste = 0.0
        var evaluationCost = 0.0
        var evaluationAvailability = 0.0

        if(reviews.isEmpty()) {
            return
        }

        for (review in reviews) {
            evaluationTaste += review.taste
            evaluationCost += review.cost
            evaluationAvailability += review.availability
        }

        coffee.evaluationTaste = evaluationTaste / reviews.size
        coffee.evaluationCost = evaluationCost / reviews.size
        coffee.evaluationAvailability = evaluationAvailability / reviews.size

        coffee.evaluationTotal =
            (coffee.evaluationTaste + coffee.evaluationCost + coffee.evaluationAvailability) / 3.0

    }
}