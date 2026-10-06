package assignment1;

public class Archer extends MilitaryUnit {

    // Fields

    private int availableArrows;

    // Methods

    public Archer (Tile tile, double healthPoints, String faction) {

        super (tile, healthPoints, 2, faction, 15.0, 2,0);
        availableArrows = 5;

    }



    @Override
    public void takeAction(Tile tile) {

        if (availableArrows < 1) {

            availableArrows = 5;

        }
        else {
            availableArrows--;
            super.takeAction(tile);
        }
    }



    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Archer) {
            return super.equals(obj) && ((Archer) obj).availableArrows == availableArrows;
        }

        return false;

    }

}