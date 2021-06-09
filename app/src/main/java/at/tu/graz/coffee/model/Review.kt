package at.tu.graz.coffee.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Review(
    @ColumnInfo(name = "taste") var taste:Int=0,
    @ColumnInfo(name = "cost") var cost:Int=0,
    @ColumnInfo(name = "availability") var availability:Int=0,
    @ColumnInfo(name = "comment") var comment:String = "",
    @ColumnInfo(name = "coffeeCreatorId") var coffeeCreatorId: Int
) {
    @PrimaryKey(autoGenerate = true) var reviewId: Int = 0
}
