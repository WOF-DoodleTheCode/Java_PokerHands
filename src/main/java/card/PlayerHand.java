package card;

import java.util.*;

public class PlayerHand {

    private final List<Card> cards = new ArrayList<>(5);
    private Map<Character, Integer> valueCounts;
    private char highCard;

    public PlayerHand(final String playerHand) {
        final String[] cardsArray = playerHand.split(" ");
        final List<String> cardsList = Arrays.asList(cardsArray);
        new Deck().validate(cardsList);
        for (String card1 : cardsList) {
            cards.add(new Card(card1));
        }
        final Comparator<Card> cardComparator = Comparator.comparing(Card::getNumber);
        final Comparator<Card> cardComparatorReversed = cardComparator.reversed();
        cards.sort(cardComparatorReversed);
        final Map<Character, Integer> unorderedValueCounts = new LinkedHashMap<>();
        for (Card card : cards) {
            unorderedValueCounts.put(
                    card.getValue(), unorderedValueCounts.getOrDefault(card.getValue(), 0) + 1);
        }
        List<Map.Entry<Character, Integer>> toSort = new ArrayList<>();
        for (Map.Entry<Character, Integer> characterIntegerEntry : unorderedValueCounts.entrySet()) {
            toSort.add(characterIntegerEntry);
        }
        toSort.sort(Map.Entry.<Character, Integer>comparingByValue().reversed());
        LinkedHashMap<Character, Integer> map = new LinkedHashMap<>();
        for (Map.Entry<Character, Integer> characterIntegerEntry : toSort) {
            map.putIfAbsent(characterIntegerEntry.getKey(), characterIntegerEntry.getValue());
        }
        valueCounts = map;
    }

    public List<Card> getCards() {
        return cards;
    }

    public Map<Character, Integer> getValueCounts() {
        return valueCounts;
    }

    public char getHighCard() {
        return highCard;
    }

    public void setHighCard(final char highCard) {
        this.highCard = highCard;
    }

}
