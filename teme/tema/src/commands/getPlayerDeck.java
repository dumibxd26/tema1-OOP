package commands;

import fileio.ActionsInput;

public class getPlayerDeck {

    private final static getPlayerDeck instance = new getPlayerDeck();

    private getPlayerDeck() {}

    public static getPlayerDeck getInstance() {
        return instance;
    }

    public void executeCommand(ActionsInput action) {
        System.out.println(action);
        System.out.println(action.getPlayerIdx());
    }
}
