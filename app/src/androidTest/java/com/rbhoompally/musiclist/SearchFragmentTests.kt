package com.rbhoompally.musiclist

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.rbhoompally.musiclist.ui.SearchFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchFragmentTests {

    @Test
    fun testEventFragment() {
        val scenario = launchFragmentInContainer<SearchFragment>()
        onView(withId(R.id.search_bar))
            .perform(typeText("test"), closeSoftKeyboard())
        onView(withId(R.id.close))
            .perform(click())
        onView(withId(R.id.search_bar))
            .check(matches(withText("")))
    }
}