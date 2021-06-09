package at.tu.graz.coffee


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
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
class AddCoffeeTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun addCoffeeTest() {
        val appCompatImageButton = onView(
            allOf(withContentDescription("Open navigation drawer"),
                childAtPosition(
                    allOf(withId(R.id.toolbar),
                        childAtPosition(
                            withClassName(`is`("com.google.android.material.appbar.AppBarLayout")),
                            0)),
                    1),
                isDisplayed()))
        appCompatImageButton.perform(click())

        val navigationMenuItemView = onView(
            allOf(withId(R.id.nav_addCoffee),
                childAtPosition(
                    allOf(withId(R.id.design_navigation_view),
                        childAtPosition(
                            withId(R.id.nav_view),
                            0)),
                    2),
                isDisplayed()))
        navigationMenuItemView.perform(click())

        val appCompatEditText = onView(
            allOf(withId(R.id.coffee_name),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0),
                    1)))
        appCompatEditText.perform(scrollTo(), replaceText("coffee"), closeSoftKeyboard())

        val editText = onView(
            allOf(withId(R.id.coffee_name), withText("coffee"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()))
        editText.check(matches(withText("coffee")))

        val appCompatEditText2 = onView(
            allOf(withId(R.id.coffee_qty),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0),
                    6)))
        appCompatEditText2.perform(scrollTo(), replaceText("5"), closeSoftKeyboard())

        val editText2 = onView(
            allOf(withId(R.id.coffee_qty), withText("5"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()))
        editText2.check(matches(withText("5")))

        val materialButton = onView(
            allOf(withId(R.id.button_addCoffee), withText("Add Coffee"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0),
                    12)))
        materialButton.perform(scrollTo(), click())

        val textView = onView(
            allOf(withId(R.id.mandatory_field), withText("Mandatory Field missing"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()))
        textView.check(matches(withText("Mandatory Field missing")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int): Matcher<View> {

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
