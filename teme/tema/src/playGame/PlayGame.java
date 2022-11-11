package playGame;

import fileio.*;
import java.util.ArrayList;

public class PlayGame {

    public Actions actions = new Actions();
    DecksInput playerOneDecks;
    DecksInput playerTwoDecks;
    ArrayList<GameInput> gameInputArray;
    public PlayGame(Input playGame) {
        playerOneDecks = playGame.getPlayerOneDecks();
        playerTwoDecks = playGame.getPlayerTwoDecks();
        gameInputArray = playGame.getGames();


    }
    public void play() {


       for(GameInput gameInputIterator : gameInputArray) {
            ArrayList<ActionsInput> actionsArray = gameInputIterator.getActions();
                actions.executeAction(actionsArray);
       }

    }
}
