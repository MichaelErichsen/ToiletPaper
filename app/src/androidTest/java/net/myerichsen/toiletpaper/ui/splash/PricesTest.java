/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

package net.myerichsen.toiletpaper.ui.splash;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import net.myerichsen.toiletpaper.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("deprecation")
@LargeTest
@RunWith(AndroidJUnit4.class)
public class PricesTest extends ToiletPaperTest {

    @Test
    public void pricesTest() {
        initialLoad();

        drawerPosition(3);

        help("Valgbillede for prisudvikling.");

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.pItemNoEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.pItemNoTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("test"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.pSearchItemNoBtn), withContentDescription("Søg"),
                        childAtPosition(
                                allOf(withId(R.id.fragmentPriceConstraintLayout),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        help("Oversigt over fundne varenumre.");

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.itemNoList),
                        childAtPosition(
                                withId(R.id.nav_host_fragment),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withId(R.id.pSearchBrandBtn), withContentDescription("Søg"),
                        childAtPosition(
                                allOf(withId(R.id.fragmentPriceConstraintLayout),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        help("Oversigt over fundne varemærker.");

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.brandList),
                        childAtPosition(
                                withId(R.id.nav_host_fragment),
                                0)));
        recyclerView3.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withId(R.id.priceSelectionBtn), withContentDescription("Vælg"),
                        childAtPosition(
                                allOf(withId(R.id.fragmentPriceConstraintLayout),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatImageButton5.perform(click());

        help("Oversigt over prisudviklingen for dette produkt");

        ViewInteraction recyclerView4 = onView(
                allOf(withId(R.id.list),
                        childAtPosition(
                                withId(R.id.nav_host_fragment),
                                0)));
        recyclerView4.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatImageButton6 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton6.perform(click());

        ViewInteraction appCompatImageButton7 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton7.perform(click());

        ViewInteraction appCompatImageButton8 = onView(
                allOf(withId(R.id.graphSelectionBtn), withContentDescription("Graf"),
                        childAtPosition(
                                allOf(withId(R.id.fragmentPriceConstraintLayout),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatImageButton8.perform(click());

        ViewInteraction view = onView(
                allOf(withId(R.id.priceGraph),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                2),
                        isDisplayed()));
        view.check(matches(isDisplayed()));

        help("Prisudvikling, grafisk.");
    }
}
