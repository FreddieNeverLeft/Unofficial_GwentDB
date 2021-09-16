import model.Card;
import model.UnitCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitCardTest {
    private UnitCard unitCard;

    @BeforeEach
    void runBefore() {
        unitCard = new UnitCard("Geralt of Rivia", 3, 10,
                Card.Rarity.Legendary, 0000, Card.Faction.Neutral);
    }

    @Test
    void getFileNameTest() {
        assertEquals("Geralt of Rivia", unitCard.getName());
        assertEquals("images/units/Geralt of Rivia-min.png", unitCard.getFileName());
    }

    @Test
    void cardValueTest() {
        assertEquals(3, unitCard.getValue());
    }
}
