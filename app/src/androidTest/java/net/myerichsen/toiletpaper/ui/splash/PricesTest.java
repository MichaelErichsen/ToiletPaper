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
public class PricesTest extends ToiletPaperTest {

    // FIXME androidx.test.espresso.NoMatchingViewException: No views in hierarchy found matching:
    //  (with id is <net.myerichsen.toiletpaper:id/title> and with text: is "Indstillinger" and
    //  Child at position 0 in parent Child at position 0 in parent with id is <2131296385> and is
    //  displayed on the screen to the user).
    //  If the target view is not part of the view hierarchy, you may need to use Espresso.onData
    //  to load it from one of the following AdapterViews:androidx.appcompat.widget.AppCompatSpinner
    //  {93cf3dc VFED..CL. ........ 230,0-1080,127 #7f09019a app:id/suppliersSpinner}
    //  - androidx.appcompat.widget.AppCompatSpinner{1c673ba VFED..CL. ........ 851,0-1080,127
    //  #7f0900d6 app:id/layersSpinner}
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

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.itemNoList),
                        childAtPosition(
                                withId(R.id.nav_host_fragment),
                                0)));
        recyclerView3.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withId(R.id.pSearchBrandBtn), withContentDescription("Søg"),
                        childAtPosition(
                                allOf(withId(R.id.fragmentPriceConstraintLayout),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatImageButton5.perform(click());

        help("Oversigt over fundne varemærker.");

        ViewInteraction recyclerView4 = onView(
                allOf(withId(R.id.brandList),
                        childAtPosition(
                                withId(R.id.nav_host_fragment),
                                0)));
        recyclerView4.perform(actionOnItemAtPosition(2, click()));

//        ViewInteraction textInputEditText2 = onView(
//                allOf(withId(R.id.pItemNoEditText), withText("170190"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.pItemNoTextInputLayout),
//                                        0),
//                                0),
//                        isDisplayed()));
//        textInputEditText2.perform(click());
//
//        ViewInteraction textInputEditText3 = onView(
//                allOf(withId(R.id.pItemNoEditText), withText("170190"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.pItemNoTextInputLayout),
//                                        0),
//                                0),
//                        isDisplayed()));
//        textInputEditText3.perform(click());
//
//        ViewInteraction textInputEditText4 = onView(
//                allOf(withId(R.id.pItemNoEditText), withText("170190"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.pItemNoTextInputLayout),
//                                        0),
//                                0),
//                        isDisplayed()));
//        textInputEditText4.perform(replaceText("test170190"));
//
//        ViewInteraction textInputEditText5 = onView(
//                allOf(withId(R.id.pItemNoEditText), withText("test170190"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.pItemNoTextInputLayout),
//                                        0),
//                                0),
//                        isDisplayed()));
//        textInputEditText5.perform(closeSoftKeyboard());
//
//        ViewInteraction textInputEditText6 = onView(
//                allOf(withId(R.id.pItemNoEditText), withText("test170190"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.pItemNoTextInputLayout),
//                                        0),
//                                0),
//                        isDisplayed()));
//        textInputEditText6.perform(click());
//
//        ViewInteraction textInputEditText7 = onView(
//                allOf(withId(R.id.pItemNoEditText), withText("test170190"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.pItemNoTextInputLayout),
//                                        0),
//                                0),
//                        isDisplayed()));
//        textInputEditText7.perform(click());
//
//        ViewInteraction textInputEditText8 = onView(
//                allOf(withId(R.id.pItemNoEditText), withText("test170190"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.pItemNoTextInputLayout),
//                                        0),
//                                0),
//                        isDisplayed()));
//        textInputEditText8.perform(click());

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.pItemNoEditText), withText("170190"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.pItemNoTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText9.perform(replaceText("test"));

        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.pItemNoEditText), withText("test"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.pItemNoTextInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText10.perform(closeSoftKeyboard());

        ViewInteraction appCompatImageButton6 = onView(
                allOf(withId(R.id.priceSelectionBtn), withContentDescription("Vælg"),
                        childAtPosition(
                                allOf(withId(R.id.fragmentPriceConstraintLayout),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatImageButton6.perform(click());

        help("Oversigt over prisudviklingen for dette produkt");

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.plPackagePrice), withText("34.95"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        textView7.check(matches(withText("34.95")));

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

        help("Prisudvikling, grafisk.");
    }
}
