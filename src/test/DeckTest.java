import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeckTest {
    private Leader leader = new Leader("King of Beggars",
                                15,GwentCard.Faction.Syndicate, 01);
    private Card avallach = new UnitCard("Avallach", 5, 8, Card.Rarity.Legendary, 0005, Card.Faction.Neutral);
    private Card vesemir = new UnitCard("Vesemir", 2, 8, Card.Rarity.Epic, 0006, Card.Faction.Neutral);
    private Card zoltanChivay = new UnitCard("Zoltan Chivay", 5, 8, Card.Rarity.Legendary, 0007, Card.Faction.Scoiatael);
    private Card hgs = new ArtifactCard("Hen Gaidth Sword",9, Card.Rarity.Legendary,00010, GwentCard.Faction.Neutral);
    private Deck deck;

    private List<Card> newDeck = new ArrayList<>();

    @BeforeEach
    void runBefore() {
        deck = new Deck();
    }

    @Test
    void setLeaderandNameTest() {
        deck.setLeader(leader);
        assertTrue(deck.getLeader().equals(leader));
        assertEquals("Untitled", deck.getName());
        deck.setDeckName("KoB");
        assertEquals("KoB", deck.getName());
    }

    @Test
    void addOneCardTest() {
        deck.addCard(vesemir);
        assertTrue(deck.getDeck().contains(vesemir));
        assertFalse(deck.getDeck().contains(zoltanChivay));
    }

    @Test
    void addTwoCardsandRemoveOneTest() {
        deck.addCard(vesemir);
        deck.addCard(avallach);
        assertTrue(deck.getDeck().contains(vesemir));
        assertTrue(deck.getDeck().contains(avallach));
        assertFalse(deck.getDeck().contains(zoltanChivay));
        deck.removeCard(avallach);
        assertFalse(deck.getDeck().contains(avallach));
    }

    @Test
    void addDeckandsetNewDeckTest() {
        newDeck.add(zoltanChivay);
        newDeck.add(hgs);
        deck.setDeck(newDeck);
        assertEquals(2, deck.getDeck().size());
    }

    @Test
    void addandRemoveTwoCardsSameTest() {
        deck.addCard(vesemir);
        deck.addCard(vesemir);
        assertTrue(deck.getDeck().contains(vesemir));
        assertEquals(1, deck.getDeck().size());
        deck.removeCard(zoltanChivay);
        assertEquals(1, deck.getDeck().size());
        assertFalse(deck.getDeck().contains(zoltanChivay));
        deck.removeCard(vesemir);
        assertEquals(0, deck.getDeck().size());
        deck.removeCard(vesemir);
        assertEquals(0, deck.getDeck().size());
    }
}
