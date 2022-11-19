package playGame;

import cards.*;
import cards.Heros.Hero;
import cards.Heros.LordRoyce;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.*;

import java.util.*;

public class PlayGame {

    ObjectMapper mapper;
    ArrayNode output;
    private Actions actions;
    private DecksInput playerOneDecks;
    private DecksInput playerTwoDecks;
    private ArrayList<GameInput> gameInputArray;
    private ArrayList<ArrayList<Minion>> playMatrix = new ArrayList<ArrayList<Minion>>(4);

    private Map<Coordinates, Player> isFrozen = new HashMap<Coordinates, Player>();
    private Set<Coordinates> usedAttack = new HashSet<Coordinates>();

    private Wins wins = new Wins(0, 0);

    private int playedGames = 1;
    public PlayGame(Input playGame, ObjectMapper mapper, ArrayNode output) {
        playerOneDecks = playGame.getPlayerOneDecks();
        playerTwoDecks = playGame.getPlayerTwoDecks();
        gameInputArray = playGame.getGames();
        this.mapper = mapper;
        this.output = output;

        for (int i = 0; i < 4; i++) {
            playMatrix.add(new ArrayList<Minion>(5 ));
        }
    }
    public void play() {
        // ia fiecare JOC din array=ul de JOCURI
        // actions este diferit pentru fiecare game,

        // returnez eroul, deck-urile si randu player-ului curent;
       for(GameInput gameInputIterator : gameInputArray) {

           CardInput playerOneHeroInput = gameInputIterator.getStartGame().getPlayerOneHero();
           CardInput playerTwoHeroInput = gameInputIterator.getStartGame().getPlayerTwoHero();
           int playerOneDeckIndex = gameInputIterator.getStartGame().getPlayerOneDeckIdx();
           int playerTwoDeckIndex = gameInputIterator.getStartGame().getPlayerTwoDeckIdx();
           int playerTurn = gameInputIterator.getStartGame().getStartingPlayer();
           int shuffleDeckSeed = gameInputIterator.getStartGame().getShuffleSeed();

//           Random random = new Random(shuffleDeckSeed);

           ArrayList<Card> playerOneDeck = utility.createDeckOfCards(playerOneDecks.getDecks().get(playerOneDeckIndex), isFrozen);
           ArrayList<Card> playerTwoDeck = utility.createDeckOfCards(playerTwoDecks.getDecks().get(playerTwoDeckIndex), isFrozen);

           Collections.shuffle(playerOneDeck, new Random(shuffleDeckSeed));
           Collections.shuffle(playerTwoDeck, new Random(shuffleDeckSeed));

           // Initialise heros
           Hero playerOneHero = utility.createHero(playerOneHeroInput.getMana(), playerOneHeroInput.getDescription(),
                                         playerOneHeroInput.getColors(), playerOneHeroInput.getName(), 30, isFrozen);

           Hero playerTwoHero = utility.createHero(playerTwoHeroInput.getMana(), playerTwoHeroInput.getDescription(),
                                          playerTwoHeroInput.getColors(), playerTwoHeroInput.getName(), 30, isFrozen);

           // Initialise heros
           Player playerOne = new Player(playerOneDeck, playerOneHero, 1, wins);
           Player playerTwo = new Player(playerTwoDeck, playerTwoHero, 2, wins);

           // The biggest piece of dog shit
           utility.addIsFrozen(playerOneDeck, playerOne, playerTwo);
           utility.addIsFrozen(playerTwoDeck, playerOne, playerTwo);

           if (playerOne.getHero().getName().compareTo("Lord Royce") == 0) {
               ((LordRoyce)playerOne.getHero()).setPlayers(playerOne, playerTwo);
           }
           if (playerTwo.getHero().getName().compareTo("Lord Royce") == 0) {
               ((LordRoyce)playerTwo.getHero()).setPlayers(playerTwo, playerTwo);
           }

           // Initialise the matrix of cards for each game
           for(int i = 0; i < 4; i++) {
               playMatrix.get(i).clear();
           }
           // Add a card in the hand of each user
           playerOne.getHand().add(playerOne.getDeck().remove(0));
           playerTwo.getHand().add(playerTwo.getDeck().remove(0));

           actions = new Actions(playerOne, playerTwo, playMatrix, playerTurn, isFrozen, usedAttack, mapper, output, playedGames, wins);
           ArrayList<ActionsInput> actionsArray = gameInputIterator.getActions();
           actions.executeActions(actionsArray);
           playedGames++;
       }

    }
}
