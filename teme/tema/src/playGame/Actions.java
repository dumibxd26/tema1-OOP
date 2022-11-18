package playGame;

import cards.Heros.Hero;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.*;
import cards.*;

import java.util.*;

public class Actions {

    private ObjectMapper mapper;
    private ArrayNode output;
    private utility utility;
    private utilsOutput utilsOutput;
    private int turns = 0;
    private int playerTurn;
    private Player playerOne;
    private Player playerTwo;
    private Player currentPlayer;
    private Player otherPlayer;
    private ArrayList<ArrayList<Minion>> playMatrix;
    private Map<Coordinates, Player> isFrozen;
    private Set<Coordinates> usedAttack;

    private Map<Coordinates, Integer> posInMatrix;

    public Actions(Player playerOne, Player playerTwo, ArrayList<ArrayList<Minion>> playMatrix, int playerTurn,
                   Map<Coordinates, Player> isFrozen, Set<Coordinates> usedAttack, ObjectMapper mapper, ArrayNode output, Map<Coordinates, Integer> posInMatrix) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.playMatrix = playMatrix;
        this.playerTurn = playerTurn;
        this.isFrozen = isFrozen;
        this.usedAttack = usedAttack;
        this.mapper = mapper;
        this.output = output;
        this.posInMatrix = posInMatrix;
//        this.utility = new utility(mapper, output);

        currentPlayer = playerTurn == 1 ? playerOne : playerTwo;
        otherPlayer = playerTurn == 1 ? playerTwo : playerOne;
    }

    public void executeActions(ArrayList<ActionsInput> actionsList) {


        for(ActionsInput action : actionsList) {


            ObjectNode playerCommand = mapper.createObjectNode();

            if (action.getCommand().compareTo("getPlayerDeck") == 0) {
                playerCommand.put("command", action.getCommand());

                playerCommand.put("playerIdx", action.getPlayerIdx());

                if(action.getPlayerIdx() == 1) {
                    playerCommand.put("output", utilsOutput.createDeck(mapper, playerOne.getDeck()));
                    System.out.println(playerOne.getDeck().size());
                } else {
                    playerCommand.put("output", utilsOutput.createDeck(mapper, playerTwo.getDeck()));
                }
            } else if (action.getCommand().compareTo("getPlayerHero") == 0) {
                playerCommand.put("command", action.getCommand());

                playerCommand.put("playerIdx", action.getPlayerIdx());

                if(action.getPlayerIdx() == 1) {
                    playerCommand.put("output", utilsOutput.createHero(mapper, playerOne.getHero()));
                } else {
                    playerCommand.put("output", utilsOutput.createHero(mapper, playerTwo.getHero()));
                }
            } else if (action.getCommand().compareTo("getPlayerTurn") == 0) {
                playerCommand.put("command", action.getCommand());
                playerCommand.put("output", playerTurn);
            } else if (action.getCommand().compareTo("getCardsInHand") == 0) {
                playerCommand.put("command", action.getCommand());

                if (action.getPlayerIdx() == 1) {
                    playerCommand.put("output", utilsOutput.createDeck(mapper, playerOne.getHand()));
                } else {
                    playerCommand.put("output", utilsOutput.createDeck(mapper, playerTwo.getHand()));
                }

                playerCommand.put("playerIdx", action.getPlayerIdx());
            } else if (action.getCommand().compareTo("getCardsOnTable") == 0) {
                playerCommand.put("command", action.getCommand());
                playerCommand.put("output", utilsOutput.createTable(mapper, playMatrix));

            } else if (action.getCommand().compareTo("getCardAtPosition") == 0) {
                playerCommand.put("command", action.getCommand());
                Coordinates set = new Coordinates(action.getX(), action.getY());

                if (utility.checkCardAtPosition(playMatrix, set)){
                    playerCommand.put("output", utilsOutput.createMinion(mapper, utility.getMinionOnTable(playMatrix, set)));
                }

            } else if (action.getCommand().compareTo("getPlayerMana") == 0) {
                playerCommand.put("command", action.getCommand());
                playerCommand.put("playerIdx", action.getPlayerIdx());

                if (action.getPlayerIdx() == 1) {
                    playerCommand.put("output", playerOne.getMana());
                } else {
                    playerCommand.put("output", playerTwo.getMana());
                }

            } else if (action.getCommand().compareTo("getEnvironmentCardsInHand") == 0) {
                playerCommand.put("command", action.getCommand());
                playerCommand.put("playerIdx", action.getPlayerIdx());

                ArrayList<Card> hand;

                if (action.getPlayerIdx() == 1) {
                    hand = playerOne.getHand();
                } else {
                    hand = playerTwo.getHand();
                }

                ArrayList<Environment> envCards = utility.filterEnv(hand);

                playerCommand.put("output", utilsOutput.createEnvDeck(mapper, envCards));

            } else if (action.getCommand().compareTo("getFrozenCardsOnTable") == 0) {
                playerCommand.put("command", action.getCommand());
                ArrayList<Minion> cards = utility.getFrozenCards(isFrozen, playMatrix);

                playerCommand.put("output", utilsOutput.createFrozenCards(mapper, playMatrix, isFrozen));
            } else if (action.getCommand().compareTo("getTotalGamesPlayed") == 0) {
                playerCommand.put("command", action.getCommand());
                playerCommand.put("output", turns);

            } else if (action.getCommand().compareTo("getPlayerOneWins") == 0) {
                playerCommand.put("command", action.getCommand());
                playerCommand.put("output", playerOne.getWins());
            } else if (action.getCommand().compareTo("getPlayerTwoWins") == 0) {
                playerCommand.put("command", action.getCommand());
                playerCommand.put("output", playerTwo.getWins());
            } else if (action.getCommand().compareTo("endPlayerTurn") == 0) {
                playerTurn = (playerTurn == 1) ? 2 : 1;

                turns++;
                if(turns != 0 && turns % 2 == 0) {

                    Iterator<Map.Entry<Coordinates, Player> >
                            iterator = isFrozen.entrySet().iterator();

                    while (iterator.hasNext()) {

                        Map.Entry<Coordinates, Player> entry = iterator.next();

                        if(entry.getValue() == currentPlayer) {
                            iterator.remove();
                        }
                    }
                    if (!playerOne.getDeck().isEmpty()) {
                        playerOne.getHand().add(playerOne.getDeck().remove(0));
                    }
                    if (!playerTwo.getDeck().isEmpty()) {
                        playerTwo.getHand().add(playerTwo.getDeck().remove(0));
                    }

                    playerOne.setMana(playerOne.getMana() + utility.addMana(turns / 2 + 1));
                    playerTwo.setMana(playerTwo.getMana() + utility.addMana(turns / 2 + 1));

                    usedAttack.clear();
                }

                Player temp = currentPlayer;
                currentPlayer = otherPlayer;
                otherPlayer = temp;
            } else if (action.getCommand().compareTo("placeCard") == 0) {

                int index = action.getHandIdx();
                Card card = currentPlayer.getHand().get(index);

                if (utility.checkTableEnvironmentCard(action, mapper, output, card) && utility.checkMana(action, mapper, output, card, currentPlayer)) {

                    ArrayList<Minion> frontRow = utility.getFrontRow(playerTurn, playMatrix);
                    ArrayList<Minion> rearRow = utility.getRearRow(playerTurn, playMatrix);

                    // refactor
                    if (utility.checkFront(card)) {
                        if (!utility.checkRowFull(action, mapper, output, frontRow)) {
                            frontRow.add((Minion) card);
                            currentPlayer.getHand().remove(index);
                            currentPlayer.setMana(currentPlayer.getMana() - card.getMana());

                            int frontRowNumber = playGame.utility.getFrontRowNumber(playerTurn);
                            posInMatrix.put(new Coordinates(action.getX(), action.getY()), frontRow.size() - 1);


                            if(utility.checkIsTank(card)) {
                                currentPlayer.setTanks(currentPlayer.getTanks() + 1);
                            }

                        }
                    } else {
                        if (!utility.checkRowFull(action, mapper, output, rearRow)) {
                            rearRow.add((Minion) card);
                            currentPlayer.getHand().remove(index);
                            currentPlayer.setMana(currentPlayer.getMana() - card.getMana());

                            int rearRowNumber = playGame.utility.getRearRowNumber(playerTurn);
                            posInMatrix.put(new Coordinates(action.getX(), action.getY()), frontRow.size() - 1);

                            if(utility.checkIsTank(card)) {
                                currentPlayer.setTanks(currentPlayer.getTanks() + 1);
                            }

                        }
                    }
                }
            } else if (action.getCommand().compareTo("useEnvironmentCard") == 0) {
                int index = action.getHandIdx();
                int row = action.getAffectedRow();
                Card card = currentPlayer.getHand().get(index);
                if (utility.checkUseEnvironmentCard(action, mapper, output, card) && utility.checkEnvMana(action, mapper, output, currentPlayer, card) && utility.checkEnvOtherplayerrow(action, mapper, output, currentPlayer, row)) {

                    if (utility.checkHeartHound(action, mapper, output, card, playMatrix, row)) {

                        Environment environmentCard = (Environment) card;

                        environmentCard.useAbility(playMatrix, row);

                        currentPlayer.getHand().remove(index);
                        currentPlayer.setMana(currentPlayer.getMana() - card.getMana());
                    }
                }
            } else if (action.getCommand().compareTo("cardUsesAttack") == 0) {

                Coordinates attackedCardCoord = action.getCardAttacked();
                Coordinates attackerCardCoord = action.getCardAttacker();

                if (utility.checkAttackBelongsToEnemy(action, mapper, output, currentPlayer, attackedCardCoord) &&
                        utility.checkAtkAttackedThisTurn(action, mapper, output, usedAttack, attackerCardCoord) &&
                        utility.checkAttackFrozen(action, mapper, output, isFrozen, attackerCardCoord)) {

                    Minion attackedCard = utility.getMinionOnTable(playMatrix, attackedCardCoord);
                    Minion attackerCard = utility.getMinionOnTable(playMatrix, attackerCardCoord);

                    if (utility.checkTankCardAttack(action, mapper, output, playMatrix, otherPlayer, attackedCard)) {

                        attackerCard.attack(attackedCard);

                        if (attackerCard.getHealth() <= 0) {
                           // int deleteRowPos = utility.getPosInMatrixRow(posInMatrix, attackedCardCoord);
                          //  playMatrix.get(attackedCardCoord.getX()).remove(deleteRowPos);
                           // utility.removeFromPosInMatrix(posInMatrix, attackedCardCoord);
                        }

                        usedAttack.add(attackerCardCoord);
                    }
                }
            } else if (action.getCommand().compareTo("cardUsesAbility") == 0) {

                Coordinates attackedCardCoord = action.getCardAttacked();
                Coordinates attackerCardCoord = action.getCardAttacked();

//                Minion attackedCard = utility.getMinionOnTable(playMatrix, attackedCardCoord);
//                Minion attackerCard = utility.getMinionOnTable(playMatrix, attackerCardCoord);

                if (    utility.checkAttackedThisTurn(usedAttack, attackerCardCoord) &&
                        utility.checkFrozen(isFrozen, attackerCardCoord)) {

                    Minion attackedCard = utility.getMinionOnTable(playMatrix, attackedCardCoord);
                    Minion attackerCard = utility.getMinionOnTable(playMatrix, attackerCardCoord);

//                    if (attackerCard.getName().compareTo("Disciple") == 0 &&
//                            utility.checkBelongsToCurrent(currentPlayer, attackedCardCoord)) {
//                            SpecialMinion attackerMinion = (SpecialMinion) attackedCard;
//                            attackerMinion.useAbility(playMatrix, attackedCardCoord);
//                    } else if (utility.checkBelongsToEnemy(otherPlayer, attackedCardCoord) &&
//                            utility.checkTankCardAttack(playMatrix, otherPlayer, attackedCard)){
//                        SpecialMinion attackerMinion = (SpecialMinion) attackedCard;
//                        attackerMinion.useAbility(playMatrix, attackedCardCoord);
//                    }
                }
            } else if (action.getCommand().compareTo("useAttackHero") == 0) {

                Coordinates attackerCardCoord = action.getCardAttacked();

                if (utility.checkFrozen(isFrozen, attackerCardCoord) &&
                    utility.checkAttackedThisTurn(usedAttack, attackerCardCoord) &&
                    utility.checkTankOnTable(playMatrix, otherPlayer)) {

                    Minion attackerCard = utility.getMinionOnTable(playMatrix, attackerCardCoord);
                    attackerCard.attack(otherPlayer.getHero());

                    if(otherPlayer.getHero().getHealth() <= 0) {
                        break;
                    }
                }
            } else if (action.getCommand().compareTo("useHeroAbility") == 0) {

                int row = action.getAffectedRow();
                Hero hero = currentPlayer.getHero();

                if (utility.checkHeroMana(mapper, output, hero, currentPlayer) && utility.checkHeroAttacked(hero)) {

                    if (utility.checkHasToApplyOwnRow(hero)) {
                        if (!utility.checkOtherPlayerRow(currentPlayer, row)) {
                            hero.useAbility(playMatrix, row);
                        }
                    } else {
                        if (utility.checkOtherPlayerRow(currentPlayer, row)) {
                            hero.useAbility(playMatrix, row);
                        }
                    }
                }
            }
           // System.out.println(playerCommand);
            if(playerCommand.size() > 0) {
                output.addAll(Arrays.asList(playerCommand));
            }
    //        if (action.getCommand().compareTo("endPlayerTurn") == 0)
//            if (currentPlayer == playerOne) {
//                System.out.println("PULAMEA");
//                System.out.println(currentPlayer.getDeck().size());
//            }
//            System.out.println(output);
        }


    }

}