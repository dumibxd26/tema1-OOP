package cards.Heros;

import cards.Heros.Hero;
import cards.Minion;
import cards.Player;
import fileio.Coordinates;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LordRoyce extends Hero {

    private Map<Coordinates, Player> isFrozen;

    private Player playerOne;
    private Player playerTwo;
    public LordRoyce(int mana, String description, ArrayList<String> colors, String name, int health) {
        super(mana, description, colors, name, health);
    }

    public void useAbility(ArrayList<ArrayList<Minion>> playMatrix, int row) {

        int maxAttackMinion = -1;
        int position = 0;

        for(int i = 0; i < playMatrix.get(row).size(); i++) {
            if(playMatrix.get(row).get(i).getAttackDamage() > maxAttackMinion) {
                maxAttackMinion = playMatrix.get(row).get(i).getAttackDamage();
                position = i;
            }
        }

        if (row == 2 || row == 3) {
            isFrozen.put(new Coordinates(row, position), playerOne);
        } else {
            isFrozen.put(new Coordinates(row, position), playerTwo);
        }

    }

    public void setIsFrozen( Map<Coordinates, Player> isFrozen) {
        this.isFrozen = isFrozen;
    }

    public void setPlayers(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }
}
