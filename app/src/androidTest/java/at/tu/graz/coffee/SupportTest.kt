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
class SupportTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun supportTest() {
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
allOf(withId(R.id.nav_support),
childAtPosition(
allOf(withId(R.id.design_navigation_view),
childAtPosition(
withId(R.id.nav_view),
0)),
4),
isDisplayed()))
        navigationMenuItemView.perform(click())
        
        val appCompatEditText = onView(
allOf(withId(R.id.txt_supportMsg),
childAtPosition(
childAtPosition(
withId(R.id.nav_host_fragment),
0),
1),
isDisplayed()))
        appCompatEditText.perform(replaceText("Test"), closeSoftKeyboard())
        
        val textView = onView(
allOf(withId(R.id.textView2), withText("Your Message:"),
withParent(withParent(withId(R.id.nav_host_fragment))),
isDisplayed()))
        textView.check(matches(withText("Your Message:")))
        
        val radioButton = onView(
allOf(withId(R.id.rdbtn_Owner), withText("Owner"),
withParent(allOf(withId(R.id.radio_group),
withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java)))),
isDisplayed()))
        radioButton.check(matches(isDisplayed()))
        
        val radioButton2 = onView(
allOf(withId(R.id.rdbtn_Support), withText("Support"),
withParent(allOf(withId(R.id.radio_group),
withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java)))),
isDisplayed()))
        radioButton2.check(matches(isDisplayed()))
        
        val editText = onView(
allOf(withId(R.id.txt_supportMsg), withText("Test"),
withParent(withParent(withId(R.id.nav_host_fragment))),
isDisplayed()))
        editText.check(matches(withText("Test")))
        
        val editText2 = onView(
allOf(withId(R.id.txt_supportMsg), withText("Test"),
withParent(withParent(withId(R.id.nav_host_fragment))),
isDisplayed()))
        editText2.check(matches(withText("Test")))
        
        val materialButton = onView(
allOf(withId(R.id.btn_sendEmail), withText("Send Message"),
childAtPosition(
childAtPosition(
withId(R.id.nav_host_fragment),
0),
3),
isDisplayed()))
        materialButton.perform(click())
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
