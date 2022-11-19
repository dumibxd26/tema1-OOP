package cards;

import fileio.CardInput;
import fileio.Coordinates;
import playGame.utility;

import java.util.ArrayList;

public class Miraj extends SpecialMinion {


    public Miraj(int mana, String description, ArrayList<String> colors, String name, int health, int attackDamage) {
        super(mana, description, colors, name, health, attackDamage);
    }

    public void useAbility(ArrayList<ArrayList<Minion>> playMatrix, Coordinates set) {

        Minion otherPlayer = utility.getMinionOnTable(playMatrix, set);
        int currentHealth = this.getHealth();

        this.setHealth(otherPlayer.getHealth());
        otherPlayer.setHealth(currentHealth);

    }

}
