package cards;

import java.util.ArrayList;

public class Firestorm extends Environment{

    public Firestorm(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    public void useAbility(ArrayList<ArrayList<Minion>> playMatrix, int row) {

        ArrayList<Minion> otherMinionsRow = playMatrix.get(row);

        for (int i = 0 ; i < playMatrix.get(row).size(); i++) {
           Minion card =  playMatrix.get(row).get(i);

           if(card.getHealth() == 1) {
               playMatrix.get(row).remove(card);
               i--;
           } else {
               card.setHealth(card.getHealth() - 1);
            }
        }
    }
}
