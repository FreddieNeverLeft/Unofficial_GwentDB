package model;

public class UnitCard extends Card {
    private int cardValue;

    public UnitCard(String cardName, int cardValue, int provision, Rarity rarity, int cardID, Faction faction) {
        super(cardName, provision, rarity, cardID, faction);
        this.cardValue = cardValue;
    }

    public String getFileName() {
        return "images/units/" + getName() + "-min.png";
    }

    //EFFECTS: gets the value
    public int getValue() {
        return cardValue;
    }
}
