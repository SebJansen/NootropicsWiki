package nl.hva.mobileapplicationdevelopment.jansens015.nootropicswiki.models;

public class Article {
    private long id;
    private String title;
    private String description;
    private int image;

    public Article() {
    }

    public Article(String title, String description, int image) {
        this.id = System.currentTimeMillis();
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public Article(long id, String title, String description, int image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image=" + image +
                '}';
    }
}
