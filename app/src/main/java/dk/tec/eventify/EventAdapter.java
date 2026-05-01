package dk.tec.eventify;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private ArrayList<Event> events;
    private Context context;

    public EventAdapter(ArrayList<Event> events, Context context) {
        this.events = events;
        this.context = context;
    }

    public void updateList(ArrayList<Event> newList) {
        events = newList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = events.get(position);

        ImageView image = holder.itemView.findViewById(R.id.eventImage);
        if (event.getImageUri() != null && !event.getImageUri().isEmpty()) {
            image.setImageURI(Uri.parse(event.getImageUri()));
        } else {
            image.setImageResource(R.drawable.logo);
        }

        holder.name.setText(event.getName());
        holder.date.setText(event.getDate());

        // 🔍 Detaljer-knap
        holder.detailsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("name", event.getName());
            intent.putExtra("description", event.getDescription());
            intent.putExtra("url", event.getUrl());
            context.startActivity(intent);
        });

        // ✅ Tilmeld-knap (Dialog)
        holder.signupBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Tilmelding")
                    .setMessage("Er du sikker på, du vil tilmelde dig " + event.getName() + "?")
                    .setPositiveButton("Ja", (dialog, which) -> {
                        Toast.makeText(context, "Tilmeldt!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Nej", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, date;
        Button detailsBtn, signupBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.eventName);
            date = itemView.findViewById(R.id.eventDate);
            detailsBtn = itemView.findViewById(R.id.detailsBtn);
            signupBtn = itemView.findViewById(R.id.signupBtn);
        }
    }
}