package model;

public abstract class Card implements GwentCard {
    private String cardName;
    private Rarity rarity;
    private int cardID;
    private Faction faction;
    private int provision;


    // MODIFIES: this
    // EFFECTS: Constructs the card and initializing its Value, Provision,
    //          Rarity and CardID.
    public Card(String cardName, int provision, Rarity rarity, int cardID, Faction faction) {
        this.faction = faction;
        this.cardName = cardName;
        this.provision = provision;
        this.rarity = rarity;
        this.cardID = cardID;
    }


    // EFFECTS: get the provision value of a card
    public int getProvision() {
        return provision;
    }

    // EFFECTS: gets name of card
    public String getName() {
        return cardName;
    }

    // EFFECTS: gets the card ID
    public int getCardID() {
        return cardID;
    }

    // EFFECTS: gets the rarity
    public Rarity getRarity() {
        return rarity;
    }

    public Faction getFaction() {
        return faction;
    }

    public String getUrl() {
        return "https://firebasestorage.googleapis.com/v0/b/gwent-9e62a.appspot.com/o/images%2Fv3.1.0%2F"
                + cardID + "%2F" + cardID + "00%2Fthumbnail.png?alt=media";
    }

}
