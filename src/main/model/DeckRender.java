package model;

import ui.Sort;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class DeckRender implements Saveable, Loadable {
//    AllDecks allDecks = new AllDecks();
    private Sort sort = new Sort();
    private Deck deck = new Deck();
    private CardObserver cardObserver = new CardObserver();
    private UnitObserver unitObserver = new UnitObserver();
    private int max;
    private String leaderName = "No leader chosen";
    private List<Card> currentDeck = deck.getDeck();
    private int provisionCounter;
    private HashMap<String,Card> cardLibrary;
    private HashMap<String,Leader> leaderLibrary;
    private String inputPath = "inputfile.txt";
    private String outputPath = "outputfile.txt";

    // REQUIRES: i to be a set of allowed numbers currently accepts: (1)
    // MODIFIES: max
    // EFFECTS: return the leader based on user input
    public void setLeader(Leader leader) {
        deck.setLeader(leader);
        leaderName = leader.getName();
        max = leader.getProvision();
    }

    // REQUIRES: i to be a set of allowed numbers currently accepts: (1)
    // MODIFIES: max
    // EFFECTS: return the leader based on user input
    public List<Card> getCurrentDeck() {
        return sort.sort(currentDeck);
    }


    // MODIFIES: this
    // EFFECTS: return the MAX value based on the value of max
    public int getMax() {
        return max;
    }


    // MODIFIES: this
    // EFFECTS: adds the card to Deck and DeckArray and add the provision
    //          to the provision counter
    public void addCard(Card c) {
        if (!currentDeck.contains(c)) {
            deck.addCard(c);
            provisionCounter(c, true);
            updateObservers(c, true);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the card from Deck and DeckArray and subtracts the provision
    //          from the provision counter
    public void removeCard(Card c) {
        if (currentDeck.contains(c)) {
            deck.removeCard(c);
            provisionCounter(c,false);
            updateObservers(c, false);
        }
    }

    // REQUIRES: provisionCounter > 0
    // MODIFIES: this
    // EFFECTS: updates the Provision Counter based on the Boolean input
    //          adds to the provision Counter if true;
    //          subtracts from the Provision Counter if false;
    //          if over provision, output warning message.
    private void provisionCounter(Card c, Boolean b) {
        if (b) {
            provisionCounter += c.getProvision();
        } else {
            provisionCounter -= c.getProvision();
        }
    }

    // EFFECTS: returns the provisionCounter Value
    public int getProvisionCounter() {
        return provisionCounter;
    }

    public void save() throws IOException {
        PrintWriter writer = new PrintWriter(outputPath,"UTF-8");
        writer.println(deck.getName());
        writer.println(leaderName);
        for (int i = 0; i < getCurrentDeck().size(); i++) {
            writer.println(getCurrentDeck().get(i).getName());
        }
        writer.close();
    }

    public void load() throws IOException {
        String name = Files.readAllLines(Paths.get(inputPath)).get(0);
        deck.setDeckName(name);
        String leadername = Files.readAllLines(Paths.get(inputPath)).get(1);
        setLeader(leaderLibrary.get(leadername));
        List<String> lines = Files.readAllLines(Paths.get(inputPath));
        lines.remove(0);
        lines.remove(0);
        for (String line : lines) {
            addCard(cardLibrary.get(line));
        }
    }

    public Boolean checkfileValid() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(inputPath));
        String leadername = Files.readAllLines(Paths.get(inputPath)).get(1);
        lines.remove(0);
        lines.remove(0);
        if (!leaderLibrary.containsKey(leadername)) {
            return false;
        }
        for (String line : lines) {
            if (!cardLibrary.containsKey(line)) {
                return false;
            }
        }
        return true;
    }

    public void setInputPath(String path) {
        inputPath = path;
    }

    public void setOutputPath(String path) {
        outputPath = path;
    }

    public int getUnitCount() {
        return unitObserver.getUnitCount();
    }

    public int getCardCount() {
        return cardObserver.getCardCount();
    }

    public String getDeckName() {
        return deck.getName();
    }

    public void setDeckName(String deckName) {
        deck.setDeckName(deckName);
    }

    public String getLeaderName() {
        return leaderName;
    }

    private void updateObservers(Card c, Boolean b) {
        cardObserver.update(c, b);
        unitObserver.update(c, b);
    }

    public Leader getLeader() {
        return deck.getLeader();
    }

    public void setCardLibrary(HashMap<String, Card> cardLibrary) {
        this.cardLibrary = cardLibrary;
    }

    public void setLeaderLibrary(HashMap<String, Leader> leaderLibrary) {
        this.leaderLibrary = leaderLibrary;
    }
}
