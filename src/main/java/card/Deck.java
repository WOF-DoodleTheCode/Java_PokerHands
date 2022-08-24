package card;

import java.util.*;
import java.util.stream.Collectors;

public class Deck {

    private final List<String> cards;
    private final Set<String> cardsSet = new HashSet<>();

    public Deck() {
        final String[] cardsArray =  new String[] {
                "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "TC", "JC", "QC", "KC", "AC",
                "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "TD", "JD", "QD", "KD", "AD",
                "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "TH", "JH", "QH", "KH", "AH",
                "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "TS", "JS", "QS", "KS", "AS"
        };
        cards = new LinkedList<>(Arrays.asList(cardsArray));
    }



    public void validate(List<String> cards) {
        final Set<String> duplicateCards = cards.stream()
                .filter(card -> !cardsSet.add(card)).collect(Collectors.toSet());
        if(!duplicateCards.isEmpty()) {
            throw new RuntimeException("Duplicate Card(s): " + duplicateCards);
        }
        final List<String> invalidCards = cards.stream()
                .filter(card -> !this.cards.contains(card)).collect(Collectors.toList());
        if(!invalidCards.isEmpty()) {
            throw new RuntimeException("Invalid Card(s): " + invalidCards);
        }
        this.cards.removeAll(cards);
    }

}
