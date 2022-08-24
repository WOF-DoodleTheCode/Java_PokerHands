package play;

import card.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static card.HandStrength.*;

public class PlayPoker {

    private final Deck deck = new Deck();

    public Winner play(final String player1, final PlayerHand playerHand1,
                       final String player2, final PlayerHand playerHand2) {
        Winner winner;
        final HandStrength player1HandStrength = evaluate(playerHand1);
        final HandStrength player2HandStrength = evaluate(playerHand2);
        if(player1HandStrength == player2HandStrength) {
            final SameHandStrengthEvaluator evaluator = new SameHandStrengthEvaluator(
                    player1HandStrength, playerHand1, playerHand2);
            final PlayerHand strongHand = evaluator.strongHand();
            if(strongHand == playerHand1) {
                winner = new Winner(player1, player1HandStrength, playerHand1.getHighCard());
            } else if(strongHand == playerHand2) {
                winner = new Winner(player2, player2HandStrength, playerHand2.getHighCard());
            } else {
                winner = new Winner(player1HandStrength);
            }
        } else if(player1HandStrength.ordinal() < player2HandStrength.ordinal()) {
            winner = new Winner(player1, player1HandStrength);
        } else {
            winner = new Winner(player2, player2HandStrength);
        }
        return winner;
    }

    public boolean validate(final PlayerHand playerHand1, final PlayerHand playerHand2) {
        final List<String> player1Cards = playerHand1.getCards().stream().map(Card::getCard).collect(Collectors.toList());
        final List<String> player2Cards = playerHand2.getCards().stream().map(Card::getCard).collect(Collectors.toList());
        deck.validate(player1Cards);
        deck.validate(player2Cards);
        return true;
    }

    public HandStrength evaluate(final PlayerHand playerHand) {
        HandStrength handStrength;
        HandStrength result = null;
        final List<Card> cards = playerHand.getCards();
        int currentValue;
        int previousValue = 0;
        int decrementValue = 0;
        char currentSuit;
        char previousSuit = 0;
        for(final Card card : cards) {
            currentSuit = card.getSuit();
            currentValue = card.getNumber();
            if(previousValue == 0 || (currentSuit == previousSuit && decrementValue == currentValue)) {
                previousSuit = currentSuit;
                previousValue = currentValue;
            } else {
                result = HIGH_CARD;
                break;
            }
            decrementValue = currentValue - 1;
        }
        if (result == null) {
            result = STRAIGHT_FLUSH;
        }
        final Map<Character, Integer> valueCounts = playerHand.getValueCounts();
        final Map<Character, Integer> valueCounts1 = playerHand.getValueCounts();
        HandStrength result1 = null;
        final List<Card> cards1 = playerHand.getCards();
        char currentSuit1;
        char previousSuit1 = 0;
        for(final Card card : cards1) {
            currentSuit1 = card.getSuit();
            if(previousSuit1 == 0 || (currentSuit1 == previousSuit1)) {
                previousSuit1 = currentSuit1;
            } else {
                result1 = HIGH_CARD;
                break;
            }
        }
        if (result1 == null) {
            result1 = FLUSH;
        }
        HandStrength result2 = null;
        final List<Card> cards2 = playerHand.getCards();
        int currentValue1;
        int previousValue1 = 0;
        int decrementValue1 = 0;
        for(final Card card : cards2) {
            currentValue1 = card.getNumber();
            if(previousValue1 == 0 || decrementValue1 == currentValue1) {
                previousValue1 = currentValue1;
            } else {
                result2 = HIGH_CARD;
                break;
            }
            decrementValue1 = currentValue1 - 1;
        }
        if (result2 == null) {
            result2 = STRAIGHT;
        }
        final Map<Character, Integer> valueCounts2 = playerHand.getValueCounts();
        final Map<Character, Integer> valueCounts3 = playerHand.getValueCounts();
        final Map<Character, Integer> valueCounts4 = playerHand.getValueCounts();
        if(STRAIGHT_FLUSH == result) {
            handStrength = STRAIGHT_FLUSH;
        } else if(FOUR_OF_A_KIND == (valueCounts.containsValue(4) ? FOUR_OF_A_KIND : HIGH_CARD)) {
            handStrength = FOUR_OF_A_KIND;
        } else if(FULL_HOUSE == (valueCounts1.containsValue(3) && valueCounts1.containsValue(2) ? FULL_HOUSE : HIGH_CARD)) {
            handStrength = FULL_HOUSE;
        } else if(FLUSH == result1) {
            handStrength = FLUSH;
        } else if(STRAIGHT == result2) {
            handStrength = STRAIGHT;
        } else if(THREE_OF_A_KIND == (valueCounts2.containsValue(3) ? THREE_OF_A_KIND : HIGH_CARD)) {
            handStrength = THREE_OF_A_KIND;
        } else if(TWO_PAIRS == (valueCounts3.containsValue(2) && valueCounts3.size() == 3 ? TWO_PAIRS : HIGH_CARD)) {
            handStrength = TWO_PAIRS;
        } else if(PAIR == (valueCounts4.containsValue(2) && valueCounts4.size() == 4 ? PAIR : HIGH_CARD)) {
            handStrength = PAIR;
        } else {
            handStrength = HIGH_CARD;
        }
        return handStrength;
    }

}
