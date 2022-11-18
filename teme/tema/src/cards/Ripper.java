package cards;

import fileio.CardInput;
import fileio.Coordinates;

import java.util.ArrayList;

public class Ripper extends SpecialMinion{

    public Ripper(int mana, String description, ArrayList<String> colors, String name, int health, int attackDamage) {
        super(mana, description, colors, name, health, attackDamage);
    }

    public void useAbility(ArrayList<ArrayList<Minion>> playMatrix, Coordinates set) {
        Minion otherMinion = playMatrix.get(set.getX()).get(set.getY());

        otherMinion.setHealth(otherMinion.getHealth() - 2);
    }
}
