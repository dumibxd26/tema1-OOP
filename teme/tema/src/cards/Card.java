package cards;

import fileio.CardInput;

import java.util.ArrayList;

public abstract class Card {
    public int mana;
    public String description;
    public ArrayList<String> colors;
    public String name;

    public Card(int mana, String description, ArrayList<String> colors, String name) {
        this.mana = mana;
        this.description = description;
        this.colors = colors;
        this.name = name;
    }
    // Setters not needed
    public int getMana() {
        return mana;
    }
    public String getDescription() {
        return description;
    }
    public ArrayList<String> getColors() {
        return colors;
    }
    public String getName() {
        return name;
    }



}
