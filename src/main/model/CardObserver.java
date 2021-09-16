package model;

public class CardObserver implements DeckObserver {
    private int cardCount;

    public CardObserver() {
        cardCount = 0;
    }

    @Override
    public void update(Card c, Boolean b) {
        if (b) {
            cardCount++;
        } else {
            cardCount--;
        }
    }

    public int getCardCount() {
        return cardCount;
    }
}
