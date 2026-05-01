package dk.tec.eventify;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {

    TextView name, description;
    Button openBrowserBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        name = findViewById(R.id.detailName);
        description = findViewById(R.id.detailDescription);
        openBrowserBtn = findViewById(R.id.browserBtn);
        backBtn = findViewById(R.id.backBtn);

        Intent intent = getIntent();

        String eventName = intent.getStringExtra("name");
        String eventDesc = intent.getStringExtra("description");
        String url = intent.getStringExtra("url");

        name.setText(eventName);
        description.setText(eventDesc);

        // 🌐 Åbn browser
        openBrowserBtn.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });

        // 🔙 Tilbage
        backBtn.setOnClickListener(v -> finish());
    }
}