package uk.ac.reading.student.zj018597.whatstheplan.util;

import android.app.Activity;
import android.content.Context;
import com.google.android.material.snackbar.Snackbar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.ac.reading.student.zj018597.whatstheplan.R;

public class CustomSnackBar {

    public static Snackbar setSnackBar(Activity activity, Context context, int viewId,
                                       String message) {
        final Snackbar snackbar = Snackbar.make(
                activity.findViewById(viewId), message, Snackbar.LENGTH_LONG
        );
        // Set text and background
        View view = snackbar.getView();
        TextView textView = view.findViewById(com.google.android.material.R.id.snackbar_text);
        view.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
        textView.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimaryLight));

        // Set params
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                snackbar.getView().getLayoutParams();
        params.setMargins(
                params.leftMargin, params.topMargin, params.rightMargin,
                Math.round(Calculators.convertDpToPx(context, 56))
        );
        view.setLayoutParams(params);
        return snackbar;
    }

}
