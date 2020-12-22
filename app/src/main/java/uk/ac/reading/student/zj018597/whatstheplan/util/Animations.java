package uk.ac.reading.student.zj018597.whatstheplan.util;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

public class Animations {

    /**
     * Sets the {@link FloatingActionButton} animation when an item is deleted.
     * @param floatingActionButton is pulled down after the {@link Snackbar} is gone.
     */
    public static void setFabAnimLift(FloatingActionButton floatingActionButton) {
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
        animPause.setDuration(3000);

        final AnimationSet animSet = new AnimationSet(true);
        animSet.addAnimation(animLift);
        animSet.addAnimation(animPause);

        floatingActionButton.startAnimation(animSet);
    }

    public static void setFabAnimPull(FloatingActionButton floatingActionButton) {
        final TranslateAnimation animPull = new TranslateAnimation(
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, 1
        );
        animPull.setDuration(500);
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> floatingActionButton.setAnimation(animPull), 200);
    }

}
