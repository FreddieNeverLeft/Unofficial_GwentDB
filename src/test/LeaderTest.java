import model.GwentCard;
import model.Leader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeaderTest {
    private Leader leader;

    @BeforeEach
    void runBefore() {
        leader = new Leader("King of Beggars",
                15, GwentCard.Faction.Syndicate, 01);
    }

    @Test
    void getCriteriaTest() {
        assertEquals(165, leader.getProvision());
        assertEquals(01, leader.getCardID());
        assertEquals(GwentCard.Faction.Syndicate, leader.getFaction());
        assertEquals("King of Beggars", leader.getName());
        assertEquals("images/leaders/King of Beggars-min.png", leader.getFileName());
        assertEquals("https://firebasestorage.googleapis.com/v0/b/gwent-9e62a.appspot.com/o/images%2Fv3.1.0%2F"
                + "1%2F100%2Fthumbnail.png?alt=media", leader.getUrl());
    }
}
