package model;

public class SpecialCard extends Card {
    public SpecialCard(String cardName, int provision, Rarity rarity, int cardID, Faction faction) {
        super(cardName,  provision, rarity, cardID, faction);
    }

    public String getFileName() {
        return "images/specials/" + getName() + "-min.png";
    }
}
