package card;

public class Card {
    private final String card;
    private final char suit;
    private final char value;
    private final int number;

    public Card(final String card) {
        this.card = card;
        this.suit = card.charAt(1);
        this.value = card.charAt(0);
        this.number = number(value);
    }

    public String getCard() {
        return card;
    }

    public char getSuit() {
        return suit;
    }

    public char getValue() {
        return value;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return card + ": " + suit + " " + value + " (" + number + ")";
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
