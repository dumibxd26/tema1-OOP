package cards;

import fileio.Coordinates;

import java.util.ArrayList;

public abstract class SpecialMinion extends Minion{

    public SpecialMinion(int mana, String description, ArrayList<String> colors, String name, int health, int attackDamage) {
        super(mana, description, colors, name, health, attackDamage);
    }

    public abstract void useAbility(ArrayList<ArrayList<Minion>> playMatrix, Coordinates set);
}
