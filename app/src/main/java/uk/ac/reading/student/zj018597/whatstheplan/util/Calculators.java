package uk.ac.reading.student.zj018597.whatstheplan.util;

import android.content.Context;

/**
 * Contains mathematical calculations.
 */
public class Calculators {

    /**
     * @return  pixel value in float
     */
    public static float convertDpToPx(Context context, int dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
