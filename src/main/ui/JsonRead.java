package ui;

import model.*;
import org.json.simple.JSONObject;

import java.util.HashMap;

// https://stackoverflow.com/questions/10926353/how-to-read-json-file-into-java-with-simple-json-library for learning resource.

public class JsonRead {
    private HashMap<String,Card> cardMap = new HashMap<>();
    private HashMap<String,Leader> leaderMap = new HashMap<>();
    private Sort sort = new Sort();
    private GwentCard.Faction faction;
    private GwentCard.Rarity rarity;
    private int provision;
    private int strength;
    private int cardID;
    private String name;
    private Boolean collectible;

    public void loadJsonCards(JSONObject jsonObj) {
        jsonObj.keySet().forEach(keyStr -> {
            JSONObject card = (JSONObject) jsonObj.get(keyStr);
            String cardType =  (String) card.get("cardType");
            loadCriteria(card);
            if (collectible) {
                generateCard(cardType);
                loadJsonLeaders(cardType);
            }
        });
    }

    private void generateCard(String cardType) {
        if (cardType.equals("Unit")) {
            Card c = new UnitCard(name,strength, provision, rarity, cardID, faction);
            addCard(c);
        }
        if (cardType.equals("Artifact")) {
            Card c = new ArtifactCard(name, provision, rarity, cardID, faction);
            addCard(c);
        }
        if (cardType.equals("Spell")) {
            Card c = new SpecialCard(name, provision, rarity, cardID, faction);
            addCard(c);
        }
    }

    private void addCard(Card c) {
        cardMap.put(c.getName(),c);
    }

    private void loadJsonLeaders(String cardType) {
        if (cardType.equals("Leader")) {
            Leader l = new Leader(name, provision, faction, cardID);
            addLeader(l);
        }
    }

    private void addLeader(Leader l) {
        leaderMap.put(l.getName(), l);
    }

    private void loadCriteria(JSONObject card) {
        JSONObject variations = (JSONObject) card.get("variations");
        String inGameID = (String) card.get("ingameId");
        cardID = Integer.parseInt(inGameID);
        JSONObject variation = (JSONObject) variations.get(Integer.toString(cardID) + "00");
        Long cardStrength = (Long) card.get("strength");
        strength = cardStrength.intValue();
        Long cardProvision = (Long) card.get("provision");
        provision = cardProvision.intValue();
        JSONObject cardnames = (JSONObject) card.get("name");
        loadJsonObjectFaction(card);
        loadJsonObjectName(cardnames);
        loadJsonObjectRarity(variation);
    }

    private void loadJsonObjectRarity(JSONObject variation) {
        collectible = (Boolean) variation.get("collectible");
        String rarity = (String) variation.get("rarity");
        if (rarity.equals("Legendary")) {
            this.rarity = GwentCard.Rarity.Legendary;
        }
        if (rarity.equals("Epic")) {
            this.rarity = GwentCard.Rarity.Epic;
        }
        if (rarity.equals("Rare")) {
            this.rarity = GwentCard.Rarity.Rare;
        }
        if (rarity.equals("Common")) {
            this.rarity = GwentCard.Rarity.Common;
        }
    }

    private void loadJsonObjectName(JSONObject cardnames) {
        name = (String) cardnames.get("en-US");
    }

    private void loadJsonObjectFaction(JSONObject card) {
        String faction = (String) card.get("faction");
        setSkellige(faction);
        setNr(faction);
        if (faction.equals("Scoiatael")) {
            this.faction = GwentCard.Faction.Scoiatael;
        }
        if (faction.equals("Syndicate")) {
            this.faction = GwentCard.Faction.Syndicate;
        }
        if (faction.equals("Monster")) {
            this.faction = GwentCard.Faction.Monster;
        }
        if (faction.equals("Nilfgaard")) {
            this.faction = GwentCard.Faction.Nilfgaard;
        }
        if (faction.equals("Neutral")) {
            this.faction = GwentCard.Faction.Neutral;
        }
    }

    private void setNr(String faction) {
        if (faction.equals("Northern Realms")) {
            this.faction = GwentCard.Faction.Northern_Realms;
        }
    }

    private void setSkellige(String faction) {
        if (faction.equals("Skellige")) {
            this.faction = GwentCard.Faction.Skellige;
        }
    }

    public HashMap<String, Card> getCardMap() {
        return sort.sort(cardMap);
    }

    public HashMap<String, Leader> getLeaderMap() {
        return sort.sortLeader(leaderMap);
    }
}
