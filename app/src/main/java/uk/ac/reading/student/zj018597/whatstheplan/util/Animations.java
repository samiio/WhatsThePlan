package uk.ac.reading.student.zj018597.whatstheplan.util;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

public class Animations {

    /**
     * Sets the {@link FloatingActionButton} animation when an item is deleted.
     * @param floatingActionButton is pulled down after the {@link Snackbar} is gone.
     */
    public static void setFabAnimLift(FloatingActionButton floatingActionButton) {

        final int TIMEOUT_FAB = 3000;

        final TranslateAnimation animLift = new TranslateAnimation(
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, -100
        );

        animLift.setDuration(100);

        final TranslateAnimation animPause = new TranslateAnimation(
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, 0
        );

        animPause.setDuration(TIMEOUT_FAB);

        final AnimationSet animSet = new AnimationSet(true);
        animSet.addAnimation(animLift);
        animSet.addAnimation(animPause);

        floatingActionButton.startAnimation(animSet);
    }

    /**
     * Sets the {@link FloatingActionButton} the initial animation when an item is deleted.
     * @param floatingActionButton is is moved up when to make space for the {@link Snackbar}.
     */
    public static void setFabAnimPull(FloatingActionButton floatingActionButton) {

        final TranslateAnimation animPull = new TranslateAnimation(
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, 1
        );
        animPull.setDuration(500);
        floatingActionButton.setAnimation(animPull);
    }

}
