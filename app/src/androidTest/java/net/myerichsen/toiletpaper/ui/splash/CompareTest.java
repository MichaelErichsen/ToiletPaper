package net.myerichsen.toiletpaper.ui.splash;

import android.view.View;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import net.myerichsen.toiletpaper.R;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

/**
 * Copyright (c) 2020. Michael Erichsen.
 * <p>
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

@SuppressWarnings("deprecation")
@LargeTest
@RunWith(AndroidJUnit4.class)
public class CompareTest extends ToiletPaperTest {

    @Test
    public void compareTest() {
        initialLoad();

        drawerPosition(2);

        help("Sammenlign.");

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.filterSpinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1)));
        appCompatSpinner.perform(scrollTo(), click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(6);
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.filterSpinner),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1)));
        appCompatSpinner2.perform(scrollTo(), click());

        DataInteraction appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(0);
        appCompatCheckedTextView2.perform(click());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.radioButton1), withText("Papirvægt (g pr. m2)"),
                        childAtPosition(
                                allOf(withId(R.id.sortKeyRadioGroup),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                4)),
                                0)));
        appCompatRadioButton.perform(scrollTo(), click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.executeCompareTextView), withText("\n\nKlik for at se sorteret liste"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                6)));
        appCompatTextView2.perform(scrollTo(), click());

        help(  "Sorteret produktliste.");

        ViewInteraction tableRow = onView(
                childAtPosition(
                        allOf(withId(R.id.compareTableLayout),
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0)),
                        5));
        tableRow.perform(scrollTo(), click());

        help(    "Produktdetaljer.");

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withText("48.0"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableRow.class),
                                        0),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("48.0")));

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction appCompatRadioButton2 = onView(
                allOf(withId(R.id.radioButton2), withText("Kilopris"),
                        childAtPosition(
                                allOf(withId(R.id.sortKeyRadioGroup),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                4)),
                                1)));
        appCompatRadioButton2.perform(scrollTo(), click());

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.executeCompareTextView), withText("\n\nKlik for at se sorteret liste"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                6)));
        appCompatTextView3.perform(scrollTo(), click());

        help(   "Sorteret produktliste.");

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.clBrand), withText("Tork Advanced Extra Soft T4"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView6.check(matches(withText("Tork Advanced Extra Soft T4")));

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton5.perform(click());

        ViewInteraction appCompatRadioButton3 = onView(
                allOf(withId(R.id.radioButton3), withText("Meterpris"),
                        childAtPosition(
                                allOf(withId(R.id.sortKeyRadioGroup),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                4)),
                                2)));
        appCompatRadioButton3.perform(scrollTo(), click());

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(R.id.executeCompareTextView), withText("\n\nKlik for at se sorteret liste"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                6)));
        appCompatTextView4.perform(scrollTo(), click());

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.clKiloPrice), withText("10.93"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        textView7.check(matches(withText("10.93")));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.list),
                        childAtPosition(
                                withId(R.id.nav_host_fragment),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction textView8 = onView(
                allOf(withText("5705830002242"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableRow.class),
                                        1),
                                0),
                        isDisplayed()));
        textView8.check(matches(withText("5705830002242")));

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

        ViewInteraction appCompatImageButton7 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton7.perform(click());

        ViewInteraction appCompatRadioButton4 = onView(
                allOf(withId(R.id.radioButton4), withText("Pris pr. ark"),
                        childAtPosition(
                                allOf(withId(R.id.sortKeyRadioGroup),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                4)),
                                3)));
        appCompatRadioButton4.perform(scrollTo(), click());

        ViewInteraction appCompatTextView5 = onView(
                allOf(withId(R.id.executeCompareTextView), withText("\n\nKlik for at se sorteret liste"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                6)));
        appCompatTextView5.perform(scrollTo(), click());

        ViewInteraction textView9 = onView(
                allOf(withText("0.48433"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableRow.class),
                                        0),
                                0),
                        isDisplayed()));
        textView9.check(matches(withText("0.48433")));
    }
}
