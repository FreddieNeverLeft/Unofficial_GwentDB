import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArtifactCardandCardTest {
    private ArtifactCard artifactCard;

    @BeforeEach
    void runBefore(){
        artifactCard = new ArtifactCard("Portal",12, Card.Rarity.Epic,
                00011, GwentCard.Faction.Neutral);
    }

    @Test
    void getFileNameTest() {
        assertEquals("Portal", artifactCard.getName());
        assertEquals("images/artifacts/Portal-min.png", artifactCard.getFileName());
        assertEquals("https://firebasestorage.googleapis.com/v0/b/gwent-9e62a.appspot.com/o/images%2Fv3.1.0%2F"
        + "9%2F900%2Fthumbnail.png?alt=media",artifactCard.getUrl());
    }

    @Test
    void getCriteriaTest() {
        assertEquals(12, artifactCard.getProvision());
        assertEquals(00011, artifactCard.getCardID());
        assertEquals(GwentCard.Faction.Neutral, artifactCard.getFaction());
        assertEquals(GwentCard.Rarity.Epic, artifactCard.getRarity());
    }

}
