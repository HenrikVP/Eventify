package dk.tec.eventify;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Event> eventList;
    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private EditText searchField;
    private Button searchBtn;

    private ActivityResultLauncher<Intent> createEventLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        searchField = findViewById(R.id.searchField);
        searchBtn = findViewById(R.id.searchBtn);

        eventList = new ArrayList<>();


        eventList.add(new Event(
                "Generalforsamling",
                "10/05",
                "Årligt møde for medlemmer",
                "https://example.com",
                ""
        ));
        // Hardcoded data
        eventList.add(new Event(
                "Generalforsamling",
                "10/05",
                "Årligt møde for medlemmer",
                "https://example.com",
                ""
        ));

        eventList.add(new Event(
                "Sommerfest",
                "20/06",
                "Socialt arrangement",
                "https://example.com",
                ""
        ));

        adapter = new EventAdapter(eventList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // 🔍 Søgning
        searchBtn.setOnClickListener(v -> {
            String query = searchField.getText().toString().toLowerCase();
            ArrayList<Event> filtered = new ArrayList<>();

            for (Event e : eventList) {
                if (e.getName().toLowerCase().contains(query)) {
                    filtered.add(e);
                }
            }

            adapter.updateList(filtered);
        });

        createEventLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                        Intent data = result.getData();

                        Event event = new Event(
                                data.getStringExtra("name"),
                                data.getStringExtra("date"),
                                data.getStringExtra("description"),
                                data.getStringExtra("url"),
                                data.getStringExtra("imageUri")
                        );

                        eventList.add(event);
                        adapter.notifyDataSetChanged();
                    }
                }
        );


        FloatingActionButton fab = findViewById(R.id.fabAddEvent);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateEventActivity.class);
            createEventLauncher.launch(intent);
        });
    }
}