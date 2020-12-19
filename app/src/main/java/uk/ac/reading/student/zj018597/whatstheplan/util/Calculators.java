package uk.ac.reading.student.zj018597.whatstheplan.util;

import android.app.Activity;
import android.content.Context;

import uk.ac.reading.student.zj018597.whatstheplan.ui.MainActivity;

public class Calculators {

    /**
     * Converts dpi value into pixels
     * To be used in:
     * Method {@link MainActivity#toolbarElevation()}
     * Method {@link CustomSnackBar#setSnackBar(Activity, Context, int, String)}
     * @return  pixel value in float
     */
    public static float convertDpToPx(Context context, int dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

}
