package com.example.openai;

public class CardData {

    private String id;
    private int icon;
    private String title;
    private String disc;
    private String colour;

    public CardData(String id, int icon, String title, String disc, String colour) {
        this.id = id;
        this.icon = icon;
        this.title = title;
        this.disc = disc;
        this.colour = colour;
    }

    public String getId() {
        return id;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getDisc() {
        return disc;
    }

    public String getColour() {
        return colour;
    }
}
