package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ShowActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule =
            new ActivityScenarioRule<>(MainActivity.class);

    private void addCityAndOpen(String cityName) {
        // Add a city to the list (matches your existing ids)
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText(cityName), closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());

        // Tap the first item (position 0) to open ShowActivity
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.cities))
                .atPosition(0)
                .perform(click());
    }

    /** 1) Check whether the activity correctly switched */
    @Test
    public void testActivitySwitchesToShowActivity() {
        Intents.init();
        addCityAndOpen("Edmonton");
        intended(hasComponent(ShowActivity.class.getName()));
        Intents.release();
    }

    /** 2) Test whether the city name is consistent */
    @Test
    public void testCityNameIsShownCorrectly() {
        String target = "Edmonton";
        addCityAndOpen(target);
        onView(withId(R.id.text_city_name)).check(matches(withText(target)));
    }

    /** 3) Test the "back" button */
    @Test
    public void testBackButtonReturnsToMain() {
        addCityAndOpen("Edmonton");
        // Click the explicit back button in ShowActivity
        onView(withId(R.id.button_back)).perform(click());
        // Verify we're back on MainActivity (list is visible)
        onView(withId(R.id.cities)).check(matches(isDisplayed()));

        // (Alternative: pressBack(); works too if you prefer system back)
        // pressBack();
        // onView(withId(R.id.cities)).check(matches(isDisplayed()));
    }
}
