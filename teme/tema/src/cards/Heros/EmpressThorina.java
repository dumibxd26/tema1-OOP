package cards.Heros;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import cards.Minion;
import fileio.Coordinates;

public class EmpressThorina extends Hero{

    public EmpressThorina(int mana, String description, ArrayList<String> colors, String name, int health) {
        super(mana, description, colors, name, health);
    }

    public void useAbility(ArrayList<ArrayList<Minion>> playMatrix, int row) {

        int maxHealthMinion = -1;
        int position = -1;

        for(int i = 0; i < playMatrix.get(row).size(); i++) {
            if(playMatrix.get(row).get(i).getHealth() > maxHealthMinion) {
                maxHealthMinion = playMatrix.get(row).get(i).getHealth();
                position = i;
            }
        }
        playMatrix.get(row).remove(position);
    }
}
