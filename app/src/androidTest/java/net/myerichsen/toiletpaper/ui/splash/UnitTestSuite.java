/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

package net.myerichsen.toiletpaper.ui.splash;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

// Run all unit tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AboutTest.class,
        CompareTest.class,
        HomeTest.class,
        PricesTest.class,
        ProductTest.class,
        SupplierTest.class})

public class UnitTestSuite {
}

