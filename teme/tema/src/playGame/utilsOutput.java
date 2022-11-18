package playGame;

import cards.Card;
import cards.Environment;
import cards.Heros.*;
import cards.Minion;
import cards.Player;
import fileio.Coordinates;
import playGame.utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Map;
import java.util.Set;

import java.util.ArrayList;


public class utilsOutput {

    public static ArrayNode createColors(ObjectMapper mapper, ArrayList<String> colors) {
        ArrayNode outString = mapper.createArrayNode();
        for (String color : colors) {
            outString.add(color);
        }

        return outString;
    }

    public static ObjectNode createMinion(ObjectMapper mapper, Minion card) {
        ObjectNode node = mapper.createObjectNode();

        node.put("mana", card.getMana());
        node.put("attackDamage", card.getAttackDamage());
        node.put("health", card.getHealth());
        node.put("description", card.getDescription());
        node.put("colors", createColors(mapper, card.getColors()));
        node.put("name", card.getName());

        return node;
    }

    public static ObjectNode createEnvironment(ObjectMapper mapper, Environment card) {
        ObjectNode node = mapper.createObjectNode();

        node.put("mana", card.getMana());
        node.put("description", card.getDescription());
        node.put("colors", createColors(mapper, card.getColors()));
        node.put("name", card.getName());

        return node;
    }

    public static ObjectNode createHero(ObjectMapper mapper, Hero hero) {
        ObjectNode node = mapper.createObjectNode();

        node.put("mana", hero.getMana());
        node.put("description", hero.getDescription());
        node.put("colors", createColors(mapper, hero.getColors()));
        node.put("name", hero.getName());
        node.put("health", hero.getHealth());

        return node;
    }

    public static ArrayNode createDeck(ObjectMapper mapper, ArrayList<Card> deck) {

        ArrayNode outDeck = mapper.createArrayNode();

        for (Card card : deck) {

            if (utility.checkEnvironmentCard(card)) {
                outDeck.add(createEnvironment(mapper, (Environment) card));
            } else {
                outDeck.add(createMinion(mapper, (Minion) card));
            }
        }

        return outDeck;
    }

    public static ArrayList<Card> castToCard(ArrayList<Minion> row) {

        ArrayList<Card> cardRow = new ArrayList<Card>();

        for (Minion minion: row) {
            cardRow.add(minion);
        }

        return cardRow;
    }
    public static ArrayNode createTable(ObjectMapper mapper, ArrayList<ArrayList<Minion>> table) {

        ArrayNode outTable = mapper.createArrayNode();

        for (ArrayList<Minion> row : table) {
            outTable.add(createDeck(mapper, castToCard(row)));
        }

        return outTable;
    }

    public static ArrayNode createEnvDeck(ObjectMapper mapper, ArrayList<Environment> envArray) {
        ArrayNode outArr = mapper.createArrayNode();

        for (Environment envCard : envArray) {
            outArr.add(createEnvironment(mapper, envCard));
        }

        return outArr;
    }

    public static ArrayNode createFrozenCards(ObjectMapper mapper, ArrayList<ArrayList<Minion>> playMatrix,  Map<Coordinates, Player> isFrozen) {

        ArrayNode outArr = mapper.createArrayNode();

        for (Map.Entry<Coordinates, Player> entry : isFrozen.entrySet()) {
            outArr.add(createMinion(mapper, utility.getMinionOnTable(playMatrix, entry.getKey())));
        }

        return outArr;
    }

    public static ObjectNode createCoordinates(ObjectMapper mapper, Coordinates set) {

        ObjectNode node = mapper.createObjectNode();

        node.put("x", set.getX());
        node.put("y", set.getY());

        return node;
    }
}
