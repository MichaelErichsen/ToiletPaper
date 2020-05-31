/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

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

@SuppressWarnings("deprecation")
@LargeTest
@RunWith(AndroidJUnit4.class)
public class HomeTest extends ToiletPaperTest {

    @Test
    public void homeTest() {
        initialLoad();

        help("Hovedbillede for programmet.");

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.itemNoEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.pItemNoTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("731104108"), closeSoftKeyboard());

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

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.scanBtn), withContentDescription("Scan"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1)));
        appCompatImageButton3.perform(scrollTo(), click());

        help("Scan en stregkode eller QR-kode.");

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withId(R.id.searchBrandBtn), withContentDescription("Søg"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1)));
        appCompatImageButton5.perform(scrollTo(), click());

        drawerPosition(1);

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.brandEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.brandTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("la"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton7 = onView(
                allOf(withId(R.id.searchBrandBtn), withContentDescription("Søg"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1)));
        appCompatImageButton7.perform(scrollTo(), click());

        help("Oversigt over fundne varemærker.");

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.brandList),
                        childAtPosition(
                                withId(R.id.nav_host_fragment),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.brandEditText), withText("Lambi Classic 12"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.brandTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        editText2.check(matches(withText("Lambi Classic 12")));

        drawerPosition(1);

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.itemNoEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.pItemNoTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("7"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton9 = onView(
                allOf(withId(R.id.searchItemNoBtn), withContentDescription("Søg"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                2)));
        appCompatImageButton9.perform(scrollTo(), click());

        help("Oversigt over fundne varenumre.");

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.itemNoList),
                        childAtPosition(
                                withId(R.id.nav_host_fragment),
                                0)));
        recyclerView3.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.itemNoEditText), withText("75532"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.pItemNoTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        editText3.check(matches(withText("75532")));

        drawerPosition(1);

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.brandEditText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.brandTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("budget"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton11 = onView(
                allOf(withId(R.id.searchBrandBtn), withContentDescription("Søg"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1)));
        appCompatImageButton11.perform(scrollTo(), click());

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.itemNoEditText), withText("70225"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.pItemNoTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        editText4.check(matches(withText("70225")));

        ViewInteraction appCompatImageButton12 = onView(
                allOf(withId(R.id.calculateBtn), withContentDescription("Beregn"),
                        childAtPosition(
                                allOf(withId(R.id.buttonLinearLayout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                18)),
                                0)));
        appCompatImageButton12.perform(scrollTo(), click());

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.rollWeightEditText), withText("100.0"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText5.check(matches(withText("100.0")));
    }
}
