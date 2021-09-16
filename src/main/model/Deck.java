package model;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private String name;
    private Leader leader;
    private List<Card> deck;

    public Deck() {
        this.name = "Untitled";
        this.deck = new ArrayList<>();
        this.leader = null;
    }

    public void setDeckName(String name) {
        this.name = name;
    }

    public void setDeck(List<Card> inputDeck) {
        deck = inputDeck;
    }

    public void addCard(Card c) {
        if (!deck.contains(c)) {
            deck.add(c);
        }
    }


    // MODIFIES: this
    // EFFECTS: removes the card from Deck and DeckArray and subtracts the provision
    //          from the provision counter
    public void removeCard(Card c) {
        if (deck.contains(c)) {
            deck.remove(c);
        }
    }

    public List<Card> getDeck() {
        return deck;
    }

    public String getName() {
        return name;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public Leader getLeader() {
        return leader;
    }

}
