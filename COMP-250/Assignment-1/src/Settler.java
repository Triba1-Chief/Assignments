package assignment1;

public class Settler extends Unit {

    // Method

    public Settler(Tile tile, double healthPoints, String faction) {

        super(tile, healthPoints, 2, faction);

    }



    public void takeAction(Tile tile) {

        if (tile != null) {
            if (this.getPosition() == tile && !(tile.isCity())) {
                tile.buildCity();
                this.getPosition().removeUnit(this);
                tile.removeUnit(this); //removes unit from tile.
            }
        }

    }



    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof Settler;
    }
}
