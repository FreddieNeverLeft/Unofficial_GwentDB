import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardObserverTest {
    private Card geraltOfRivia = new UnitCard("Geralt of Rivia", 3, 10, Card.Rarity.Legendary, 0000, Card.Faction.Neutral);
    private Card roach = new UnitCard("roach", 3, 10 , Card.Rarity.Epic, 0001, Card.Faction.Neutral);
    private Card rnr = new SpecialCard("Ragh Nar Roog",12, Card.Rarity.Legendary,00014, GwentCard.Faction.Neutral);
    private CardObserver cardObserver;

    @BeforeEach
    void runBefore() {
        cardObserver = new CardObserver();
    }

    @Test
    void addOneCardTest() {
        cardObserver.update(geraltOfRivia,true);
        assertEquals(1,cardObserver.getCardCount());
    }

    @Test
    void addCardsTest() {
        cardObserver.update(geraltOfRivia,true);
        cardObserver.update(roach,true);
        cardObserver.update(rnr,true);
        assertEquals(3,cardObserver.getCardCount());
    }

    @Test
    void removeCardsTest() {
        cardObserver.update(geraltOfRivia,true);
        cardObserver.update(roach,true);
        cardObserver.update(rnr,true);
        assertEquals(3,cardObserver.getCardCount());
        cardObserver.update(rnr,false);
        assertEquals(2,cardObserver.getCardCount());
        cardObserver.update(rnr,false);
        assertEquals(1,cardObserver.getCardCount());
    }
}
