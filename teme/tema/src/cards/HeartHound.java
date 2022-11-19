package cards;

import java.util.ArrayList;

public class HeartHound extends Environment{

    public HeartHound(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    public void useAbility(ArrayList<ArrayList<Minion>> playMatrix, int row) {

        ArrayList<Minion> otherMinionsRow = playMatrix.get(row);
        int position = 0;
        int maxHealth = 0;

        for (int i = 0; i < otherMinionsRow.size(); i++) {
            if (otherMinionsRow.get(i).getHealth() > maxHealth) {
                maxHealth = otherMinionsRow.get(i).getHealth();
                position = i;
            }
        }

        playMatrix.get(3 - row).add(otherMinionsRow.remove(position));
    }
}
