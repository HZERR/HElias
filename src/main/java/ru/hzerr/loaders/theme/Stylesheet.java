package ru.hzerr.loaders.theme;

public class Stylesheet {

    private String nodeId;
    private String stylesheet;

    public Stylesheet() { }
    public Stylesheet(String nodeId) { this.nodeId = nodeId; }
    public Stylesheet(String nodeId, String stylesheet) { this.nodeId = nodeId; this.stylesheet = stylesheet; }

    public String getTargetNodeId() { return nodeId; }
    public void setTargetNodeId(String id) { this.nodeId = id; }
    public String getStylesheet() { return stylesheet; }
    public void setStylesheet(String stylesheet) { this.stylesheet = stylesheet; }

    public static Stylesheet create(String id, String stylesheet) { return new Stylesheet(id, stylesheet); }
}
