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

//    @Rule
//    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);
//
//    @Rule
//    public GrantPermissionRule mGrantPermissionRule =
//            GrantPermissionRule.grant(
//                    "android.permission.CAMERA");

    @Test
    public void homeTest() {
        initialLoad();

//        ViewInteraction overflowMenuButton = onView(
//                allOf(withContentDescription("More options"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.toolbar),
//                                        2),
//                                1),
//                        isDisplayed()));
//        overflowMenuButton.perform(click());
//
//        ViewInteraction appCompatTextView = onView(
//                allOf(withId(R.id.title), withText("Indstillinger"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.content),
//                                        0),
//                                0),
//                        isDisplayed()));
//        appCompatTextView.perform(click());
//
//        ViewInteraction recyclerView = onView(
//                allOf(withId(R.id.recycler_view),
//                        childAtPosition(
//                                withId(android.R.id.list_container),
//                                0)));
//        recyclerView.perform(actionOnItemAtPosition(1, click()));
//
//        ViewInteraction appCompatImageButton = onView(
//                allOf(withContentDescription("Navigate up"),
//                        childAtPosition(
//                                allOf(withId(R.id.action_bar),
//                                        childAtPosition(
//                                                withId(R.id.action_bar_container),
//                                                0)),
//                                1),
//                        isDisplayed()));
//        appCompatImageButton.perform(click());

        help("Hovedbillede for programmet.");

//        ViewInteraction actionMenuItemView = onView(
//                allOf(withId(R.id.fragment_help), withContentDescription("Hjælp"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.toolbar),
//                                        2),
//                                0),
//                        isDisplayed()));
//        actionMenuItemView.perform(click());
//
//        ViewInteraction textView = onView(
//                allOf(withId(R.id.HelpDialogTextView), withText("Hovedbillede for programmet.\n Indtast alle kendte data og klik på beregn nederst på billedet.\n Varenummer kan scannes eller slås op i databasen.\n Varemærke kan slås op i databasen.\n Hvis varenummer eller varemærke ikke er komplet eller entydigt, vises et valgbillede. Hvis det er fundet og entydigt, vises alle data fra databasen.\n Manglende værdier beregnes ved tryk på beregningsknappen. Beregnede værdier markeres som sådan. Ved indtastning fjernes markeringen.\n De indtastede data gemmes med gemmeknappen nederst.\n Der kan foretages opslag på varemærket hos Google med Googleknappen.\n Der kan foretages opslag på toiletpapir generelt hos Pricerunner med Pricerunnerknappen.\n Antallet af felter på billedet styres med indstilingen \"Avanceret inddataformat\"."),
//                        childAtPosition(
//                                childAtPosition(
//                                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
//                                        0),
//                                1),
//                        isDisplayed()));
//        textView.check(matches(withText("Hovedbillede for programmet.  Indtast alle kendte data og klik på beregn nederst på billedet.  Varenummer kan scannes eller slås op i databasen.  Varemærke kan slås op i databasen.  Hvis varenummer eller varemærke ikke er komplet eller entydigt, vises et valgbillede. Hvis det er fundet og entydigt, vises alle data fra databasen.  Manglende værdier beregnes ved tryk på beregningsknappen. Beregnede værdier markeres som sådan. Ved indtastning fjernes markeringen.  De indtastede data gemmes med gemmeknappen nederst.  Der kan foretages opslag på varemærket hos Google med Googleknappen.  Der kan foretages opslag på toiletpapir generelt hos Pricerunner med Pricerunnerknappen.  Antallet af felter på billedet styres med indstilingen \"Avanceret inddataformat\".")));
//
//        ViewInteraction appCompatButton = onView(
//                allOf(withId(R.id.helpDialogButtonOK), withText("OK"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                2)));
//        appCompatButton.perform(scrollTo(), click());

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

//        ViewInteraction actionMenuItemView2 = onView(
//                allOf(withId(R.id.fragment_help), withContentDescription("Hjælp"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.toolbar),
//                                        2),
//                                0),
//                        isDisplayed()));
//        actionMenuItemView2.perform(click());
//
//        ViewInteraction textView2 = onView(
//                allOf(withId(R.id.HelpDialogTextView), withText("Scan en stregkode eller QR-kode.\n Telefonen vil bippe, og koden vil blive vist nedenfor.\n Koden indsættes i hovedbilledet, og der søges efter denne i databasen."),
//                        childAtPosition(
//                                childAtPosition(
//                                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
//                                        0),
//                                1),
//                        isDisplayed()));
//        textView2.check(matches(withText("Scan en stregkode eller QR-kode.  Telefonen vil bippe, og koden vil blive vist nedenfor.  Koden indsættes i hovedbilledet, og der søges efter denne i databasen.")));
//
//        ViewInteraction appCompatButton2 = onView(
//                allOf(withId(R.id.helpDialogButtonOK), withText("OK"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                2)));
//        appCompatButton2.perform(scrollTo(), click());

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

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withId(R.id.searchBrandBtn), withContentDescription("Søg"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1)));
        appCompatImageButton5.perform(scrollTo(), click());

        drawerPosition(1);

//        ViewInteraction appCompatImageButton6 = onView(
//                allOf(withContentDescription("Open navigation drawer"),
//                        childAtPosition(
//                                allOf(withId(R.id.toolbar),
//                                        childAtPosition(
//                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
//                                                0)),
//                                1),
//                        isDisplayed()));
//        appCompatImageButton6.perform(click());
//
//        ViewInteraction navigationMenuItemView = onView(
//                allOf(childAtPosition(
//                        allOf(withId(R.id.design_navigation_view),
//                                childAtPosition(
//                                        withId(R.id.nav_view),
//                                        0)),
//                        1),
//                        isDisplayed()));
//        navigationMenuItemView.perform(click());

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

//        ViewInteraction actionMenuItemView3 = onView(
//                allOf(withId(R.id.fragment_help), withContentDescription("Hjælp"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.toolbar),
//                                        2),
//                                0),
//                        isDisplayed()));
//        actionMenuItemView3.perform(click());
//
//        ViewInteraction textView3 = onView(
//                allOf(withId(R.id.HelpDialogTextView), withText("Oversigt over fundne varemærker.\nVed valg af en række returneres denne til hovedbilledet, og der søges efter denne i databasen."),
//                        childAtPosition(
//                                childAtPosition(
//                                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
//                                        0),
//                                1),
//                        isDisplayed()));
//        textView3.check(matches(withText("Oversigt over fundne varemærker. Ved valg af en række returneres denne til hovedbilledet, og der søges efter denne i databasen.")));
//
//        ViewInteraction appCompatButton3 = onView(
//                allOf(withId(R.id.helpDialogButtonOK), withText("OK"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                2)));
//        appCompatButton3.perform(scrollTo(), click());

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

//        ViewInteraction appCompatImageButton8 = onView(
//                allOf(withContentDescription("Open navigation drawer"),
//                        childAtPosition(
//                                allOf(withId(R.id.toolbar),
//                                        childAtPosition(
//                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
//                                                0)),
//                                1),
//                        isDisplayed()));
//        appCompatImageButton8.perform(click());
//
//        ViewInteraction navigationMenuItemView2 = onView(
//                allOf(childAtPosition(
//                        allOf(withId(R.id.design_navigation_view),
//                                childAtPosition(
//                                        withId(R.id.nav_view),
//                                        0)),
//                        1),
//                        isDisplayed()));
//        navigationMenuItemView2.perform(click());

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

//        ViewInteraction actionMenuItemView4 = onView(
//                allOf(withId(R.id.fragment_help), withContentDescription("Hjælp"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.toolbar),
//                                        2),
//                                0),
//                        isDisplayed()));
//        actionMenuItemView4.perform(click());
//
//        ViewInteraction textView4 = onView(
//                allOf(withId(R.id.HelpDialogTextView), withText("Oversigt over fundne varenumre.\nVed valg af en række returneres denne til hovedbilledet, og der søges efter denne i databasen."),
//                        childAtPosition(
//                                childAtPosition(
//                                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
//                                        0),
//                                1),
//                        isDisplayed()));
//        textView4.check(matches(withText("Oversigt over fundne varenumre. Ved valg af en række returneres denne til hovedbilledet, og der søges efter denne i databasen.")));
//
//        ViewInteraction appCompatButton4 = onView(
//                allOf(withId(R.id.helpDialogButtonOK), withText("OK"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                2)));
//        appCompatButton4.perform(scrollTo(), click());

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

//        ViewInteraction appCompatImageButton10 = onView(
//                allOf(withContentDescription("Open navigation drawer"),
//                        childAtPosition(
//                                allOf(withId(R.id.toolbar),
//                                        childAtPosition(
//                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
//                                                0)),
//                                1),
//                        isDisplayed()));
//        appCompatImageButton10.perform(click());
//
//        ViewInteraction navigationMenuItemView3 = onView(
//                allOf(childAtPosition(
//                        allOf(withId(R.id.design_navigation_view),
//                                childAtPosition(
//                                        withId(R.id.nav_view),
//                                        0)),
//                        1),
//                        isDisplayed()));
//        navigationMenuItemView3.perform(click());

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

//        ViewInteraction appCompatImageButton13 = onView(
//                allOf(withId(R.id.googleBtn), withContentDescription("Google søgning"),
//                        childAtPosition(
//                                allOf(withId(R.id.buttonLinearLayout),
//                                        childAtPosition(
//                                                withClassName(is("android.widget.LinearLayout")),
//                                                18)),
//                                2)));
//        appCompatImageButton13.perform(scrollTo(), click());
    }

//    private static Matcher<View> childAtPosition(
//            final Matcher<View> parentMatcher, final int position) {
//
//        return new TypeSafeMatcher<View>() {
//            @Override
//            public void describeTo(Description description) {
//                description.appendText("Child at position " + position + " in parent ");
//                parentMatcher.describeTo(description);
//            }
//
//            @Override
//            public boolean matchesSafely(View view) {
//                ViewParent parent = view.getParent();
//                return parent instanceof ViewGroup && parentMatcher.matches(parent)
//                        && view.equals(((ViewGroup) parent).getChildAt(position));
//            }
//        };
//    }
}
