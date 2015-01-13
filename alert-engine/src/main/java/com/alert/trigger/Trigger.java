package com.alert.trigger;

public class Trigger extends TriggerTemplate {

    // id must be unique
    private String id;
    private boolean active;

    public Trigger(String id, String name) {
        this(id, name, true);
    }

    public Trigger(String id, String name, boolean active) {
        super(name);

        if (null == id || id.isEmpty()) {
            throw new IllegalArgumentException("Trigger id must be non-empty.");
        }

        this.id = id;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
