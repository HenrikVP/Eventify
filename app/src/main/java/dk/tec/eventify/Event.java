package dk.tec.eventify;

public class Event {
    private String name;
    private String date;
    private String description;
    private String url;
    private String imageUri; // 🖼️ NY

    public Event(String name, String date, String description, String url, String imageUri) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.url = url;
        this.imageUri = imageUri;
    }

    public String getName() { return name; }
    public String getDate() { return date; }
    public String getDescription() { return description; }
    public String getUrl() { return url; }
    public String getImageUri() { return imageUri; }
}