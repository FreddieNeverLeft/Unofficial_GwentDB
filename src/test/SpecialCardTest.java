import model.Card;
import model.GwentCard;
import model.SpecialCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpecialCardTest {
    private SpecialCard specialCard;

    @BeforeEach
    void runBefore() {
        specialCard = new SpecialCard("Scorch",14,
                Card.Rarity.Epic,00016, GwentCard.Faction.Neutral);
    }

    @Test
    void getFileNameTest() {
        assertEquals("Scorch", specialCard.getName());
        assertEquals("images/specials/Scorch-min.png", specialCard.getFileName());
    }
}
