package net.myerichsen.toiletpaper;

import net.myerichsen.toiletpaper.ui.home.HomeFragment;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    public void testATest() {
        HomeFragment hf = new HomeFragment();
        hf.calculate();
    }
}