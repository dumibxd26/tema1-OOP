package cards;

import java.util.ArrayList;

public abstract class Card {
    private final int mana;
    private final String description;
    private final ArrayList<String> colors;
    private final String name;

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
