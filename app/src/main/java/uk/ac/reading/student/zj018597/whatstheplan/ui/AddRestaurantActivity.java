package uk.ac.reading.student.zj018597.whatstheplan.ui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import uk.ac.reading.student.zj018597.whatstheplan.R;

public class AddRestaurantActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_RESTAURANT = "whatstheplan.restaurant.REPLY";

    private EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        etName = findViewById(R.id.et_restaurant);

        final Button button = findViewById(R.id.btn_add_restaurant);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(etName.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String word = etName.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY_RESTAURANT, word);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}
