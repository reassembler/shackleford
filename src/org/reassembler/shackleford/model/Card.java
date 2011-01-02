package org.reassembler.shackleford.model;

public class Card {
    private int id;
    private int resourceId;

    public Card(int id, int resourceId) {
        this.id = id;
        this.resourceId = resourceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return  String.format("id: %s, Resource: %s", id, resourceId);
    }
}
