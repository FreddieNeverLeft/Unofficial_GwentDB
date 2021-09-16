package model;

public class Leader implements GwentCard {
    private String leaderName;
    private int provision;
    private Faction faction;
    private int leaderID;

    // MODIFIES: this
    // EFFECTS: constructs a Leader with given name, provision amount and Faction.
    public Leader(String leaderName, int provision, Faction faction, int leaderID) {
        this.leaderName = leaderName;
        this.provision = provision;
        this.faction = faction;
        this.leaderID = leaderID;
    }

    // REQUIRES: provision > 0
    // MODIFIES: this and MaxProvision
    // EFFECTS: add 150 to the provision to get the MaxProvision value and return MaxProvision
    public int getProvision() {
        int maxProvision;
        maxProvision = 150 + provision;
        return maxProvision;
    }



    // EFFECTS: gets name of card
    public String getName() {
        return leaderName;
    }

    // EFFECTS: gets the card ID
    public int getCardID() {
        return leaderID;
    }

    public Faction getFaction() {
        return faction;
    }

    public String getFileName() {
        return "images/leaders/" + getName() + "-min.png";
    }

    public String getUrl() {
        return "https://firebasestorage.googleapis.com/v0/b/gwent-9e62a.appspot.com/o/images%2Fv3.1.0%2F"
                + leaderID + "%2F" + + leaderID +  "00%2Fthumbnail.png?alt=media";
    }
}
