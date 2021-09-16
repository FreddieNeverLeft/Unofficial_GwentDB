package model;

public class UnitObserver implements DeckObserver {
    private int unitCount;

    public UnitObserver() {
        unitCount = 0;
    }

    @Override
    public void update(Card c, Boolean b) {
        if (c instanceof UnitCard) {
            if (b) {
                unitCount++;
            } else {
                unitCount--;
            }
        }
    }

    public int getUnitCount() {
        return unitCount;
    }
}
