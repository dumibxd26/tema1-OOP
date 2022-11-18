package cards;

import fileio.Coordinates;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Winterfell extends Environment{

    private  Map<Coordinates, Player> isFrozen;

    private Player playerOne;
    private Player playerTwo;

    public Winterfell(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    public void useAbility(ArrayList<ArrayList<Minion>> playMatrix, int row) {

        for(int i = 0; i < playMatrix.get(row).size(); i++) {
            Coordinates coordinates = new Coordinates(row, i);

            if (row == 2 || row == 3) {
                isFrozen.put(coordinates, playerOne);
            } else {
                isFrozen.put(coordinates, playerTwo);
            }
        }
    }

    public void setIsFrozen(Map<Coordinates, Player> isFrozen) {
        this.isFrozen = isFrozen;
    }

    public void setPlayers(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }
}
