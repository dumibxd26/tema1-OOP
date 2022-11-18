package cards.Heros;

import java.util.ArrayList;
import cards.Minion;

public class GeneralKacioraw extends  Hero{

    public GeneralKacioraw(int mana, String description, ArrayList<String> colors, String name, int health) {
        super(mana, description, colors, name, health);
    }

    public void useAbility(ArrayList<ArrayList<Minion>> playMatrix, int row) {

        for(Minion minion : playMatrix.get(row)) {
            minion.setAttackDamage(minion.getAttackDamage() + 1);
        }
    }
}
