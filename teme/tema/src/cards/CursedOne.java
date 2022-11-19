package cards;

import fileio.Coordinates;
import lombok.ToString;

import java.util.ArrayList;

public class CursedOne extends SpecialMinion{

    public CursedOne(int mana, String description, ArrayList<String> colors, String name, int health, int attackDamage) {
        super(mana, description, colors, name, health, attackDamage);
    }

    public void useAbility(ArrayList<ArrayList<Minion>> playMatrix, Coordinates set) {

        Minion otherMinion = playMatrix.get(set.getX()).get(set.getY());

        int temp = otherMinion.getAttackDamage();

        if (temp == 0) {
            playMatrix.get(set.getX()).remove(otherMinion);
        } else {
            otherMinion.setAttackDamage(otherMinion.getHealth());
            otherMinion.setHealth(temp);
        }
    }

}
