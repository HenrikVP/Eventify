package dk.tec.eventify;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateEventActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;

    EditText name, date, description, url;
    ImageView imagePreview;
    String imageUri = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        name = findViewById(R.id.inputName);
        EditText dateInput = findViewById(R.id.inputDate);

        dateInput.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(
                    this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {

                        // Format: DD/MM/YYYY
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(selectedYear, selectedMonth, selectedDay);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        dateInput.setText(sdf.format(selectedDate.getTime()));
                    },
                    year, month, day
            );

            datePicker.show();
        });
        description = findViewById(R.id.inputDescription);
        url = findViewById(R.id.inputUrl);
        imagePreview = findViewById(R.id.imagePreview);

        Button pickImageBtn = findViewById(R.id.pickImageBtn);
        Button saveBtn = findViewById(R.id.saveBtn);

        // 🖼️ Vælg billede
        pickImageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE);
        });

        // 💾 Gem event
        saveBtn.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", name.getText().toString());
            resultIntent.putExtra("date", dateInput.getText().toString());
            resultIntent.putExtra("description", description.getText().toString());
            resultIntent.putExtra("url", url.getText().toString());
            resultIntent.putExtra("imageUri", imageUri);

            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            imageUri = uri.toString();
            imagePreview.setImageURI(uri);
        }
    }
}