package play;

import card.HandStrength;
import card.PlayerHand;
import org.junit.jupiter.api.Test;

import static card.HandStrength.*;
import static org.junit.jupiter.api.Assertions.*;

class PlayPokerTest {

    private static final String PLAYER1 = "Jack";
    private static final String PLAYER2 = "Jim";

    private final PlayPoker playPoker = new PlayPoker();

    private Winner assertPlayPoker(final String player1Cards, final String player2Cards,
                                   final String winnerName, final HandStrength winnerHandStrength, final char winnerHighCard) {
        final PlayerHand playerHand1 = new PlayerHand(player1Cards);
        final PlayerHand playerHand2 = new PlayerHand(player2Cards);
        final Winner winner = playPoker.play(PLAYER1, playerHand1, PLAYER2, playerHand2);
        assertEquals(winnerName, winner.getName());
        assertEquals(winnerHandStrength, winner.getHandStrength());
        assertEquals(winnerHighCard, winner.getHighCard());
        return winner;
    }

    @Test
    void testValidCards() {
        final PlayerHand playerHand1 = new PlayerHand("2H 3D 5S 9C KD");
        final PlayerHand playerHand2 = new PlayerHand("2C 3H 4S 8C AH");
        assertTrue(playPoker.validate(playerHand1, playerHand2));
    }

    @Test
    void testInvalidCardsfromTheFirstPlayer() {
        try {
            final PlayerHand playerHand1 = new PlayerHand("2H 3D HH 9C 178");
            final PlayerHand playerHand2 = new PlayerHand("2C 3H 4S 8C AH");
            playPoker.validate(playerHand1, playerHand2);
        } catch (Exception e) {
            assertEquals("Invalid Card(s): [HH, 178]", e.getMessage());
        }
    }

    @Test
    void testInvalidCardsfromTheSecondPlayer() {
        try {
            final PlayerHand playerHand1 = new PlayerHand("2H 3D 4D 9C 6S");
            final PlayerHand playerHand2 = new PlayerHand("FG 3H 4S 8C SSS");
            playPoker.validate(playerHand1, playerHand2);
        } catch (Exception e) {
            assertEquals("Invalid Card(s): [FG, SSS]", e.getMessage());
        }
    }

    @Test
    void testDuplicateCardFromTheFirstPlayer() {
        try {
            final PlayerHand playerHand1 = new PlayerHand("2H 3D 4D 9C 9C");
            final PlayerHand playerHand2 = new PlayerHand("AD 3H 4S 8C KS");
            playPoker.validate(playerHand1, playerHand2);
        } catch (Exception e) {
            assertEquals("Duplicate Card(s): [9C]", e.getMessage());
        }
    }

    @Test
    void testDuplicateCardsFromTheSecondPlayer() {
        try {
            final PlayerHand playerHand1 = new PlayerHand("2H 3D 4D 9C 9H");
            final PlayerHand playerHand2 = new PlayerHand("AD AD KS 8C KS");
            playPoker.validate(playerHand1, playerHand2);
        } catch (Exception e) {
            assertEquals("Duplicate Card(s): [AD, KS]", e.getMessage());
        }
    }

    @Test
    void testDuplicateCardsBetweenThePlayers() {
        try {
            final PlayerHand playerHand1 = new PlayerHand("2H AD 4D 9C 9H");
            final PlayerHand playerHand2 = new PlayerHand("AD 9C KS 8C 9H");
            playPoker.validate(playerHand1, playerHand2);
        } catch (Exception e) {
            assertEquals("Duplicate Card(s): [AD, 9C, 9H]", e.getMessage());
        }
    }

    @Test
    void testPlay1() {
        final Winner winner = assertPlayPoker("2H 3D 5S 9C KD", "2C 3H 4S 8C AH", PLAYER2, HIGH_CARD, 'A');
        assertEquals("Jim wins - HIGH_CARD: A", winner.toString());
    }

    @Test
    void testPlay2() {
        final Winner winner = assertPlayPoker("2H 4S 4C 2D 4H", "2S 8S AS QS 3S", PLAYER1, FULL_HOUSE, ' ');
        assertEquals("Jack wins - FULL_HOUSE", winner.toString());
    }

    @Test
    void testPlay3() {
        final Winner winner = assertPlayPoker("2H 3D 5S 9C KD", "2C 3H 4S 8C KH", PLAYER1, HIGH_CARD, '9');
        assertEquals("Jack wins - HIGH_CARD: 9", winner.toString());
    }

    @Test
    void testPlay4() {
        final Winner winner = assertPlayPoker("2H 3D 5S 9C KD", "2D 3H 5C 9S KH", "", HIGH_CARD, ' ');
        assertEquals("Tie", winner.toString());
    }

    @Test
    void testPlay5() {
        final Winner winner = assertPlayPoker("2H 3D 5S 9C KD", "2D 3H 4C 6S 5H", PLAYER2, STRAIGHT, ' ');
        assertEquals("Jim wins - STRAIGHT", winner.toString());
    }

}