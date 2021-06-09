package at.tu.graz.coffee.businessLogic

import android.content.Context
import at.tu.graz.coffee.R
import at.tu.graz.coffee.model.CoffeeType

class CoffeeTypeHelper {
    companion object {
        fun getCoffeeTypeStringList(context: Context): List<String> {
            return listOf(
                context.getString(R.string.none),
                context.getString(R.string.robusta),
                context.getString(R.string.arabica),
                context.getString(R.string.mixed)
            )
        }

        fun getCoffeeTypeName(context: Context, coffeeType: CoffeeType): String {
            return when (coffeeType) {
                CoffeeType.NONE -> context.getString(R.string.none)
                CoffeeType.ARABICA -> context.getString(R.string.arabica)
                CoffeeType.MIXED -> context.getString(R.string.mixed)
                CoffeeType.ROBUSTA -> context.getString(R.string.robusta)
            }
        }

        fun getEnumTypeFromCoffeeTypeName(context: Context, coffeeTypeName: String): CoffeeType {
            return when (coffeeTypeName) {
                context.getString(R.string.none) -> CoffeeType.NONE
                context.getString(R.string.arabica) -> CoffeeType.ARABICA
                context.getString(R.string.mixed) -> CoffeeType.MIXED
                context.getString(R.string.robusta) -> CoffeeType.ROBUSTA
                else -> CoffeeType.NONE
            }
        }
    }
}