package assignment1;

public class Warrior extends MilitaryUnit {

    // Methods

    public Warrior (Tile tile, double healthPoints, String faction) {

        super (tile, healthPoints, 1, faction, 20.0, 1, 25);

    }



    @Override
    public boolean equals(Object obj) {

        return obj instanceof Warrior && super.equals(obj);

    }

}
