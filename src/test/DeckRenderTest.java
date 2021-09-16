import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class DeckRenderTest {
    private DeckRender deckRender;
    private Card geraltOfRivia = new UnitCard("Geralt of Rivia", 3, 10, Card.Rarity.Legendary, 0000, Card.Faction.Neutral);
    private Card roach = new UnitCard("Roach", 3, 10 , Card.Rarity.Epic, 0001, Card.Faction.Neutral);
    private Card avallach = new UnitCard("Avallach", 5, 8, Card.Rarity.Legendary, 0005, Card.Faction.Neutral);
    private Leader kob = new Leader("King of Beggars",
            15, GwentCard.Faction.Syndicate, 01);
    private HashMap<String, Card> cardHashMap = new HashMap<>();
    private HashMap<String, Leader> leaderHashMap = new HashMap<>();

    @BeforeEach
    void beforeEachTest(){
        deckRender = new DeckRender();
        cardHashMap.put(geraltOfRivia.getName(), geraltOfRivia);
        cardHashMap.put(roach.getName(), roach);
        cardHashMap.put(avallach.getName(), avallach);
        leaderHashMap.put(kob.getName(), kob);
        deckRender.setLeaderLibrary(leaderHashMap);
        deckRender.setCardLibrary(cardHashMap);
    }

    // Check the add card feature by adding a single card
    @Test
    void testAddOne() {
        deckRender.addCard(roach);
        Assertions.assertTrue(deckRender.getCurrentDeck().contains(roach));
        Assertions.assertEquals(1, deckRender.getCurrentDeck().size());
    }

    // Check the add card feature by adding a two cards
    @Test
    void testAddTwo() {
        deckRender.addCard(roach);
        deckRender.addCard(geraltOfRivia);
        Assertions.assertTrue(deckRender.getCurrentDeck().contains(roach));
        Assertions.assertTrue(deckRender.getCurrentDeck().contains(geraltOfRivia));
        Assertions.assertEquals(2, deckRender.getCurrentDeck().size());
    }

    // Check the add card feature by adding duplicates
    @Test
    void testAddRemoveDuplicate() {
        deckRender.addCard(roach);
        deckRender.addCard(roach);
        deckRender.removeCard(geraltOfRivia);
        Assertions.assertTrue(deckRender.getCurrentDeck().contains(roach));
        Assertions.assertFalse(deckRender.getCurrentDeck().contains(geraltOfRivia));
        Assertions.assertEquals(1, deckRender.getCurrentDeck().size());
    }

    // Check the remove card feature by removing a card from the list
    @Test
    void testRemoveOne() {
        deckRender.addCard(roach);
        deckRender.removeCard(roach);
        Assertions.assertFalse(deckRender.getCurrentDeck().contains(geraltOfRivia));
        Assertions.assertFalse(deckRender.getCurrentDeck().contains(roach));
        Assertions.assertEquals(0, deckRender.getCurrentDeck().size());
    }

    // Check the remove card feature by removing the wrong card from the list
    @Test
    void testRemoveWrong() {
        deckRender.addCard(roach);
        deckRender.addCard(avallach);
        deckRender.removeCard(geraltOfRivia);
        Assertions.assertFalse(deckRender.getCurrentDeck().contains(geraltOfRivia));
        Assertions.assertTrue(deckRender.getCurrentDeck().contains(roach));
        Assertions.assertEquals(2, deckRender.getCurrentDeck().size());
    }

    // Check the Provision counter if it functions
    @Test
    void testProvisionCounter() {
        deckRender.addCard(roach);
        Assertions.assertEquals(10, deckRender.getProvisionCounter());
    }

    @Test
    void testLoad() throws IOException {
        deckRender.setOutputPath("inputfile.txt");
        deckRender.setDeckName("lol");
        deckRender.setLeader(kob);
        deckRender.addCard(geraltOfRivia);
        deckRender.addCard(roach);
        deckRender.addCard(avallach);
        deckRender.save();
        deckRender.setInputPath("inputfile.txt");
        deckRender.load();
        assertEquals(3, deckRender.getCurrentDeck().size());
        assertTrue(deckRender.getCurrentDeck().contains(roach));
        assertTrue(deckRender.getCurrentDeck().contains(geraltOfRivia));
        assertTrue(deckRender.getCurrentDeck().contains(avallach));
        assertEquals(3, deckRender.getUnitCount());
    }

    @Test
    void testSaveLoad() throws IOException {
        deckRender.setOutputPath("inputfile.txt");
        deckRender.setDeckName("lol");
        deckRender.setLeader(kob);
        deckRender.addCard(geraltOfRivia);
        deckRender.addCard(roach);
        deckRender.addCard(avallach);
        deckRender.save();
        deckRender.setInputPath("inputfile.txt");
        deckRender.load();
        assertEquals(165, deckRender.getMax());
        assertEquals(kob, deckRender.getLeader());
        assertEquals(kob.getName(), deckRender.getLeaderName());
        assertEquals(3, deckRender.getCardCount());
        assertTrue(deckRender.getCurrentDeck().contains(roach));;
        assertTrue(deckRender.getCurrentDeck().contains(geraltOfRivia));
        assertTrue(deckRender.getCurrentDeck().contains(avallach));
        deckRender.setOutputPath("outputfile.txt");
        deckRender.save();
        deckRender = new DeckRender();
        deckRender.setCardLibrary(cardHashMap);
        deckRender.setLeaderLibrary(leaderHashMap);
        deckRender.setInputPath("outputfile.txt");
        deckRender.load();
        assertEquals(3, deckRender.getCurrentDeck().size());
        assertTrue(deckRender.getCurrentDeck().contains(roach));
        assertTrue(deckRender.getCurrentDeck().contains(geraltOfRivia));
        assertTrue(deckRender.getCurrentDeck().contains(avallach));
    }

    @Test
    void changeNameTest() throws IOException {
        assertEquals("Untitled", deckRender.getDeckName());
        deckRender.setOutputPath("inputfile.txt");
        deckRender.setDeckName("lol");
        deckRender.setLeader(kob);
        deckRender.addCard(geraltOfRivia);
        deckRender.addCard(roach);
        deckRender.addCard(avallach);
        deckRender.save();
        deckRender.setInputPath("inputfile.txt");
        assertTrue(deckRender.checkfileValid());
         deckRender.load();
        assertEquals("lol", deckRender.getDeckName());
        deckRender = new DeckRender();
        assertEquals("Untitled", deckRender.getDeckName());
        deckRender.setDeckName("sike");
        assertEquals("sike", deckRender.getDeckName());
    }

    @Test
    void loadWrongLeaderTest() throws IOException {
        deckRender.setDeckName("lol");
        deckRender.setLeader(kob);
        deckRender.addCard(geraltOfRivia);
        deckRender.addCard(roach);
        deckRender.addCard(avallach);
        PrintWriter writer = new PrintWriter("inputfile_wrong.txt","UTF-8");
        writer.println(deckRender.getDeckName());
        writer.println("King of Begg");
        for (int i = 0; i < deckRender.getCurrentDeck().size(); i++) {
            writer.println(deckRender.getCurrentDeck().get(i).getName());
        }
        writer.close();
        deckRender.setInputPath("inputfile_wrong.txt");
        assertFalse(deckRender.checkfileValid());
        PrintWriter writertwo = new PrintWriter("inputfile_wrong_1.txt","UTF-8");
        writertwo.println(deckRender.getDeckName());
        writertwo.println("King of Beggars");
        for (int i = 0; i < deckRender.getCurrentDeck().size() - 1; i++) {
            writertwo.println(deckRender.getCurrentDeck().get(i).getName());
        }
        writertwo.println("Triss");
        writertwo.close();
        deckRender.setInputPath("inputfile_wrong_1.txt");
        assertFalse(deckRender.checkfileValid());
    }

}

