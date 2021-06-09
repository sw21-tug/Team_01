package at.tu.graz.coffee.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coffee")
data class Coffee(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "price") var price: Double,
    @ColumnInfo(name = "store_to_buy_from") var storeToBuyFrom: String,
    @ColumnInfo(name = "coffee_type") var coffeeType: CoffeeType,
    @ColumnInfo(name = "quantity") var quantity: Double = -1.0,
    @ColumnInfo(name = "strength") var strength: Int = -1,
    @ColumnInfo(name = "additional_information") var additionalInformation: String = "",
    @ColumnInfo(name = "image") var image: String
) {
    @PrimaryKey(autoGenerate = true) var coffeeId: Int = 0
    var evaluationTotal: Double = 0.0
    var evaluationTaste: Double = 0.0
    var evaluationCost: Double = 0.0
    var evaluationAvailability: Double = 0.0
}