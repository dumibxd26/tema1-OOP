package playGame;

public class Wins {
    int playerOneWins;
    int playerTwoWins;

    public Wins(int playerOneWins, int playerTwoWins) {
        this.playerOneWins = playerOneWins;
        this.playerTwoWins = playerTwoWins;
    }

    public int getPlayerOneWins() {
        return playerOneWins;
    }

    public void setPlayerOneWins(int playerOneWins) {
        this.playerOneWins = playerOneWins;
    }

    public int getPlayerTwoWins() {
        return playerTwoWins;
    }

    public void setPlayerTwoWins(int playerTwoWins) {
        this.playerTwoWins = playerTwoWins;
    }
}
