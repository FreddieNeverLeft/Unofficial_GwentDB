package ui;

import model.*;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Filter {
    private HashMap<String, Leader> allLeaders;
    private HashMap<String, Card> allDeck;
    private HashMap<String, Leader> currentLeaders;
    private HashMap<String, Card> currentDeck;
    private Boolean leaderisChosen = false;
    private String nameSearch = "";
    private GwentCard.Faction leaderFaction = GwentCard.Faction.Neutral;
    private FactionSelect factionSelect = FactionSelect.FactionAll;
    private ColorSelect colorSelect = ColorSelect.ColorALL;
    private ProvisionSelect provisionSelect = ProvisionSelect.ProvAll;
    private TypeSelect typeSelect = TypeSelect.TypeAll;
    private RaritySelect raritySelect = RaritySelect.RarityAll;

    public enum FactionSelect {
        FactionAll(GwentCard.Faction.Neutral),
        FactionMO(GwentCard.Faction.Monster),
        FactionNR(GwentCard.Faction.Northern_Realms),
        FactionNG(GwentCard.Faction.Nilfgaard),
        FactionSY(GwentCard.Faction.Syndicate),
        FactionSK(GwentCard.Faction.Skellige),
        FactionST(GwentCard.Faction.Scoiatael),
        FactionNeu(GwentCard.Faction.Neutral);

        private GwentCard.Faction faction;
        FactionSelect(GwentCard.Faction faction) {
            this.faction = faction;
        }

        public GwentCard.Faction getFaction() {
            return faction;
        }
    }

    public enum ColorSelect {
        ColorALL(GwentCard.Rarity.Legendary, GwentCard.Rarity.Legendary),
        ColorBronze(GwentCard.Rarity.Rare, GwentCard.Rarity.Common),
        ColorGold(GwentCard.Rarity.Legendary, GwentCard.Rarity.Epic);
        private Card.Rarity rarity1;
        private Card.Rarity rarity2;
        ColorSelect(Card.Rarity rarity1, Card.Rarity rarity2) {
            this.rarity1 = rarity1;
            this.rarity2 = rarity2;
        }

        public Card.Rarity getRarity1() {
            return rarity1;
        }

        public Card.Rarity getRarity2() {
            return rarity2;
        }
    }

    public enum ProvisionSelect {
        ProvAll(0), ProvFour(4), ProvFive(5), ProvSix(6), ProvSeven(7),
        ProvEight(8), ProvNine(9), ProvTen(10), ProvEleven(11);
        private int value;
        ProvisionSelect(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum TypeSelect {
        TypeAll, TypeArti, TypeSpec, TypeUnit
    }

    public enum RaritySelect {
        RarityAll(GwentCard.Rarity.Legendary),
        RarityRare(GwentCard.Rarity.Rare),
        RarityComm(GwentCard.Rarity.Common),
        RarityEpic(GwentCard.Rarity.Epic),
        RarityLegn(GwentCard.Rarity.Legendary);
        private Card.Rarity rarity;

        RaritySelect(Card.Rarity rarity) {
            this.rarity = rarity;
        }

        public Card.Rarity getRarity() {
            return rarity;
        }
    }

    public void setFactionSelect(FactionSelect factionSelect) {
        this.factionSelect = factionSelect;
    }

    public void setColorSelect(ColorSelect colorSelect) {
        this.colorSelect = colorSelect;
    }

    public void setProvisionSelect(ProvisionSelect provisionSelect) {
        this.provisionSelect = provisionSelect;
    }

    public void setTypeSelect(TypeSelect typeSelect) {
        this.typeSelect = typeSelect;
    }

    public void setRaritySelect(RaritySelect raritySelect) {
        this.raritySelect = raritySelect;
    }

    private void resetCurrentDeck() {
        currentDeck = allDeck;
    }

    private void resetCurrentLeaderMap() {
        currentLeaders = allLeaders;
    }

    public void filterLibrary() {
        filterType(factionSelect,colorSelect,provisionSelect,typeSelect,raritySelect);
    }

    private void filterType(FactionSelect fs, ColorSelect cs, ProvisionSelect ps, TypeSelect ts, RaritySelect rs) {
        resetCurrentDeck();
        if (ts.equals(TypeSelect.TypeAll)) {
            filterProvision(cs, ps, fs, rs);
        }
        if (ts.equals(TypeSelect.TypeArti)) {
            filterbyArtifactClass(fs, cs, ps, rs);
        }
        if (ts.equals(TypeSelect.TypeSpec)) {
            filterbySpecialClass(fs, cs, ps, rs);
        }
        if (ts.equals(TypeSelect.TypeUnit)) {
            filterbyUnitClass(fs, cs, ps, rs);
        }
    }

    private void filterbyArtifactClass(FactionSelect fs, ColorSelect cs, ProvisionSelect ps, RaritySelect rs) {
        HashMap<String, Card> temp = new LinkedHashMap<>();
        for (Map.Entry<String,Card> entry: currentDeck.entrySet()) {
            if (entry.getValue() instanceof ArtifactCard) {
                temp.put(entry.getKey(),entry.getValue());
            }
        }
        currentDeck = temp;
        filterProvision(cs, ps, fs, rs);
    }

    private void filterbySpecialClass(FactionSelect fs, ColorSelect cs, ProvisionSelect ps, RaritySelect rs) {
        HashMap<String, Card> temp = new LinkedHashMap<>();
        for (Map.Entry<String,Card> entry: currentDeck.entrySet()) {
            if (entry.getValue() instanceof SpecialCard) {
                temp.put(entry.getKey(),entry.getValue());
            }
        }
        currentDeck = temp;
        filterProvision(cs, ps, fs, rs);
    }

    private void filterbyUnitClass(FactionSelect fs, ColorSelect cs, ProvisionSelect ps, RaritySelect rs) {
        HashMap<String, Card> temp = new LinkedHashMap<>();
        for (Map.Entry<String,Card> entry: currentDeck.entrySet()) {
            if (entry.getValue() instanceof UnitCard) {
                temp.put(entry.getKey(),entry.getValue());
            }
        }
        currentDeck = temp;
        filterProvision(cs, ps, fs, rs);
    }

    private void filterProvision(ColorSelect cs, ProvisionSelect ps, FactionSelect fs, RaritySelect rs) {
        if (ps.equals(ProvisionSelect.ProvAll)) {
            filterFaction(cs, fs, rs);
        } else {
            if (ps.equals(ProvisionSelect.ProvEleven)) {
                HashMap<String, Card> temp = new LinkedHashMap<>();
                for (Map.Entry<String, Card> entry : currentDeck.entrySet()) {
                    if (entry.getValue().getProvision() >= 11) {
                        temp.put(entry.getKey(), entry.getValue());
                    }
                }
                currentDeck = temp;
                filterFaction(cs, fs, rs);
            } else {
                filterbyProvision(cs, fs, rs, ps.getValue());
            }
        }
    }

    private void filterbyProvision(ColorSelect cs, FactionSelect fs, RaritySelect rs, int n) {
        HashMap<String, Card> temp = new LinkedHashMap<>();
        for (Map.Entry<String,Card> entry: currentDeck.entrySet()) {
            if (entry.getValue().getProvision() == n) {
                temp.put(entry.getKey(),entry.getValue());
            }
        }
        currentDeck = temp;
        filterFaction(cs, fs, rs);
    }

    private void filterFaction(ColorSelect cs, FactionSelect fs, RaritySelect rs) {
        if ((!leaderisChosen)
                && factionSelect.equals(FactionSelect.FactionAll)) {
            filterColorSelect(rs, cs);
        } else if (factionSelect.equals(FactionSelect.FactionAll)) {
            filterFactionAll(cs, rs);
        } else if (!factionSelect.equals(FactionSelect.FactionAll)) {
            HashMap<String, Card> temp = new LinkedHashMap<>();
            for (Map.Entry<String, Card> entry : currentDeck.entrySet()) {
                if (entry.getValue().getFaction().equals(fs.getFaction())) {
                    temp.put(entry.getKey(), entry.getValue());
                }
            }
            currentDeck = temp;
            filterColorSelect(rs, cs);
        }
    }

    private void filterFactionAll(ColorSelect cs, RaritySelect rs) {
        HashMap<String, Card> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Card> entry : currentDeck.entrySet()) {
            if (entry.getValue().getFaction().equals(leaderFaction)
                    || entry.getValue().getFaction().equals(GwentCard.Faction.Neutral)) {
                temp.put(entry.getKey(), entry.getValue());
            }
        }
        currentDeck = temp;
        filterColorSelect(rs, cs);
    }


    private void filterColorSelect(RaritySelect rs, ColorSelect cs) {
        if (cs.equals(ColorSelect.ColorALL)) {
            filterRarity(rs);
        } else {
            filterbyColor(rs,cs.getRarity1(),cs.getRarity2());
        }
    }

    private void filterbyColor(RaritySelect rs, GwentCard.Rarity rarity1, GwentCard.Rarity rarity2) {
        HashMap<String, Card> temp = new LinkedHashMap<>();
        for (Map.Entry<String,Card> entry: currentDeck.entrySet()) {
            if (entry.getValue().getRarity().equals(rarity1)
                    || entry.getValue().getRarity().equals(rarity2)) {
                temp.put(entry.getKey(),entry.getValue());
            }
        }
        currentDeck = temp;
        filterRarity(rs);
    }

    private void filterRarity(RaritySelect rs) {
        if (!rs.equals(RaritySelect.RarityAll)) {
            filterbyRarity(rs.getRarity());
        }
        nameSearchCard();
    }

    private void filterbyRarity(GwentCard.Rarity rarity) {
        HashMap<String, Card> temp = new LinkedHashMap<>();
        for (Map.Entry<String,Card> entry: currentDeck.entrySet()) {
            if (entry.getValue().getRarity().equals(rarity)) {
                temp.put(entry.getKey(),entry.getValue());
            }
        }
        currentDeck = temp;
    }

    public void filterLeaderFaction() {
        resetCurrentLeaderMap();
        filterLeaderbyFaction(factionSelect);
    }

    private void filterLeaderbyFaction(FactionSelect faction) {
        if (!leaderisChosen) {
            if (!factionSelect.equals(FactionSelect.FactionAll)) {
                leaderisNotChosenandNotFactionAll(faction);
            } else {
                nameSearchLeader();
            }
        } else {
            if (!factionSelect.equals(FactionSelect.FactionNeu)) {
                filterLeaderWhenNotNeu();
            } else {
                currentLeaders = new LinkedHashMap<>();
            }
        }
    }

    private void leaderisNotChosenandNotFactionAll(FactionSelect faction) {
        HashMap<String, Leader> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Leader> entry : currentLeaders.entrySet()) {
            if (entry.getValue().getFaction().equals(faction.getFaction())) {
                temp.put(entry.getKey(), entry.getValue());
            }
        }
        currentLeaders = temp;
        nameSearchLeader();
    }

    private void filterLeaderWhenNotNeu() {
        HashMap<String, Leader> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Leader> entry : currentLeaders.entrySet()) {
            if (entry.getValue().getFaction().equals(leaderFaction)) {
                temp.put(entry.getKey(), entry.getValue());
            }
        }
        currentLeaders = temp;
        nameSearchLeader();
    }

    public HashMap<String, Leader> getCurrentLeaders() {
        return currentLeaders;
    }

    public HashMap<String, Card> getCurrentDeck() {
        return currentDeck;
    }

    public boolean shouldDisplayLeaders() {
        if (colorSelect.equals(ColorSelect.ColorALL)
                && provisionSelect.equals(ProvisionSelect.ProvAll)
                && typeSelect.equals(TypeSelect.TypeAll)
                && raritySelect.equals(RaritySelect.RarityAll)) {
            return true;
        } else {
            return false;
        }
    }

    public void setAllLeaders(HashMap<String, Leader> allLeaders) {
        this.allLeaders = allLeaders;
        resetCurrentLeaderMap();
    }

    public void setAllDeck(HashMap<String, Card> allDeck) {
        this.allDeck = allDeck;
        resetCurrentDeck();
    }

    public void setLeaderisChosen(Boolean leaderisChosen) {
        this.leaderisChosen = leaderisChosen;
    }

    public void setLeaderFaction(GwentCard.Faction faction) {
        setLeaderisChosen(true);
        leaderFaction = faction;
    }

    private void nameSearchCard() {
        HashMap<String, Card> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Card> entry : currentDeck.entrySet()) {
            String card = entry.getKey();
            card = Normalizer.normalize(card, Normalizer.Form.NFD);
            card = card.replaceAll("\\p{M}", "");
            if (card.startsWith(nameSearch)) {
                temp.put(entry.getKey(), entry.getValue());
            } else {
                if (card.toLowerCase().contains(nameSearch.toLowerCase())) {
                    temp.put(entry.getKey(), entry.getValue());
                }
            }
        }
        currentDeck = temp;
    }

    private void nameSearchLeader() {
        HashMap<String, Leader> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Leader> entry : currentLeaders.entrySet()) {
            String card = entry.getKey();
            card = Normalizer.normalize(card, Normalizer.Form.NFD);
            card = card.replaceAll("\\p{M}", "");
            if (card.startsWith(nameSearch)) {
                temp.put(entry.getKey(), entry.getValue());
            } else {
                if (card.toLowerCase().contains(nameSearch.toLowerCase())) {
                    temp.put(entry.getKey(), entry.getValue());
                }
            }
        }
        currentLeaders = temp;
    }

    public void setNameSearch(String nameSearch) {
        this.nameSearch = nameSearch;
    }
}