package at.tu.graz.coffee

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import at.tu.graz.coffee.database.AppDatabase
import at.tu.graz.coffee.database.CoffeeRepository
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class CoffeeApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())
    val coffeeRepository by lazy { CoffeeRepository(
        AppDatabase.getCoffeeInstance(this, applicationScope),
        AppDatabase.getReviewInstance(this, applicationScope)) }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var ctx: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
        Fresco.initialize(ctx)
    }
}