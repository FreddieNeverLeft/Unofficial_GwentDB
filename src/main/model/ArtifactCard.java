package model;

public class ArtifactCard extends Card {
    public ArtifactCard(String cardName, int provision, Rarity rarity, int cardID, Faction faction) {
        super(cardName, provision, rarity, cardID, faction);
    }

    public String getFileName() {
        return "images/artifacts/" + getName() + "-min.png";
    }
}
