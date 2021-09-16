package model;

public interface GwentCard {
    enum Faction {
        Monster, Northern_Realms, Syndicate, Nilfgaard, Skellige, Scoiatael, Neutral
    }

    enum Rarity {
        Common, Rare, Epic, Legendary
    }

    // EFFECTS: get the provision value of a card
    int getProvision();

    // EFFECTS: gets name of card
    String getName();

    // EFFECTS: gets the card ID
    int getCardID();

    // EFFECTS: gets the file name for the card image;
    String getFileName();
}
