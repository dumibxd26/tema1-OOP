package cards;

import playGame.Wins;
import cards.Heros.Hero;
import fileio.Coordinates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player {

    private ArrayList<Card> deck;
    private ArrayList<Card> hand = new ArrayList<Card>();
    private Hero hero;
    private int playerNumber;
    private Wins wins;
    private int mana = 1;
    private int tanks = 0;

    public Player(ArrayList<Card> deck, Hero hero, int playerNumber, Wins wins) {
        this.deck = deck;
        this.hero = hero;
        this.playerNumber = playerNumber;
        this.wins = wins;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Wins getWins() {
        return wins;
    }

    public void setWins(Wins wins) {
        this.wins = wins;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getTanks() {
        return tanks;
    }

    public void setTanks(int tanks) {
        this.tanks = tanks;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }
}
