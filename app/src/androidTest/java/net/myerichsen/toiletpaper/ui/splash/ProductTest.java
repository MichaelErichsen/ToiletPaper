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
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("deprecation")
@LargeTest
@RunWith(AndroidJUnit4.class)
public class ProductTest extends ToiletPaperTest {
    @Test
    public void productTest() {
        initialLoad();

        drawerPosition(4);

        help("Oversigt over kendte produkter.");

        ViewInteraction textView = onView(
                allOf(withText("Budget"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableRow.class),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Budget")));

        ViewInteraction tableRow = onView(
                childAtPosition(
                        allOf(withId(R.id.productTableLayout),
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0)),
                        1));
        tableRow.perform(scrollTo(), click());

        help("Produktdetaljer.");

        ViewInteraction textView3 = onView(
                allOf(withText("70225"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableRow.class),
                                        1),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("70225")));
    }
}
