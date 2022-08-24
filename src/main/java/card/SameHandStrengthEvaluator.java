package card;

import java.util.List;
import java.util.Map;

import static card.HandStrength.*;


public class SameHandStrengthEvaluator {

    private final HandStrength handStrength;
    private final PlayerHand playerHand1;
    private final PlayerHand playerHand2;

    public SameHandStrengthEvaluator(final HandStrength handStrength,
                                     final PlayerHand playerHand1, final PlayerHand playerHand2) {
        this.handStrength = handStrength;
        this.playerHand1 = playerHand1;
        this.playerHand2 = playerHand2;
    }

    public PlayerHand strongHand() {
        PlayerHand strongHand = null;
        if(PAIR == handStrength
                || TWO_PAIRS == handStrength
                || THREE_OF_A_KIND == handStrength
                || FULL_HOUSE == handStrength
                || FOUR_OF_A_KIND == handStrength) {
            PlayerHand strongHand1 = null;
            final Map<Character, Integer> player1ValueCounts = playerHand1.getValueCounts();
            final Map<Character, Integer> player2ValueCounts = playerHand2.getValueCounts();
            boolean iterate = true;
            while(iterate && player1ValueCounts.size() > 0) {
                final Character player1Value = player1ValueCounts.keySet().stream().findFirst().get();
                final Character player2Value = player2ValueCounts.keySet().stream().findFirst().get();
                final int player1Number = number(player1Value);
                final int player2Number = number(player2Value);
                if(player1Number == player2Number) {
                    player1ValueCounts.remove(player1Value);
                    player2ValueCounts.remove(player2Value);
                } else if(player1Number > player2Number) {
                    playerHand1.setHighCard(player1Value);
                    strongHand1 = playerHand1;
                    iterate = false;
                } else {
                    playerHand2.setHighCard(player2Value);
                    strongHand1 = playerHand2;
                    iterate = false;
                }
            }
            strongHand = strongHand1;
        } else if(HIGH_CARD == handStrength
                || STRAIGHT == handStrength
                || FLUSH == handStrength
                || STRAIGHT_FLUSH == handStrength) {
            PlayerHand strongHand1 = null;
            List<Card> player1Cards = playerHand1.getCards();
            List<Card> player2Cards = playerHand2.getCards();
            for(int i = 0; i < player1Cards.size(); i++) {
                Card player1Card = player1Cards.get(i);
                Card player2Card = player2Cards.get(i);
                if(player1Card.getNumber() > player2Card.getNumber()) {
                    playerHand1.setHighCard(player1Card.getValue());
                    strongHand1 = playerHand1;
                    break;
                } else if(player2Card.getNumber() > player1Card.getNumber()) {
                    playerHand2.setHighCard(player2Card.getValue());
                    strongHand1 = playerHand2;
                    break;
                }
            }
            strongHand = strongHand1;
        }
        return strongHand;
    }

    private int number(final char cardValue) {
        switch (cardValue) {
            case 'T': return 10;
            case 'J': return 11;
            case 'Q': return 12;
            case 'K': return 13;
            case 'A': return 14;
            default : return Integer.parseInt(String.valueOf(cardValue));
        }
    }

}
