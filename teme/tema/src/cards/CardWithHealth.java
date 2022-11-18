package cards;

import java.util.ArrayList;

public abstract class CardWithHealth extends Card{

    public int health;

    public CardWithHealth(int mana, String description, ArrayList<String> colors, String name,int health) {
        super(mana, description, colors, name);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
