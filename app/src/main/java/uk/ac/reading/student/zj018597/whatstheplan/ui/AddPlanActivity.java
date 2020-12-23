package uk.ac.reading.student.zj018597.whatstheplan.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import uk.ac.reading.student.zj018597.whatstheplan.R;
import uk.ac.reading.student.zj018597.whatstheplan.db.PlanEntity;

/**
 * Launched to create and add a new {@link PlanEntity}.
 */
public class AddPlanActivity extends AppCompatActivity {

    private EditText etName;
    public static final String EXTRA_REPLY_PLAN = "whatstheplan.plan.REPLY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        etName = findViewById(R.id.et_plan);
        Button button = findViewById(R.id.btn_add_plan);
        button.setOnClickListener(new AddNewPlanClick());

        Toolbar toolbar = findViewById(R.id.tbr_activity_plan_activity);
        setToolbar(toolbar);
    }

    private void setToolbar(Toolbar tbr) {
        TextView tvToolbarTitle = tbr.findViewById(R.id.tbr_main_title);
        tvToolbarTitle.setText(getResources().getString(R.string.add_plan));
        tbr.setTitle("");
        setSupportActionBar(tbr);
    }

    /**
     * Pass new {@link PlanEntity} name via intent and close Activity.
     */
    private class AddNewPlanClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(etName.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String plan = etName.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY_PLAN, plan);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        }
    }
}
