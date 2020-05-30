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
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("deprecation")
@LargeTest
@RunWith(AndroidJUnit4.class)
public class ProductTest extends ToiletPaperTest {

    // FIXME androidx.test.espresso.NoMatchingViewException: No views in hierarchy found matching:
    //  (with id is <net.myerichsen.toiletpaper:id/title> and with text: is "Indstillinger" and
    //  Child at position 0 in parent Child at position 0 in parent with id is <2131296385> and is
    //  displayed on the screen to the user)
    //  If the target view is not part of the view hierarchy, you may need to use Espresso.onData to
    //  load it from one of the following AdapterViews:androidx.appcompat.widget.AppCompatSpinner
    //  {810bcb0 VFED..CL. ........ 230,0-1080,127 #7f09019a app:id/suppliersSpinner}
    //  - androidx.appcompat.widget.AppCompatSpinner{b9b5aae VFED..CL. ........ 851,0-1080,127
    //  #7f0900d6 app:id/layersSpinner}
    @Test
    public void productTest() {
        initialLoad();

        drawerPosition(4);

        help("Oversigt over kendte produkter.");

        ViewInteraction textView2 = onView(
                allOf(withText("First Price Toiletpapir 2-lags"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableRow.class),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("First Price Toiletpapir 2-lags")));

        ViewInteraction tableRow = onView(
                childAtPosition(
                        allOf(withId(R.id.productTableLayout),
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0)),
                        1));
        tableRow.perform(scrollTo(), click());

        help("Produktdetaljer.");

        ViewInteraction textView4 = onView(
                allOf(withText("7311041080306"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableRow.class),
                                        1),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("7311041080306")));

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
    }

}
