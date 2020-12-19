package uk.ac.reading.student.zj018597.whatstheplan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import uk.ac.reading.student.zj018597.whatstheplan.R;
import uk.ac.reading.student.zj018597.whatstheplan.db.PlanEntity;

/**
 * Activity which creates and adds a new {@link PlanEntity}.
 */
public class AddPlanActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_PLAN = "whatstheplan.plan.REPLY";

    private EditText etName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        etName = findViewById(R.id.et_plan);

        final Button button = findViewById(R.id.btn_add_plan);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(etName.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String word = etName.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY_PLAN, word);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }

}
