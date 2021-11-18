package ru.hzerr.loaders.theme;

public class Entity {

    private String id;
    private String stylesheet;

    public Entity() { }
    public Entity(String id) { this.id = id; }
    public Entity(String id, String stylesheet) { this.id = id; this.stylesheet = stylesheet; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStylesheet() { return stylesheet; }
    public void setStylesheet(String stylesheet) { this.stylesheet = stylesheet; }

    public static Entity create(String id, String stylesheet) { return new Entity(id, stylesheet); }
}
