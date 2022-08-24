package play;

import card.HandStrength;

import java.text.MessageFormat;

public class Winner {


    private final String name;
    private final HandStrength handStrength;
    private final char highCard;

    public Winner(String name, HandStrength handStrength, char highCard) {
        this.name = name;
        this.handStrength = handStrength;
        this.highCard = highCard;
    }

    public Winner(String name, HandStrength handStrength) {
        this(name, handStrength, ' ');
    }

    public Winner(HandStrength handStrength) {
        this("", handStrength);
    }

    public String getName() {
        return name;
    }

    public HandStrength getHandStrength() {
        return handStrength;
    }

    public char getHighCard() {
        return highCard;
    }

    @Override
    public String toString() {
        if(name.equals(""))
            return "Tie";
        if(highCard == ' ')
            return MessageFormat.format("{0} wins - {1}", name, handStrength.name());
        return MessageFormat.format("{0} wins - {1}: {2}", name, handStrength.name(), highCard);
    }
}
