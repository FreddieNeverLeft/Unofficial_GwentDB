package ui;

import model.*;

import java.util.*;

// Learning Resource: https://www.geeksforgeeks.org/comparator-interface-java/

public class Sort {
    public List<Card> sort(List deck) {

        Collections.sort(deck, new Comparator<Card>() {
            public int compare(Card c1,
                               Card c2) {
                return (c1.getProvision() < c2.getProvision() ? 1 :
                        c1.getProvision() == c2.getProvision() ? sortColor(c1, c2) : -1);
            }
        });

        return deck;
    }

    public HashMap<String, Card> sort(HashMap<String, Card> hm) {
        List<Map.Entry<String, Card>> list =
                new LinkedList<Map.Entry<String, Card>>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Card>>() {
            public int compare(Map.Entry<String, Card> c1,
                               Map.Entry<String, Card> c2) {
                Card a = c1.getValue();
                Card b = c2.getValue();
                return (a.getProvision() < b.getProvision() ? 1 :
                        a.getProvision() ==  b.getProvision() ? sortColor(a, b) : -1);
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Card> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Card> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    private int sortColor(Card c1,Card c2) {
        if (c1.getRarity().equals(Card.Rarity.Legendary) || c1.getRarity().equals(Card.Rarity.Epic)) {
            if (c2.getRarity().equals(Card.Rarity.Legendary) || c2.getRarity().equals(Card.Rarity.Epic)) {
                return sortSameColor(c1, c2);
            } else {
                return -1;
            }
        } else if (c1.getRarity().equals(Card.Rarity.Rare) || c1.getRarity().equals(Card.Rarity.Common)) {
            if (c2.getRarity().equals(Card.Rarity.Rare) || c2.getRarity().equals(Card.Rarity.Common)) {
                return sortSameColor(c1, c2);
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }


    private int sortSameColor(Card c1, Card c2) {
        if (c1 instanceof SpecialCard) {
            return instanceofSecondCardSpec(c1, c2);
        } else if (c1 instanceof ArtifactCard) {
            if (c2 instanceof SpecialCard) {
                return 1;
            } else {
                return instanceofSecondCardArti(c1, c2);
            }
        } else {
            if (c1 instanceof UnitCard) {
                return instanceofSecondCardUnit(c1, c2);
            } else {
                return 0;
            }
        }
    }

    private int instanceofSecondCardUnit(Card c1, Card c2) {
        if (c2 instanceof UnitCard) {
            UnitCard c = (UnitCard) c1;
            UnitCard d = (UnitCard) c2;
            return sortValue(c,d);
        } else {
            return 1;
        }
    }

    private int instanceofSecondCardArti(Card c1, Card c2) {
        if (c2 instanceof ArtifactCard) {
            return (sortRarity(c1, c2));
        } else {
            return -1;
        }
    }

    private int instanceofSecondCardSpec(Card c1, Card c2) {
        if (c2 instanceof SpecialCard) {
            return sortRarity(c1, c2);
        }  else {
            return -1;
        }
    }

    private int sortValue(UnitCard c, UnitCard d) {
        int i = c.getValue();
        int j = d.getValue();
        return (Integer.compare(j, i));
    }

    private int sortRarity(Card c1, Card c2) {
        if (c1.getRarity().equals(Card.Rarity.Legendary)) {
            return instanceofSecondCardLegendary(c2);
        } else {
            if (c1.getRarity().equals(Card.Rarity.Epic)) {
                return secondCardLegendary(c2);
            } else {
                if (c1.getRarity().equals(Card.Rarity.Rare)) {
                    return secondCardRare(c2);
                } else {
                    if (c1.getRarity().equals(Card.Rarity.Common)) {
                        return secondCardRareFirstComm(c2);
                    } else {
                        return 0;
                    }
                }
            }
        }
    }

    private int secondCardRareFirstComm(Card c2) {
        if (c2.getRarity().equals(Card.Rarity.Rare)) {
            return 1;
        } else {
            return 0;
        }
    }

    private int secondCardRare(Card c2) {
        if (c2.getRarity().equals(Card.Rarity.Rare)) {
            return 0;
        } else {
            return -1;
        }
    }

    private int secondCardLegendary(Card c2) {
        if (c2.getRarity().equals(Card.Rarity.Legendary)) {
            return 1;
        } else {
            return 0;
        }
    }

    private int instanceofSecondCardLegendary(Card c2) {
        if (c2.getRarity().equals(Card.Rarity.Legendary)) {
            return 0;
        } else {
            return -1;
        }
    }

    public HashMap<String, Leader> sortLeader(HashMap<String, Leader> hm) {

        List<Map.Entry<String, Leader>> list =
                new LinkedList<Map.Entry<String, Leader>>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Leader>>() {
            public int compare(Map.Entry<String, Leader> l1,
                               Map.Entry<String, Leader> l2) {
                Leader a = l1.getValue();
                Leader b = l2.getValue();
                return (Integer.compare(b.getProvision(), a.getProvision()));
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Leader> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Leader> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

}
