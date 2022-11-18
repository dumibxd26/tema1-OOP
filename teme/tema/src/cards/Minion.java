package cards;

import fileio.Coordinates;

import java.util.ArrayList;

import playGame.utility;
public class Minion extends CardWithHealth{

    private int attackDamage;

    private int 

    public Minion(int mana, String description, ArrayList<String> colors, String name, int health, int attackDamage) {
        super(mana, description, colors, name, health);
        this.attackDamage = attackDamage;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void attack(CardWithHealth card) {
        card.setHealth(card.getHealth() - attackDamage);
    }

    @Override
    public String toString() {
        return "{" +
                "mana=" + mana  +
                ", attackDamage=" + attackDamage +
                ", health=" + health +
                ", description='" + description +
                ", colors=" + colors +
                ", name='" + name +
                '}';
    }
}
