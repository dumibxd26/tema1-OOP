package cards.Heros;

import cards.Minion;

import java.util.ArrayList;

public class KingMudface extends Hero{

    public KingMudface(int mana, String description, ArrayList<String> colors, String name, int health) {
        super(mana, description, colors, name, health);
    }

    public void useAbility(ArrayList<ArrayList<Minion>> playMatrix, int row) {

        for(Minion minion : playMatrix.get(row)) {
            minion.setHealth(minion.getHealth() + 1);
        }
    }
}
