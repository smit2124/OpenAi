package com.example.openai;

public class CardData {

    private  String id;

    private int icon;

    private String color;

    private String title;

    private  String disc;

    public CardData(String id,int icon,String color,String title,String disc) {

        this.id = id;
        this.icon = icon;
        this.color = color;
        this.title = title;
        this.disc = disc;

    }

    public String getId() {

        return  id;
    }

    public int getIcon() {
        return icon;
    }

    public String getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }

    public String getDisc() {
        return disc;
    }
}
