package net.myerichsen.toiletpaper.ui.splash;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import net.myerichsen.toiletpaper.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AboutTest extends ToiletPaperTest {

    @Test
    public void aboutTest() {
        drawerPosition(6);

        ViewInteraction textView = onView(
                allOf(withId(R.id.versionTextView), withText(containsString("Toiletpapir\nVersion")),
                        childAtPosition(
                                allOf(withId(R.id.aboutConstraintLayout),
                                        childAtPosition(
                                                withId(R.id.aboutScrollView),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText(containsString("Toiletpapir"))));

        help("Forklaring til programmet.");
    }
}
