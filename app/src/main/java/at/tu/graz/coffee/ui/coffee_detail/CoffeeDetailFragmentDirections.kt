package at.tu.graz.coffee.ui.coffee_detail

import android.os.Bundle
import androidx.navigation.NavDirections
import at.tu.graz.coffee.R

class CoffeeDetailFragmentDirections private constructor() {
    private data class ActionOpenDetails(
        val coffeeId: Int = -1
    ) : NavDirections {
        override fun getActionId(): Int = R.id.commentFragment

        override fun getArguments(): Bundle {
            val result = Bundle()
            result.putInt("coffeeId", this.coffeeId)
            return result
        }
    }

    companion object {
        fun actionOpenDetails(coffeeId: Int = -1): NavDirections = ActionOpenDetails(coffeeId)
    }
}