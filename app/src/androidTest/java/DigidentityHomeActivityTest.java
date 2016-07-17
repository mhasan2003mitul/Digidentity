import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ListView;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import eu.digidentity.exam.DigidentityHomeActivity;
import eu.digidentity.exam.R;
import eu.digidentity.exam.view.adapter.CatalogItemAdapter;

/**
 * Created by Md. Mainul Hasan Patwary on 17-Jul-16.
 * Email: mhasan_mitul@yahoo.com
 * Skype: mhasan_mitul
 */
@RunWith(AndroidJUnit4.class)
public class DigidentityHomeActivityTest {

    @Rule
    public ActivityTestRule<DigidentityHomeActivity> mFirstActivityTestRule = new ActivityTestRule<>(DigidentityHomeActivity.class);

    @Test
    public void isScrollViewDisplayed() throws Exception
    {
        Espresso.onView(ViewMatchers.withId(R.id.catalogItemScrollView)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void isListViewDisplayed() throws Exception
    {
        Espresso.onView(ViewMatchers.withId(R.id.catalogItemListView)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void swipUpCatalogListView() throws Exception
    {
        Espresso.onView(ViewMatchers.withId(R.id.catalogItemListView)).perform(ViewActions.swipeUp());
        Espresso.onView(ViewMatchers.withId(R.id.catalogItemListView)).perform(ViewActions.swipeDown());
    }

}
