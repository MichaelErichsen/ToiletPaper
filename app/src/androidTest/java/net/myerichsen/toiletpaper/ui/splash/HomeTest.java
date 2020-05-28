package net.myerichsen.toiletpaper.ui.splash;

import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import net.myerichsen.toiletpaper.R;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

/**
 * Unit test of home screen
 */
@SuppressWarnings("deprecation")
@LargeTest
@RunWith(AndroidJUnit4.class)
public class HomeTest extends ToiletPaperTest {

    @Test
    public void homeTest() {
        initialLoad();
        homeHelp();
        homeItemNoSearchSingleResult();
        homeItemNoSearchSeveralResults();
        homeBrandTest();
    }

    private void homeHelp() {
        help("Hovedbillede for programmet.");
    }

    private void homeItemNoSearchSeveralResults() {
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.itemNoEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.pItemNoTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("ww"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.searchItemNoBtn), withContentDescription("Søg"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                2)));
        appCompatImageButton2.perform(scrollTo(), click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.itemNoList),
                        childAtPosition(
                                withId(R.id.nav_host_fragment),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(2, click()));

        ViewInteraction editText = onView(
                allOf(withId(R.id.itemNoEditText), withText("WW-101012"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.pItemNoTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("WW-101012")));
    }

    private void homeItemNoSearchSingleResult() {
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.itemNoEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.pItemNoTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("7"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.searchItemNoBtn), withContentDescription("Søg"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                2)));
        appCompatImageButton2.perform(scrollTo(), click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.itemNoEditText), withText("7311041080306"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.pItemNoTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("7311041080306")));
    }

    public void homeBrandTest() {
        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.brandEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.brandTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("i"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.searchBrandBtn), withContentDescription("Søg"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1)));
        appCompatImageButton3.perform(scrollTo(), click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.brandEditText), withText("Irma Tusindfryd Toiletpapir"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.brandTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("Irma Tusindfryd Toiletpapir")));

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.brandEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.brandTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText8.perform(replaceText("l"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton6 = onView(
                allOf(withId(R.id.searchBrandBtn), withContentDescription("Søg"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1)));
        appCompatImageButton6.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.fbSupplier), withText("nemlig.com"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("nemlig.com")));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.brandList),
                        childAtPosition(
                                withId(R.id.nav_host_fragment),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.brandEditText), withText("Lotus Comfort"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.brandTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        editText2.check(matches(withText("Lotus Comfort")));

        ViewInteraction appCompatImageButton7 = onView(
                allOf(withId(R.id.calculateBtn), withContentDescription("Beregn"),
                        childAtPosition(
                                allOf(withId(R.id.buttonLinearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                17)),
                                0)));
        appCompatImageButton7.perform(scrollTo(), click());

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.sheetPriceEditText), withText("0.017741935"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.sheetPriceTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        editText3.check(matches(withText("0.017741935")));

        ViewInteraction appCompatImageButton8 = onView(
                allOf(withId(R.id.saveBtn), withContentDescription("Gem"),
                        childAtPosition(
                                allOf(withId(R.id.buttonLinearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                17)),
                                1)));
        appCompatImageButton8.perform(scrollTo(), click());
    }

}
