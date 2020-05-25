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

@SuppressWarnings("deprecation")
@LargeTest
@RunWith(AndroidJUnit4.class)
public class SupplierTest extends ToiletPaperTest {
    @Test
    public void supplierTest() {
        initialLoad();

        drawerPosition(5);
        
        ViewInteraction textView5 = onView(
                allOf(withText("Rema Vejby"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableRow.class),
                                        0),
                                0),
                        isDisplayed()));
        textView5.check(matches(withText("Rema Vejby")));

        help(   "Oversigt over kendte butikker.");

        ViewInteraction tableRow2 = onView(
                childAtPosition(
                        allOf(withId(R.id.supplierTableLayout),
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0)),
                        6));
        tableRow2.perform(scrollTo(), click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.supplierDetailChainEditText), withText("REMA 1000"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.supplierDetailChainTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("REMA 1000")));

        help(  "Detaljer for en butik.");

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.supplierDetailSupplierEditText), withText("Rema Vejby"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.supplierDetailSupplierTextInputLayout),
                                        0),
                                0)));
        textInputEditText.perform(scrollTo(), click());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.supplierDetailSupplierEditText), withText("Rema Vejby"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.supplierDetailSupplierTextInputLayout),
                                        0),
                                0)));
        textInputEditText2.perform(scrollTo(), replaceText("Rema Vejbyxx"));

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.supplierDetailSupplierEditText), withText("Rema Vejbyxx"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.supplierDetailSupplierTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(closeSoftKeyboard());

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withId(R.id.supplierDetailAddBtn), withContentDescription("Tilføj"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1)));
        appCompatImageButton5.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton6 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton6.perform(click());

        ViewInteraction textView8 = onView(
                allOf(withText("Rema Vejbyxx"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableRow.class),
                                        0),
                                0),
                        isDisplayed()));
        textView8.check(matches(withText("Rema Vejbyxx")));

        ViewInteraction tableRow3 = onView(
                childAtPosition(
                        allOf(withId(R.id.supplierTableLayout),
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0)),
                        7));
        tableRow3.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton7 = onView(
                allOf(withId(R.id.supplierDetailDeleteBtn), withContentDescription("Slet"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                2)));
        appCompatImageButton7.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton8 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton8.perform(click());
    }
}
