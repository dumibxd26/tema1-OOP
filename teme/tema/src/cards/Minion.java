package cards;

import fileio.Coordinates;

import java.util.ArrayList;

import playGame.utility;
public class Minion extends CardWithHealth{

    private int attackDamage;

    private int positionOnRow;

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

    public int getPositionOnRow() {
        return positionOnRow;
    }

    public void setPositionOnRow(int positionOnRow) {
        this.positionOnRow = positionOnRow;
    }

    @Override
    public String toString() {
        return "{" +
                "mana=" + this.getMana()  +
                ", attackDamage=" + attackDamage +
                ", health=" + health +
                ", description='" + this.getDescription() +
                ", colors=" + this.getColors() +
                ", name='" + this.getName() +
                '}';
    }
}
