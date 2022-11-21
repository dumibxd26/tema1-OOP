package playGame;

import cards.Heros.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import cards.*;
import cards.Card;
import fileio.Coordinates;

public class utility {

//    ObjectMapper mapper;
//    ArrayNode output;
    static ObjectNode node;

    public utility() {}
//    public utility(ObjectMapper mapper, ArrayNode output) {
//        this.mapper = mapper;
//        this.output = output;
//    }
    public static ArrayList<Card> createDeckOfCards(ArrayList<CardInput> cardInputArray, Map<Coordinates, Player> isFrozen) {

        ArrayList<Card> cardsArray = new ArrayList<Card>();

        for(CardInput cardInput: cardInputArray) {

            Card card = null;
            if (    cardInput.getName().compareTo("Sentinel") == 0 ||
                    cardInput.getName().compareTo("Berserker") == 0 ||
                    cardInput.getName().compareTo("Goliath") == 0 ||
                    cardInput.getName().compareTo("Warden") == 0 ) {
                card = new Minion(cardInput.getMana(), cardInput.getDescription(),
                                       cardInput.getColors(), cardInput.getName(),
                                        cardInput.getHealth(), cardInput.getAttackDamage());
            } else if (cardInput.getName().compareTo("The Ripper") == 0) {
                card = new Ripper(cardInput.getMana(), cardInput.getDescription(),
                        cardInput.getColors(), cardInput.getName(),
                        cardInput.getHealth(), cardInput.getAttackDamage());
            } else if (cardInput.getName().compareTo("Miraj") == 0) {
                card = new Miraj(cardInput.getMana(), cardInput.getDescription(),
                        cardInput.getColors(), cardInput.getName(),
                        cardInput.getHealth(), cardInput.getAttackDamage());
            } else if (cardInput.getName().compareTo("The Cursed One") == 0) {
                card = new CursedOne(cardInput.getMana(), cardInput.getDescription(),
                        cardInput.getColors(), cardInput.getName(),
                        cardInput.getHealth(), cardInput.getAttackDamage());
            } else if (cardInput.getName().compareTo("Disciple") == 0) {
                card = new Disciple(cardInput.getMana(), cardInput.getDescription(),
                        cardInput.getColors(), cardInput.getName(),
                        cardInput.getHealth(), cardInput.getAttackDamage());
            } else if (cardInput.getName().compareTo("Firestorm") == 0) {
                card = new Firestorm(cardInput.getMana(), cardInput.getDescription(),
                        cardInput.getColors(), cardInput.getName());
            } else if (cardInput.getName().compareTo("Winterfell") == 0) {
                card = new Winterfell(cardInput.getMana(), cardInput.getDescription(),
                        cardInput.getColors(), cardInput.getName());
                Winterfell winterfell = (Winterfell) card;
                winterfell.setIsFrozen(isFrozen);
            } else if (cardInput.getName().compareTo("Heart Hound") == 0) {
                card = new HeartHound(cardInput.getMana(), cardInput.getDescription(),
                        cardInput.getColors(), cardInput.getName());
            }
            cardsArray.add(card);
        }
        return cardsArray;
    }

    public static Hero createHero(int mana, String description, ArrayList<String> colors, String name, int health,  Map<Coordinates, Player> isFrozen) {

        Hero card = null;

        if (name.compareTo("Lord Royce") == 0) {
            card = new LordRoyce(mana, description, colors, name, health);
            LordRoyce lordRoyce = (LordRoyce) card;
            lordRoyce.setIsFrozen(isFrozen);
        } else if (name.compareTo("Empress Thorina") == 0) {
            card = new EmpressThorina(mana, description, colors, name, health);
        } else if (name.compareTo("King Mudface") == 0) {
            card = new KingMudface(mana, description, colors, name, health);
        } else if (name.compareTo("General Kocioraw") == 0) {
            card = new GeneralKacioraw(mana, description, colors, name, health);
        }

        return card;
    }

    public static void addIsFrozen(ArrayList<Card> deck, Player playerOne, Player playerTwo) {

        for (Card card : deck) {
            if (card instanceof Winterfell) {
                ((Winterfell) card).setPlayers(playerOne, playerTwo);
            } else if (card instanceof  LordRoyce) {
                ((LordRoyce) card).setPlayers(playerOne, playerTwo);
            }
        }
    }

    private static Boolean containsKey( Map<Coordinates, Player> isFrozen, Coordinates set) {

        for (Map.Entry<Coordinates, Player> entry : isFrozen.entrySet()) {
            if(entry.getKey().getX() == set.getX() && entry.getKey().getY() == set.getY()) {
                return true;
            }
        }
        return false;
    }

//    private static int getValue(Map<Coordinates, Integer> isFrozen, Coordinates set) {
//
//        for (Map.Entry<Coordinates,Integer> entry : isFrozen.entrySet()) {
//            if(entry.getKey().getX() == set.getX() && entry.getKey().getY() == set.getY()) {
//                return entry.getValue();
//            }
//        }
//        return -1;
//    }

    private static void addErrorPlaceCard(ActionsInput action, ObjectMapper mapper, ArrayNode output, String error) {
        node = mapper.createObjectNode();
        node.put("command", "placeCard");
        node.put("handIdx", action.getHandIdx());
        node.put("error", error);
        output.add(node);
    }

    private static void addErrorUseEnv(ActionsInput action, ObjectMapper mapper, ArrayNode output, String error) {
        node = mapper.createObjectNode();
        node.put("command", "useEnvironmentCard");
        node.put("handIdx", action.getHandIdx());
        node.put("affectedRow", action.getAffectedRow());
        node.put("error", error);
        output.add(node);
    }

    private static void addErrorUseCard(ActionsInput action, ObjectMapper mapper, ArrayNode output, String error, int command) {
        node = mapper.createObjectNode();
        if (command == 1) {
            node.put("command", "cardUsesAttack");
        } else {
            node.put("command", "cardUsesAbility");
        }

        node.put("cardAttacker", utilsOutput.createCoordinates(mapper, action.getCardAttacker()));
        node.put("cardAttacked", utilsOutput.createCoordinates(mapper, action.getCardAttacked()));
        node.put("error", error);
        output.add(node);
    }

    private static void addErrorAtkHero(ActionsInput action, ObjectMapper mapper, ArrayNode output, String error) {
        node = mapper.createObjectNode();
        node.put("command", "useAttackHero");
        node.put("cardAttacker", utilsOutput.createCoordinates(mapper, action.getCardAttacker()));
        node.put("error", error);
        output.add(node);
    }

    private static void addErrorAbilityHero(ActionsInput action, ObjectMapper mapper, ArrayNode output, String error) {
        node = mapper.createObjectNode();
        node.put("command", "useHeroAbility");
        node.put("affectedRow", action.getAffectedRow());
        node.put("error", error);
        output.add(node);
    }

    static public int addMana(int add) {

        if(add > 10)
            add = 0;
        return add;
    }

    static public Minion getMinionOnTable(ArrayList<ArrayList<Minion>> playMatrix, Coordinates set) {
        return playMatrix.get(set.getX()).get(set.getY());
    }
    public static Boolean checkFront(Card card) {
        return (card.getName().compareTo("The Ripper") == 0 ||
                card.getName().compareTo("Miraj") == 0 ||
                card.getName().compareTo("Goliath") == 0 ||
                card.getName().compareTo("Warden") == 0);
    }

    public static int getFrontRowNumber(int playerTurn) {
        if(playerTurn == 1) {
            return 2;
        }
        return 1;
    }

    public static int getRearRowNumber(int playerTurn) {
        if (playerTurn == 1) {
            return 3;
        }
        return 0;
    }

    public static ArrayList<Minion> getFrontRow(int playerTurn, ArrayList<ArrayList<Minion>> playMatrix) {
            return playMatrix.get(getFrontRowNumber(playerTurn));
    }

    public static ArrayList<Minion> getRearRow(int playerTurn, ArrayList<ArrayList<Minion>> playMatrix) {
            return playMatrix.get(getRearRowNumber(playerTurn));
    }
    public static Boolean checkEnvironmentCard(Card card) {

        return (card instanceof Environment);
    }

    public static Boolean checkMana(ActionsInput action, ObjectMapper mapper, ArrayNode output, Card card, Player player) {

        if (player.getMana() < card.getMana()) {
            addErrorPlaceCard(action, mapper, output, "Not enough mana to place card on table.");

            return false;
        }
        return true;
    }

    public static Boolean checkRowFull(ActionsInput action, ObjectMapper mapper, ArrayNode output, ArrayList<Minion> Row) {

        if (Row.size() == 5) {
            addErrorPlaceCard(action, mapper, output, "Cannot place card on table since row is full.");
            return true;
        }
        return false;
    }

    public static Boolean checkOtherPlayerRow(Player currentPlayer, int row) {
        if(currentPlayer.getPlayerNumber() == 1) {
            return (row == 0 || row == 1);
        } else {
            return (row == 2 || row == 3);
        }
    }

    public static Boolean checkHeartHound(Card card, ArrayList<ArrayList<Minion>> playMatrix, int currentRow) {
        if (card instanceof HeartHound) {
            return playMatrix.get(3 - currentRow).size() == 5 ? false : true;
        }
        return true;
    }

    public static Boolean checkIsTank(Card card) {
        return (card.getName().compareTo("Goliath") == 0 ||
                card.getName().compareTo("Warden") == 0 );
    }

    public static Boolean checkBelongsToEnemy(Player currentPlayer, Coordinates attackedCardCoordinates) {

        if (currentPlayer.getPlayerNumber() == 1 && (attackedCardCoordinates.getX() == 0 || attackedCardCoordinates.getX() == 1)) {
           return true;
        } else if (currentPlayer.getPlayerNumber() == 2 && (attackedCardCoordinates.getX() == 2 || attackedCardCoordinates.getX() == 3)) {
            return true;
        }
        return false;
    }

    public static Boolean contains(Set<Coordinates> usedAttack, Coordinates attackerCardCoord)  {
        for (Coordinates set : usedAttack) {
            if(set.getX() == attackerCardCoord.getX() && set.getY() == attackerCardCoord.getY()) {
                return true;
            }
        }
        return false;
    }
    public static Boolean checkAttackedThisTurn(Set<Coordinates> usedAttack, Coordinates attackerCardCoord) {
        if (utility.contains(usedAttack, attackerCardCoord)) {
            return true;
        }
        return false;
    }

    public static Boolean checkFrozen( Map<Coordinates, Player> isFrozen, Coordinates attackerCardCoord) {

        if (utility.containsKey(isFrozen, attackerCardCoord)) {
            return true;
        }
        return false;
    }

    public static Boolean hasTanks(Player player) {
        return player.getTanks() != 0;
    }
    public static Boolean checkTankOnTable(ArrayList<ArrayList<Minion>> playMatrix, Player otherPlayer) {
            return hasTanks(otherPlayer);
    }
    public static Boolean checkBelongsToCurrent(Player currentPlayer, Coordinates attackedCardCoord) {
        return !checkBelongsToEnemy(currentPlayer, attackedCardCoord);
    }

    public static Boolean checkHasToApplyOwnRow(Hero hero) {
        return (hero.getName().compareTo("King Mudface") == 0 ||
                hero.getName().compareTo("General Kocioraw") == 0);
    }

    public static Boolean checkCardAtPosition(ObjectNode commandNode, ArrayList<ArrayList<Minion>> playMatrix, Coordinates set) {

        if (playMatrix.get(set.getX()).size() <= set.getY()) {
            commandNode.put("output", "No card available at that position.");
            return false;
        }
        return true;
    }

    public static ArrayList<Environment> filterEnv(ArrayList<Card> hand) {

        ArrayList<Environment> envCards = new ArrayList<Environment>();

        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i) instanceof  Environment) {
                envCards.add((Environment) hand.get(i));
            }
        }
        return envCards;
    }

    public static ArrayList<Minion> getFrozenCards( Map<Coordinates, Player> isFrozen, ArrayList<ArrayList<Minion>> playMatrix) {

        ArrayList<Minion> frozenCard = new ArrayList<Minion>();
        for (Map.Entry<Coordinates, Player> entry : isFrozen.entrySet()) {
            frozenCard.add(getMinionOnTable(playMatrix, entry.getKey()));
        }
        return frozenCard;
    }

    public static Boolean checkTableEnvironmentCard(ActionsInput action, ObjectMapper mapper, ArrayNode output, Card card) {
       if(checkEnvironmentCard(card)) {
           addErrorPlaceCard(action, mapper, output, "Cannot place environment card on table.");
           return false;
       }
       return true;
    }


    public static Boolean checkUseEnvironmentCard(ActionsInput action, ObjectMapper mapper, ArrayNode output, Card card) {
        if (!checkEnvironmentCard(card)) {
            addErrorUseEnv(action, mapper, output, "Chosen card is not of type environment.");
            return false;
        }
        return true;
    }

    public static Boolean checkEnvOtherplayerrow(ActionsInput action, ObjectMapper mapper, ArrayNode output, Player player, int row) {
        if (!checkOtherPlayerRow(player, row)) {
            addErrorUseEnv(action, mapper, output, "Chosen row does not belong to the enemy.");
            return false;
        }
        return true;
    }

    public static Boolean checkEnvMana(ActionsInput action, ObjectMapper mapper, ArrayNode output, Player player, Card card) {
        if (player.getMana() < card.getMana()) {
            addErrorUseEnv(action, mapper, output, "Not enough mana to use environment card.");
            return false;
        }
        return true;
    }

    public static Boolean checkHeartHound(ActionsInput action, ObjectMapper mapper, ArrayNode output, Card card, ArrayList<ArrayList<Minion>> playMatrix, int currentRow) {
        if (card instanceof HeartHound) {
            if (playMatrix.get(3 - currentRow).size() == 5) {
                addErrorUseEnv(action, mapper, output, "Cannot steal enemy card since the player's row is full.");
                return false;
            }
            return true;
        }
        return true;
    }

    public static Boolean checkUseCardBelongsToEnemy(ActionsInput action, ObjectMapper mapper, ArrayNode output, Player currentPlayer, Coordinates attackedCardCoord, int command) {

        if (!checkBelongsToEnemy(currentPlayer, attackedCardCoord)) {
            addErrorUseCard(action, mapper, output, "Attacked card does not belong to the enemy.", command);
            return false;
        }
        return true;
    }

    public static Boolean checkUseCardBelongsToCurrent(ActionsInput action, ObjectMapper mapper, ArrayNode output, Player currentPlayer, Coordinates attackedCardCoord, int command) {

        if (checkBelongsToEnemy(currentPlayer, attackedCardCoord)) {
            addErrorUseCard(action, mapper, output, "Attacked card does not belong to the current player.", command);
            return false;
        }
        return true;
    }

    public static Boolean checkUseCardAtkAttackedThisTurn(ActionsInput action, ObjectMapper mapper, ArrayNode output, Set<Coordinates> usedAttack, Coordinates attackerCardCoord, int command) {

        if (checkAttackedThisTurn(usedAttack, attackerCardCoord)) {
            addErrorUseCard(action, mapper, output, "Attacker card has already attacked this turn.", command);
            return false;
        }
        return true;
    }

    public static Boolean checkUsecardAttackFrozen(ActionsInput action, ObjectMapper mapper, ArrayNode output,  Map<Coordinates, Player> isFrozen, Coordinates attackerCardCoord, int command) {

        if (checkFrozen(isFrozen, attackerCardCoord)) {
            addErrorUseCard(action, mapper, output, "Attacker card is frozen.", command);
            return false;
        }
        return true;
    }

    public static Boolean checkUseCardTankCard(ActionsInput action, ObjectMapper mapper, ArrayNode output, ArrayList<ArrayList<Minion>> playMatrix, Player otherPlayer, Minion attackedCard, int command) {
        if (!checkIsTank(attackedCard)) {

            if (checkTankOnTable(playMatrix, otherPlayer)) {
                addErrorUseCard(action, mapper, output, "Attacked card is not of type 'Tank'.", command);
                return false;
            }
            return true;
        }
        return true;
    }

    public static Boolean checkHeroAtkFrozen(ActionsInput action, ObjectMapper mapper, ArrayNode output,  Map<Coordinates, Player> isFrozen, Coordinates attackerCardCoord) {
        if (checkFrozen(isFrozen, attackerCardCoord)) {
            addErrorAtkHero(action, mapper, output, "Attacker card is frozen.");
            return false;
        }
        return true;
    }

    public static Boolean checkHeroAtkAttackedThisTurn(ActionsInput action, ObjectMapper mapper, ArrayNode output, Set<Coordinates> usedAttack, Coordinates attackerCardCoord) {

        if (checkAttackedThisTurn(usedAttack, attackerCardCoord)) {
            addErrorAtkHero(action, mapper, output, "Attacker card has already attacked this turn.");
            return false;
        }
        return true;
    }

    public static Boolean checkHeroAtkTankOnTable(ActionsInput action, ObjectMapper mapper, ArrayNode output, ArrayList<ArrayList<Minion>> playMatrix, Player otherPlayer) {
        if (hasTanks(otherPlayer)) {

            if (checkTankOnTable(playMatrix, otherPlayer)) {
                addErrorAtkHero(action, mapper, output, "Attacked card is not of type 'Tank'.");
                return false;
            }
            return true;
        }
        return true;
    }

    public static Boolean checkHeroMana(ActionsInput action, ObjectMapper mapper, ArrayNode output, Card card, Player player) {
        if (player.getMana() < card.getMana()) {

            addErrorAbilityHero(action, mapper, output, "Not enough mana to use hero's ability.");
            return false;
        }
        return true;
    }

    public static Boolean checkHeroAttacked(ActionsInput action, ObjectMapper mapper, ArrayNode output, Hero hero) {
        if (hero.getAttackedThisTurn()) {

            addErrorAbilityHero(action, mapper, output, "Hero has already attacked this turn.");
            return false;
        }
        return true;
    }

    public static Boolean checkHeroAbilityOtherPlayerRow(ActionsInput action, ObjectMapper mapper, ArrayNode output, Player currentPlayer, int row) {
        if (!checkOtherPlayerRow(currentPlayer, row)) {

            addErrorAbilityHero(action, mapper, output, "Selected row does not belong to the enemy.");
            return false;
        }
        return true;
    }

    public static Boolean checkHeroAbilityOwnPlayerRow(ActionsInput action, ObjectMapper mapper, ArrayNode output, Player currentPlayer, int row) {
        if (checkOtherPlayerRow(currentPlayer, row)) {

            addErrorAbilityHero(action, mapper, output, "Selected row does not belong to the current player.");
            return false;
        }
        return true;
    }

}
