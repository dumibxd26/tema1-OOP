package playGame;

import commands.*;
import fileio.ActionsInput;

import java.util.ArrayList;

public class Actions {



    public Actions() { }


    public void executeAction(ArrayList<ActionsInput> actionsList) {

        for(ActionsInput action : actionsList) {
            if(action.getCommand().compareTo("getPlayerDeck") == 0) {

                getPlayerDeck execGetPlayerDeck = getPlayerDeck.getInstance();
                execGetPlayerDeck.executeCommand(action);
            }
        }
    }

}
