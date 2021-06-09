package at.tu.graz.coffee


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class DetailPageTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun detailPageTest() {
        val recyclerView = onView(
            allOf(
                withId(R.id.home_listview),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val textView = onView(
            allOf(
                withId(R.id.lbl_total_evaluation), withText("Total:"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Total:")))

        val textView2 = onView(
            allOf(
                withId(R.id.lbl_taste_evaluation), withText("Taste:"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Taste:")))

        val textView3 = onView(
            allOf(
                withId(R.id.lbl_cost_evaluation), withText("Cost:"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Cost:")))

        val textView4 = onView(
            allOf(
                withId(R.id.lbl_availability_evaluation), withText("Availability:"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Availability:")))

        val textView5 = onView(
            allOf(
                withId(R.id.lbl_coffee_type), withText("Coffee type:"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("Coffee type:")))

        val textView6 = onView(
            allOf(
                withId(R.id.lbl_store_to_buy_from), withText("Available at:"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("Available at:")))

        val textView7 = onView(
            allOf(
                withId(R.id.lbl_price), withText("Price:"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView7.check(matches(withText("Price:")))

        val textView8 = onView(
            allOf(
                withId(R.id.lbl_quantity), withText("Quantity:"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView8.check(matches(withText("Quantity:")))

        val textView9 = onView(
            allOf(
                withId(R.id.lbl_strength), withText("Strength:"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView9.check(matches(withText("Strength:")))

        val textView10 = onView(
            allOf(
                withId(R.id.lbl_additional_information), withText("Additional information:"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView10.check(matches(withText("Additional information:")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
