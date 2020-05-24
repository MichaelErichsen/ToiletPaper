package net.myerichsen.toiletpaper.ui.splash;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

// Runs all unit tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AboutTest.class,
        HomeHelpTest.class,
        HomeItemNoSearchSeveralResultsTest.class,
        HomeItemNoSearchSeveralResultsTest.class,
        CompareTest.class})
public class UnitTestSuite {
}

