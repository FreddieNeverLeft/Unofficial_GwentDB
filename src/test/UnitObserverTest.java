import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitObserverTest {
    private  Card geraltOfRivia = new UnitCard("Geralt of Rivia", 3, 10, Card.Rarity.Legendary, 0000, Card.Faction.Neutral);
    private Card roach = new UnitCard("roach", 3, 10 , Card.Rarity.Epic, 0001, Card.Faction.Neutral);
    private Card rnr = new SpecialCard("Ragh Nar Roog",12, Card.Rarity.Legendary,00014, GwentCard.Faction.Neutral);
    private UnitObserver unitObserver;

    @BeforeEach
    void runBefore() {
        unitObserver = new UnitObserver();
    }

    @Test
    void addOneUnitTest() {
        unitObserver.update(geraltOfRivia,true);
        assertEquals(1, unitObserver.getUnitCount());
    }

    @Test
    void addUnitswithCardsTest() {
        unitObserver.update(geraltOfRivia,true);
        unitObserver.update(roach,true);
        assertEquals(2, unitObserver.getUnitCount());
        unitObserver.update(rnr,true);
        assertEquals(2, unitObserver.getUnitCount());
    }

    @Test
    void removeCardsandUnitsTest() {
        unitObserver.update(geraltOfRivia,true);
        unitObserver.update(roach,true);
        assertEquals(2, unitObserver.getUnitCount());
        unitObserver.update(rnr,true);
        assertEquals(2, unitObserver.getUnitCount());
        unitObserver.update(rnr,false);
        assertEquals(2, unitObserver.getUnitCount());
        unitObserver.update(roach,false);
        assertEquals(1, unitObserver.getUnitCount());
    }
}
