package uk.ac.reading.student.zj018597.whatstheplan.util;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.ac.reading.student.zj018597.whatstheplan.R;

public class CustomSnackBar {

    public static Snackbar setSnackBar(Activity activity, Context context, int viewId, String message) {
        final Snackbar snackbar = Snackbar.make(
                activity.findViewById(viewId), message, Snackbar.LENGTH_LONG
        );
        // Set text and background
        View view = snackbar.getView();
        TextView textView = view.findViewById(android.support.design.R.id.snackbar_text);
        view.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
        textView.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimaryLight));

        // Set params
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        params.setMargins(
                params.leftMargin, params.topMargin, params.rightMargin,
                Math.round(Calculators.convertDpToPx(context, 56))
        );
        view.setLayoutParams(params);
        return snackbar;
    }

}
