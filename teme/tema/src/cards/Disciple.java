package cards;

import fileio.Coordinates;

import java.util.ArrayList;

public class Disciple extends SpecialMinion{

    public Disciple(int mana, String description, ArrayList<String> colors, String name, int health, int attackDamage) {
        super(mana, description, colors, name, health, attackDamage);
    }

    public void useAbility(ArrayList<ArrayList<Minion>> playMatrix, Coordinates set) {

        Minion minion = playMatrix.get(set.getX()).get(set.getY());
        minion.setHealth(minion.getHealth() + 2);
    }
}
