package uk.ac.reading.student.zj018597.whatstheplan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import uk.ac.reading.student.zj018597.whatstheplan.R;
import uk.ac.reading.student.zj018597.whatstheplan.db.RestaurantEntity;

/**
 * Launched to create and add a new {@link RestaurantEntity}.
 */
public class AddRestaurantActivity extends AppCompatActivity {

    private EditText etName;
    public static final String EXTRA_REPLY_RESTAURANT = "whatstheplan.restaurant.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
        etName = findViewById(R.id.et_restaurant);
        Button button = findViewById(R.id.btn_add_restaurant);
        button.setOnClickListener(new AddNewRestaurantClick());
    }

    /**
     * Pass new {@link RestaurantEntity} name via intent and close Activity.
     */
    private class AddNewRestaurantClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(etName.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String restaurant = etName.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY_RESTAURANT, restaurant);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        }
    }
}
